package nl.loxia.streetlights.model.infra;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Roads {
    @ElementList(inline = true)
    private List<Road> roads;

    public Roads() {
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }
}
