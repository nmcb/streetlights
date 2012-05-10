package nl.loxia.streetlights.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "roads")
public class RoadList {
    private List<Road> roads;

    public RoadList() {
    }

    public RoadList(List<Road> roads) {
        this.roads = roads;
    }

    @XmlElement(name = "road")
    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }
}