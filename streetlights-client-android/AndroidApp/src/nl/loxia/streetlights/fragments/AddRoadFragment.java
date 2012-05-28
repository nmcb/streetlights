package nl.loxia.streetlights.fragments;

import java.util.Arrays;
import java.util.UUID;

import nl.loxia.streetlights.androidapp.ListRoadsActivity;
import nl.loxia.streetlights.androidapp.R;
import nl.loxia.streetlights.androidapp.ViewSingleRoadActivity;
import nl.loxia.streetlights.model.infra.Road;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddRoadFragment extends AbstractAsyncFragment {
    private Activity activity;
    private String requestedUUID;
    private TextView nameTextView;
    private boolean dualPane;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addroad_fragment, container, false);

        // check if the dualPaneFrame is there (i.e. we're on a tablet)
        View dualPaneFrame = getActivity().findViewById(R.id.dualPaneFrame);
        dualPane = dualPaneFrame != null && dualPaneFrame.getVisibility() == View.VISIBLE;

        nameTextView = (TextView) view.findViewById(R.id.roadNameEdit);
        Button addRoadButton = (Button) view.findViewById(R.id.addRoadButton);
        addRoadButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                addRoad();
            }
        });

        return view;
    }

    protected void addRoad() {
        String name = nameTextView.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(activity, "Please fill in the \"name\" field before adding", Toast.LENGTH_SHORT).show();
        } else {
            requestedUUID = UUID.randomUUID().toString();
            
            Road newRoad = new Road(name, requestedUUID, "52.038732", "5.177254");

            new AsyncRoadDetailRequest().execute(newRoad);
        }
    }

    private void refreshUI(String uuid) {
        if (uuid.equals(requestedUUID)) {
            // success! Let's go view the newly added road
            if (dualPane) {
                Intent intent = new Intent(activity, ListRoadsActivity.class);
                intent.putExtra(Road.UUID_TAG, uuid);
                startActivity(intent);
                activity.finish();
            } else {
                Intent intent = new Intent(activity, ViewSingleRoadActivity.class);
                intent.putExtra(Road.UUID_TAG, uuid);
                startActivity(intent);
                activity.finish();
            }
        } else {
            Toast.makeText(activity, "Error: response UUID different than request UUID when adding road", Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncRoadDetailRequest extends AsyncTask<Road, Void, String> implements ICancelableAsyncTask {
        private static final String TAG = "AsyncRoadDetailRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(activity, this, getString(R.string.loading));
        }

        @Override
        protected String doInBackground(Road... road) {
            String ipAddress = Settings.getSetting(activity, "ip_address", null);
            String portNumber = Settings.getSetting(activity, "port_number", ":8666");
            
            final String url = ipAddress + portNumber + getString(R.string.path_addroad);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
            HttpEntity<Road> requestEntity = new HttpEntity<Road>(road[0], requestHeaders);
            RestTemplate restTemplate = new RestTemplate();

            // Perform the HTTP POST request
            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
                String uuid = responseEntity.getBody();
                return uuid;
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
        protected void onPostExecute(String uuid) {
            dismissProgressDialog();

            refreshUI(uuid);
        }

        @Override
        public void cancel() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Adding a road can not be cancelled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
