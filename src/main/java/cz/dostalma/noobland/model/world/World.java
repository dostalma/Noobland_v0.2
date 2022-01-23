package cz.dostalma.noobland.model.world;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Composite-sort-of class that holds information about the game world.
 * Such as locations, monsters, npc and so on.
 */
@XmlRootElement(name = "world")
@XmlAccessorType(XmlAccessType.FIELD)
public class World {

    @XmlAttribute(name = "id")
    private String id;
    @XmlElement(name = "location")
    private List<Location> locations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
