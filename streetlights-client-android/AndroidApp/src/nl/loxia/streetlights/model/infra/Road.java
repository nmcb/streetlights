package nl.loxia.streetlights.model.infra;

import java.util.List;
import java.util.UUID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Road {
    public static final String UUID_TAG = "uuid";

    @Attribute()
    private String uuid;

    @Attribute(required = false)
    private String uri;

    @Element
    private String name;
    
    @Attribute(required = false)
    private String longitude;
    
    @Attribute(required = false)
    private String latitude;

    @ElementList(required = false)
    private List<Segment> segments;

    public Road() {
    }

    public Road(String name, String uuid, String longitude, String latitude) {
        this.name = name;
        this.uuid = uuid;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Road other = (Road) obj;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }
}
