package cz.dostalma.noobland.context;

import cz.dostalma.noobland.model.player.PlayerCharacter;
import cz.dostalma.noobland.model.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class GameContext {

    private PlayerCharacter playerCharacter;
    private World world;
    private ContextLocationEnum contextLocation;

    @Autowired
    private Environment env;

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

    public ContextLocationEnum getContextLocation() {
        return contextLocation;
    }

    public void setContextLocation(ContextLocationEnum contextLocation) {
        this.contextLocation = contextLocation;
    }

    public Environment getEnv() {
        return env;
    }
}
