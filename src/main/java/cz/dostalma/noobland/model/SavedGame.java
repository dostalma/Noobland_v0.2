package cz.dostalma.noobland.model;

import cz.dostalma.noobland.model.player.PlayerCharacter;
import cz.dostalma.noobland.model.world.World;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class used as a root node for saving and loading a game.
 */
@XmlRootElement(name = "SavedGame")
@XmlAccessorType(XmlAccessType.FIELD)
public class SavedGame {

    @XmlElement(name = "PlayerCharacter")
    private PlayerCharacter playerCharacter;

    @XmlElement(name = "World")
    private World world;

    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
