package cz.dostalma.noobland.model.world;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "transition")
    private List<String> transitions;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<String> transitions) {
        this.transitions = transitions;
    }

    @XmlRootElement(name = "LocationWrapper")
    public static class LocationWrapper {

        private List<Location> locations;

        public List<Location> getLocations() {
            return locations;
        }

        @XmlElement(name = "location")
        public void setLocations(List<Location> locations) {
            this.locations = locations;
        }
    }
}
