package nl.loxia.streetlights.fragments;

import java.util.Arrays;

import nl.loxia.streetlights.androidapp.AddRoadActivity;
import nl.loxia.streetlights.androidapp.R;
import nl.loxia.streetlights.androidapp.RoadsListAdapter;
import nl.loxia.streetlights.androidapp.ViewSingleRoadActivity;
import nl.loxia.streetlights.model.infra.Road;
import nl.loxia.streetlights.model.infra.Roads;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ListRoadsFragment extends AbstractAsyncListFragment {
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        new AsyncListRoadsRequest().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_roads_activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.add_road_menu_item:
            startActivity(new Intent(activity, AddRoadActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList(Roads roads) {
        RoadsListAdapter adapter = new RoadsListAdapter(activity, roads);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Road road = (Road) l.getItemAtPosition(position);
        Intent intent = new Intent(activity, ViewSingleRoadActivity.class);
        intent.putExtra(Road.UUID_TAG, road.getUuid().toString());
        startActivity(intent);
    }

    private class AsyncListRoadsRequest extends AsyncTask<Void, Void, Roads> {
        private static final String TAG = "AsyncListRoadsRequest";

        @Override
        protected void onPreExecute() {
            showProgressDialog(activity, getString(R.string.loading));
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
