package cz.dostalma.noobland.context;

import cz.dostalma.noobland.model.player.PlayerCharacter;
import cz.dostalma.noobland.model.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class GameContext {

    private PlayerCharacter playerCharacter;
    private World world;
    private ContextLocationEnum contextLocation;
    private ContextLocationEnum previousContextLocation;

    private static GameContext instance;

    @Autowired
    private Environment env;

    @Autowired
    ResourceLoader resourceLoader;

    public boolean isContextFullyInitialized() {
        return playerCharacter != null
                && world != null
                && contextLocation != null;
    }

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

    public ContextLocationEnum getPreviousContextLocation() {
        return previousContextLocation;
    }

    public void setPreviousContextLocation(ContextLocationEnum previousContextLocation) {
        this.previousContextLocation = previousContextLocation;
    }

    public Environment getEnv() {
        return env;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public static GameContext getInstance() {
        return instance;
    }

    public static void setInstance(GameContext context) {
        instance = context;
    }
}
