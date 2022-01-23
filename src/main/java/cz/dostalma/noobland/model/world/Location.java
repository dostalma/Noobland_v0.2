package cz.dostalma.noobland.model.world;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "name")
    private String name;

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

    public List<String> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<String> transitions) {
        this.transitions = transitions;
    }
}
