package nl.loxia.streetlights.fragments;

import java.util.Arrays;
import java.util.UUID;

import nl.loxia.streetlights.androidapp.AddRoadActivity;
import nl.loxia.streetlights.androidapp.R;
import nl.loxia.streetlights.androidapp.RoadsListAdapter;
import nl.loxia.streetlights.androidapp.SettingsActivity;
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
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListRoadsFragment extends AbstractAsyncListFragment {
    private Activity activity;
    private Context context;
    private boolean dualPane;
    private int currentSelection = -1;
    private UUID futureSelection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // check if the dualPaneFrame is there (i.e. we're on a tablet)
        View dualPaneFrame = getActivity().findViewById(R.id.dualPaneFrame);
        dualPane = dualPaneFrame != null && dualPaneFrame.getVisibility() == View.VISIBLE;

        if (dualPane) {
            // In dual-pane mode, the listview highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    private void showDetails(int position) {
        currentSelection = position;

        Road road = (Road) getListView().getItemAtPosition(position);

        if (dualPane) {
            getListView().setItemChecked(currentSelection, true);

            // Check what fragment is currently shown, replace if needed.
            ViewSingleRoadFragment singleRoadFragment = null;
            Fragment currentFragment = getFragmentManager().findFragmentById(R.id.dualPaneFrame);
            if (currentFragment instanceof ViewSingleRoadFragment) {
                singleRoadFragment = (ViewSingleRoadFragment) currentFragment;
            }
            if (singleRoadFragment == null || !singleRoadFragment.getCurrentRoad().equals(road)) {
                singleRoadFragment = ViewSingleRoadFragment.newInstance(road);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.dualPaneFrame, singleRoadFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent(activity, ViewSingleRoadActivity.class);
            intent.putExtra(Road.UUID_TAG, road.getUuid().toString());
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(new RoadsListAdapter(activity, Roads.emptyRoads()));
        doRequestList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_roads_activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.addRoadMenuItem:
            addRoad();
            return true;
        case R.id.refreshMenuItem:
            doRequestList();
            return true;
        case R.id.overflowMenuItem:
            settingsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addRoad() {
        if (dualPane) {
            // Check what fragment is currently shown, replace if needed.
            AddRoadFragment addRoadFragment = null;
            Fragment currentFragment = getFragmentManager().findFragmentById(R.id.dualPaneFrame);
            if (currentFragment instanceof AddRoadFragment) {
                addRoadFragment = (AddRoadFragment) currentFragment;
            }
            if (addRoadFragment == null) {
                addRoadFragment = new AddRoadFragment();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.dualPaneFrame, addRoadFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        } else {
            startActivity(new Intent(activity, AddRoadActivity.class));
        }
    }

    public void doRequestList() {
        new AsyncListRoadsRequest().execute();
    }

    private void settingsMenu() {
        startActivity(new Intent(activity, SettingsActivity.class));
    }

    public void refreshList(Roads roads) {
        RoadsListAdapter adapter = new RoadsListAdapter(activity, roads);
        setListAdapter(adapter);

        // TEST CODE To See if the SharedPreferences Work
        SharedPreferences settings = activity.getSharedPreferences(SettingsActivity.PREFS_NAME, Activity.MODE_PRIVATE);
        String ipAddress = settings.getString("ip_address", null);
        Toast.makeText(activity, "IP Address: " + ipAddress, Toast.LENGTH_SHORT).show();
        // END OF TEST CODE

        if (futureSelection != null) {
            setSelection(futureSelection);
            futureSelection = null;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private class AsyncListRoadsRequest extends AsyncTask<Void, Void, Roads> implements ICancelableAsyncTask {
        private static final String TAG = "AsyncListRoadsRequest";
        private volatile boolean cancelled = false;

        @Override
        protected void onPreExecute() {
            showProgressDialog(activity, this, getString(R.string.loading));
        }

        @Override
        public void cancel() {
            cancelled = true;
            dismissProgressDialog();
        }

        @Override
        protected Roads doInBackground(Void... params) {            
            String ipAddress = "http://172.19.3.200";
            String portNumber = ":8666";
            
            Log.i(TAG, "doInBackground");
            Roads roads = Roads.emptyRoads();

            final String url = ipAddress + portNumber + getString(R.string.path_listroads);
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
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Error during request", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return roads;
        }

        @Override
        protected void onPostExecute(Roads roads) {
            if (!cancelled) {
                Log.i(TAG, "onPostExecute: " + roads.getRoads().size() + "roads received");

                refreshList(roads);
                dismissProgressDialog();
            }
        }
    }

    public void setFutureSelection(UUID uuid) {
        futureSelection = uuid;
    }

    private void setSelection(UUID uuid) {
        ListView list = getListView();

        int itemCount = list.getCount();
        for (int i = 0; i < itemCount; ++i) {
            if (((Road) list.getItemAtPosition(i)).getUuid().equals(uuid)) {
                showDetails(i);
                break;
            }
        }

    }
}
