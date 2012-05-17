package nl.loxia.streetlights.androidapp;

import java.util.UUID;

import nl.loxia.streetlights.fragments.ViewSingleRoadFragment;
import nl.loxia.streetlights.model.infra.Road;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

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
}
