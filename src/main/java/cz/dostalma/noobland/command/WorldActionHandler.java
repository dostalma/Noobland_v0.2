package cz.dostalma.noobland.command;

import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.model.player.PlayerCharacter;
import cz.dostalma.noobland.model.world.Location;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

import static cz.dostalma.noobland.util.ConsoleUtil.writeLine;

@Component
public class WorldActionHandler {

    public static Consumer<Object> move(GameContext gameContext, String[] parameters) {
        return (o) -> {
            writeLine("Move command executed");

            String locationToken = parameters.length == 2 ? parameters[1] : parameters[2];
            Location currentLocation = gameContext.getPlayerCharacter().getLocation();
            Optional<Location> resultLocation =  gameContext.getWorld().getLocations().stream()
                    .filter(location -> currentLocation.getTransitions().contains(location.getId()))
                    .filter(location -> location.getName().equalsIgnoreCase(locationToken))
                    .findAny();

            if (resultLocation.isPresent()) {
                PlayerCharacter pc = gameContext.getPlayerCharacter();
                pc.setLocation(resultLocation.get());
                pc.setCurrentLocationId(resultLocation.get().getId());
                writeLine("Moved to the \""+ locationToken + "\"");
            } else {
                writeLine("No location with name \"" + locationToken + "\" found");
            }
        };
    }

}
