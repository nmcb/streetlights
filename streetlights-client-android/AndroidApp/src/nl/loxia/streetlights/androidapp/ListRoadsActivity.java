package nl.loxia.streetlights.androidapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.loxia.streetlights.entities.Road;
import nl.loxia.streetlights.entities.RoadList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.ListActivity;
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

    public void refreshList(List<Road> roadList) {
        // TODO refresh the UI
        for (Road road : roadList) {
            Log.i(TAG, road.getName() + " : " + road.getUuid());
        }
    }

    private class AsyncListRoadsRequest extends AsyncTask<Void, Void, List<Road>> {
        private static final String TAG = "AsyncListRoadsRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(getString(R.string.loading));
        }

        @Override
        protected List<Road> doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");

            final String url = getString(R.string.path_base) + getString(R.string.port) + getString(R.string.path_listroads);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();

            // Perform the HTTP GET request
            try {
                ResponseEntity<RoadList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, RoadList.class);
                RoadList stateList = responseEntity.getBody();
                return stateList.getRoads();
            } catch (RestClientException e) {
                Log.e(TAG, "Error during request", e);
                // TODO display error for user
            }
            return Collections.<Road> emptyList();
        }

        @Override
        protected void onPostExecute(List<Road> roadList) {
            Log.i(TAG, "onPostExecute: " + roadList.size() + "roads received");
            dismissProgressDialog();

            refreshList(roadList);
        }
    }
}
