package nl.loxia.streetlights.androidapp;

import java.util.Arrays;

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
import android.widget.TextView;

public class ViewSingleRoadActivity extends AbstractAsyncActivity {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;

        setContentView(R.layout.single_road_information);

        String uuid = getIntent().getStringExtra(Road.UUID);

        TextView uuidView = (TextView) findViewById(R.id.roadUuidLabel);
        uuidView.setText(uuid);

        new AsyncRoadDetailRequest().execute(uuid);
    }

    private void refreshUI(Road road) {
        if (road != null) {
            TextView nameView = (TextView) findViewById(R.id.roadNameLabel);
            TextView uriView = (TextView) findViewById(R.id.roadUriLabel);

            nameView.setText(road.getName());
            uriView.setText(road.getUri());
        }
    }

    private class AsyncRoadDetailRequest extends AsyncTask<String, Void, Road> {
        private static final String TAG = "AsyncRoadDetailRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(context, getString(R.string.loading));
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
