package nl.loxia.streetlights.model.infra;

import java.util.Collections;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import android.util.Log;

@Root
public class Roads {
    private static Roads EMPTY_ROADS;

    // not required because we can get an empty list
    @ElementList(inline = true, required = false)
    private List<Road> roads;

    public Roads() {
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public static Roads emptyRoads() {
        if (EMPTY_ROADS == null) {
            EMPTY_ROADS = new Roads();
            EMPTY_ROADS.setRoads(Collections.<Road> emptyList());
        }
        return EMPTY_ROADS;
    }
}
