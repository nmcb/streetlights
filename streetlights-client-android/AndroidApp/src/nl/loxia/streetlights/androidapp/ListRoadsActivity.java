package nl.loxia.streetlights.androidapp;

import java.util.UUID;

import nl.loxia.streetlights.fragments.ListRoadsFragment;
import nl.loxia.streetlights.model.infra.Road;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ListRoadsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listroads_activity);

        String roadUuid = getIntent().getStringExtra(Road.UUID_TAG);
        if (roadUuid != null) {
            ListRoadsFragment fragment = (ListRoadsFragment) getFragmentManager().findFragmentById(R.id.roadListFragment);
            fragment.setFutureSelection(UUID.fromString(roadUuid));
        }
    }
}
