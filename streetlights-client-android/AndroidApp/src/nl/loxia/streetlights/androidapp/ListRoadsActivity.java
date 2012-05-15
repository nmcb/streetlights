package nl.loxia.streetlights.androidapp;

import java.util.Arrays;

import nl.loxia.streetlights.model.infra.Road;
import nl.loxia.streetlights.model.infra.Roads;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class ListRoadsActivity extends AbstractAsyncListActivity {
    private static final String TAG = "ListRoadsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new AsyncListRoadsRequest().execute();
    }

    public void refreshList(Roads roads) {
        // TODO refresh the UI
        for (Road road : roads.getRoads()) {
            Log.i(TAG, road.getName() + " : " + road.getUuid());
        }
    }

    private class AsyncListRoadsRequest extends AsyncTask<Void, Void, Roads> {
        private static final String TAG = "AsyncListRoadsRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(getString(R.string.loading));
        }

        @Override
        protected Roads doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");

            final String url = getString(R.string.path_base) + getString(R.string.port) + getString(R.string.path_listroads);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();

            // Perform the HTTP GET request
            try {
                ResponseEntity<Roads> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Roads.class);
                Roads stateList = responseEntity.getBody();
                return stateList;
            } catch (RestClientException e) {
                Log.e(TAG, "Error during request", e);
                // TODO display error for user
            }
            return null;
        }

        @Override
        protected void onPostExecute(Roads roads) {
            Log.i(TAG, "onPostExecute: " + roads.getRoads().size() + "roads received");
            dismissProgressDialog();

            refreshList(roads);
        }
    }
}
