package nl.loxia.streetlights.fragments;

import java.util.Arrays;
import java.util.UUID;

import nl.loxia.streetlights.androidapp.R;
import nl.loxia.streetlights.model.infra.Road;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewSingleRoadFragment extends AbstractAsyncFragment {
    private Context activity;
    private TextView uuidView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_road_fragment, container, false);

        uuidView = (TextView) view.findViewById(R.id.roadUuidLabel);

        return view;
    }

    public void loadRoadInformation(UUID uuid) {
        String uuidString = uuid.toString();

        uuidView.setText(uuidString);
        new AsyncRoadDetailRequest().execute(uuidString);
    }

    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    // switch (item.getItemId()) {
    // case android.R.id.home:
    // onBackPressed();
    // return true;
    // }
    // return super.onOptionsItemSelected(item);
    // }

    private void refreshUI(Road road) {
        if (road != null) {
            TextView nameView = (TextView) getView().findViewById(R.id.roadNameLabel);
            TextView uriView = (TextView) getView().findViewById(R.id.roadUriLabel);

            nameView.setText(road.getName());
            uriView.setText(road.getUri());
        }
    }

    private class AsyncRoadDetailRequest extends AsyncTask<String, Void, Road> {
        private static final String TAG = "AsyncRoadDetailRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(activity, getString(R.string.loading));
        }

        @Override
        protected Road doInBackground(String... uuid) {
            final String url = getString(R.string.path_base) + getString(R.string.port) + getString(R.string.path_singleroad)
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
                // TODO display error for user
            }
            return null;
        }

        @Override
        protected void onPostExecute(Road road) {
            dismissProgressDialog();

            refreshUI(road);
        }
    }
}
