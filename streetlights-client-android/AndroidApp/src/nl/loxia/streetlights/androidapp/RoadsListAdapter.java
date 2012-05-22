package nl.loxia.streetlights.androidapp;

import nl.loxia.streetlights.model.infra.Road;
import nl.loxia.streetlights.model.infra.Roads;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoadsListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Roads roads;

    public RoadsListAdapter(Context context, Roads roads) {
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
            convertView = inflater.inflate(R.layout.roads_list_entry, parent, false);
        }

        Road road = getItem(position);
        if (road != null) {
            TextView nameView = (TextView) convertView.findViewById(R.id.roadNameLabel);
            TextView uuidView = (TextView) convertView.findViewById(R.id.roadUuidLabel);

            nameView.setText(road.getName());
            uuidView.setText(road.getUuid().toString());
        }

        return convertView;
    }
}
