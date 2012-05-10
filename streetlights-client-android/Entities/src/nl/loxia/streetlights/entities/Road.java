package nl.loxia.streetlights.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "road")
public class Road {
    private String name;
    private String uuid;

    public Road(String name) {
    }

    public Road(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
