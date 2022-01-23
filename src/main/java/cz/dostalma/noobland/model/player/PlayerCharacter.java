package cz.dostalma.noobland.model.player;

import cz.dostalma.noobland.model.world.Location;

import javax.xml.bind.annotation.*;

/**
 * Contains all the important defining information about player's character
 * like inventory, traits, skills and abilities
 */
@XmlRootElement(name = "PlayerCharacter")
@XmlType(propOrder = { "name", "level", "race", "clazz", "currentLocationId" })
public class PlayerCharacter {

    private String id;
    private String name;
    private Integer level;
    private String race;
    private String clazz;
    private String currentLocationId;
    private Location location;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    @XmlElement(name = "Level")
    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    @XmlElement(name = "Race")
    public void setRace(String race) {
        this.race = race;
    }

    public String getClazz() {
        return clazz;
    }

    @XmlElement(name = "Class")
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    @XmlElement(name = "CurrentLocationId")
    public void setCurrentLocationId(String currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public Location getLocation() {
        return location;
    }

    @XmlTransient
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "PlayerCharacter{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", race='" + race + '\'' +
                ", clazz='" + clazz + '\'' +
                ", location=" + location +
                '}';
    }

    public static class PlayerCharacterBuilder {
        private String name;
        private Integer level;
        private String race;
        private String clazz;
        private Location location;

        public PlayerCharacterBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlayerCharacterBuilder withLevel(Integer level) {
            this.level = level;
            return this;
        }

        public PlayerCharacterBuilder withRace(String race) {
            this.race = race;
            return this;
        }

        public PlayerCharacterBuilder withClazz(String clazz) {
            this.clazz = clazz;
            return this;
        }

        public PlayerCharacterBuilder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public PlayerCharacter build() {
            PlayerCharacter pc = new PlayerCharacter();
            pc.setName(name);
            pc.setLevel(level);
            pc.setRace(race);
            pc.setClazz(clazz);
            pc.setLocation(location);
            return pc;
        }
    }
}
