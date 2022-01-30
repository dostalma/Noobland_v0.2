package cz.dostalma.noobland.command.handler;

import cz.dostalma.noobland.command.Command;
import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.model.player.PlayerCharacter;
import cz.dostalma.noobland.model.world.Location;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

import static cz.dostalma.noobland.util.ConsoleUtil.writeLine;

@Component
public class WorldActionHandler {

    public static Consumer<Object> move(GameContext gameContext) {
        return (o) -> {
            writeLine("Move command executed");
            Command command = (Command) o;

            String locationToken = command.getParameters()[0];
            Location currentLocation = gameContext.getPlayerCharacter().getLocation();
            Optional<Location> resultLocation =  gameContext.getWorld().getLocationWrapper().getLocations().stream()
                    .filter(location -> currentLocation.getTransitions().contains(location.getId()))
                    .filter(location -> location.getName().equalsIgnoreCase(locationToken))
                    .findAny();

            if (resultLocation.isPresent()) {
                PlayerCharacter pc = gameContext.getPlayerCharacter();

                pc.setLocation(resultLocation.get());
                pc.setCurrentLocationId(resultLocation.get().getId());
                writeLine("Moved to the \""+ locationToken + "\"");

                // describe current location
                writeLine(pc.getLocation().getDescription());
            } else {
                writeLine("There is no such connected location you can go to");
//                writeLine("No location with name \"" + locationToken + "\" found");
            }
        };
    }

}
