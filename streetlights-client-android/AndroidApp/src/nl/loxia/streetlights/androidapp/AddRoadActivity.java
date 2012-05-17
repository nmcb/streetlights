package nl.loxia.streetlights.androidapp;

import java.util.Arrays;
import java.util.UUID;

import nl.loxia.streetlights.model.infra.Road;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddRoadActivity extends AbstractAsyncActivity {
    private Context context;
    private String requestedUUID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;

        setContentView(R.layout.add_road);

        Button addRoadButton = (Button) findViewById(R.id.addRoadButton);
        addRoadButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                addRoad();
            }
        });
    }

    protected void addRoad() {
        TextView nameView = (TextView) findViewById(R.id.roadNameEdit);
        String name = nameView.getText().toString();
        requestedUUID = UUID.randomUUID().toString();

        Road newRoad = new Road(name, requestedUUID);

        new AsyncRoadDetailRequest().execute(newRoad);
    }

    private void refreshUI(String uuid) {
        if (uuid.equals(requestedUUID)) {
            // success! Let's go view the newly added road
            Intent intent = new Intent(this, ViewSingleRoadActivity.class);
            intent.putExtra(Road.UUID, uuid);
            startActivity(intent);
            finish();
        } else {
            // todo show error dialog?
        }
    }

    private class AsyncRoadDetailRequest extends AsyncTask<Road, Void, String> {
        private static final String TAG = "AsyncRoadDetailRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(context, getString(R.string.loading));
        }

        @Override
        protected String doInBackground(Road... road) {
            final String url = getString(R.string.path_base) + getString(R.string.port) + getString(R.string.path_addroad);
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
                // TODO display error for user
            }
            return null;
        }

        @Override
        protected void onPostExecute(String uuid) {
            dismissProgressDialog();

            refreshUI(uuid);
        }
    }
}
