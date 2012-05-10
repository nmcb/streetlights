package nl.loxia.streetlights.androidapp;

import nl.loxia.streetlights.entities.Road;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StreetlightsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Road road = new Road("bla");

        setContentView(R.layout.main);

        Button getRoadListButton = (Button) findViewById(R.id.button_listroads);
        getRoadListButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListRoadsActivity.class));
            }
        });
    }
}