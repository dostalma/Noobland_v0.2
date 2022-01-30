package cz.dostalma.noobland.model.world;

import javax.xml.bind.annotation.*;

import static cz.dostalma.noobland.model.world.Location.LocationWrapper;

/**
 * Composite-sort-of class that holds information about the game world.
 * Such as locations, monsters, npc and so on.
 */
@XmlRootElement(name = "world")
@XmlAccessorType(XmlAccessType.FIELD)
public class World {

    @XmlAttribute(name = "id")
    private String id;
    @XmlAttribute(name = "startingLocationId")
    private String startingLocationId;
    @XmlElement(name = "locations")
    private LocationWrapper locationWrapper;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartingLocationId() {
        return startingLocationId;
    }

    public void setStartingLocationId(String startingLocationId) {
        this.startingLocationId = startingLocationId;
    }

    public LocationWrapper getLocationWrapper() {
        return locationWrapper;
    }

    public void setLocationWrapper(LocationWrapper locations) {
        this.locationWrapper = locations;
    }
}
