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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ListRoadsActivity extends AbstractAsyncListActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new AsyncListRoadsRequest().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_roads_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.add_road_menu_item:
            startActivity(new Intent(this, AddRoadActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList(Roads roads) {
        RoadsListAdapter adapter = new RoadsListAdapter(this, roads);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Road road = (Road) l.getItemAtPosition(position);
        Intent intent = new Intent(this, ViewSingleRoadActivity.class);
        intent.putExtra(Road.UUID, road.getUuid());
        startActivity(intent);
    }

    private class AsyncListRoadsRequest extends AsyncTask<Void, Void, Roads> {
        private static final String TAG = "AsyncListRoadsRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(context, getString(R.string.loading));
        }

        @Override
        protected Roads doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            Roads roads = Roads.emptyRoads();

            final String url = getString(R.string.path_base) + getString(R.string.port) + getString(R.string.path_listroads);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            // Perform the HTTP GET request
            try {
                ResponseEntity<Roads> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Roads.class);
                Roads stateList = responseEntity.getBody();
                if (stateList != null && stateList.getRoads() != null) {
                    roads = stateList;
                }
            } catch (RestClientException e) {
                Log.e(TAG, "Error during request", e);
                // TODO display error for user
            }
            return roads;
        }

        @Override
        protected void onPostExecute(Roads roads) {
            Log.i(TAG, "onPostExecute: " + roads.getRoads().size() + "roads received");
            dismissProgressDialog();

            refreshList(roads);
        }
    }
}
