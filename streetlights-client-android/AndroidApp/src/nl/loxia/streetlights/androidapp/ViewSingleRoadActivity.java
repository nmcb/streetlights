package nl.loxia.streetlights.androidapp;

import java.util.UUID;

import nl.loxia.streetlights.fragments.ViewSingleRoadFragment;
import nl.loxia.streetlights.model.infra.Road;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class ViewSingleRoadActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_road_activity);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        UUID uuid = UUID.fromString(getIntent().getStringExtra(Road.UUID_TAG));
        ViewSingleRoadFragment fragment = (ViewSingleRoadFragment) getFragmentManager().findFragmentById(R.id.viewRoadFragment);
        fragment.loadRoadInformation(uuid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
