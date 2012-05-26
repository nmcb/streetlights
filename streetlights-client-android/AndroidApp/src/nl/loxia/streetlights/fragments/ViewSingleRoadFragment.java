package nl.loxia.streetlights.fragments;

import java.util.Arrays;
import java.util.UUID;

import nl.loxia.streetlights.androidapp.R;
import nl.loxia.streetlights.androidapp.SettingsActivity;
import nl.loxia.streetlights.model.infra.Road;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSingleRoadFragment extends AbstractAsyncFragment {
    private Activity activity;
    private TextView uuidView;
    private Road currentRoad;
    SharedPreferences settings = activity.getSharedPreferences(SettingsActivity.PREFS_NAME, Activity.MODE_PRIVATE);

    public static ViewSingleRoadFragment newInstance(Road road) {
        ViewSingleRoadFragment fragment = new ViewSingleRoadFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Road.UUID_TAG, road.getUuid().toString());
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_road_fragment, container, false);

        uuidView = (TextView) view.findViewById(R.id.roadUuidLabel);

        UUID uuid = UUID.fromString(getArguments().getString(Road.UUID_TAG));
        loadRoadInformation(uuid);

        return view;
    }

    public void loadRoadInformation(UUID uuid) {
        String uuidString = uuid.toString();

        uuidView.setText(uuidString);
        new AsyncRoadDetailRequest().execute(uuidString);
    }

    private void refreshUI(Road road) {
        currentRoad = road;

        if (currentRoad != null) {
            TextView nameView = (TextView) getView().findViewById(R.id.roadNameLabel);
            TextView uriView = (TextView) getView().findViewById(R.id.roadUriLabel);

            nameView.setText(currentRoad.getName());
            uriView.setText(currentRoad.getUri());
        }
    }

    private class AsyncRoadDetailRequest extends AsyncTask<String, Void, Road> implements ICancelableAsyncTask {
        private static final String TAG = "AsyncRoadDetailRequest";
        private volatile boolean cancelled = false;

        @Override
        protected void onPreExecute() {
            showProgressDialog(activity, this, getString(R.string.loading));
        }

        @Override
        protected Road doInBackground(String... uuid) {
//            String ipAddress = settings.getString("ip_address", null);
//            String portNumber = settings.getString("port_number", null);
            String ipAddress = "http://172.19.3.200";
            String portNumber = ":8666";
            
            final String url = ipAddress + portNumber + getString(R.string.path_singleroad)
                    + uuid[0];
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();

            // Perform the HTTP GET request
            try {
                ResponseEntity<Road> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Road.class);
                Road stateList = responseEntity.getBody();
                return stateList;
            } catch (RestClientException e) {
                Log.e(TAG, "Error during request", e);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Error during request", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Road road) {
            if (!cancelled) {
                dismissProgressDialog();

                refreshUI(road);
            }
        }

        @Override
        public void cancel() {
            cancelled = true;
            dismissProgressDialog();
        }
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }
}
