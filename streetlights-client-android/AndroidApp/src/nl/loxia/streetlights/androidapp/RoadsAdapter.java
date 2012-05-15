package nl.loxia.streetlights.androidapp;

import nl.loxia.streetlights.model.infra.Road;
import nl.loxia.streetlights.model.infra.Roads;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoadsAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Roads roads;

    public RoadsAdapter(Context context, Roads roads) {
        this.inflater = LayoutInflater.from(context);
        this.roads = roads;

    }

    @Override
    public int getCount() {
        return roads.getRoads().size();
    }

    @Override
    public Road getItem(int position) {
        return roads.getRoads().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.roadslistentry, parent, false);
        }

        Road road = getItem(position);
        if (road != null) {
            TextView nameView = (TextView) convertView.findViewById(R.id.roadNameLabel);
            TextView uuidView = (TextView) convertView.findViewById(R.id.roadUuidLabel);
            TextView uriView = (TextView) convertView.findViewById(R.id.roadUriLabel);

            nameView.setText(road.getName());
            uuidView.setText(road.getUuid());
            uriView.setText(road.getUri());
        }

        return convertView;
    }
}
