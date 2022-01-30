package cz.dostalma.noobland.command;

import cz.dostalma.noobland.command.handler.WorldActionHandler;
import cz.dostalma.noobland.context.ContextLocationEnum;
import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.command.handler.MenuActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandFactory {

    @Autowired
    GameContext gameContext;

    public Command createMenuCommand() {
        return new Command(CommandEnum.MENU,
                MenuActionHandler.menu(),
                (o) -> !ContextLocationEnum.MENU.equals(gameContext.getContextLocation()));
    }

    public Command createReturnToGameCommand() {
        return new Command(CommandEnum.MENU,
                MenuActionHandler.returnToGame(),
                (o) -> gameContext.getPreviousContextLocation() != null
                        && ContextLocationEnum.MENU.equals(gameContext.getContextLocation()));
    }

    public Command createCreateNewGameCommand() {
        return new Command(CommandEnum.CREATE_NEW_GAME,
                MenuActionHandler.createNewGame(),
                null);
    }

    public Command createSaveGameCommand() {
        return new Command(CommandEnum.SAVE_GAME,
                MenuActionHandler.saveGame(),
                (o) -> gameContext.getPlayerCharacter() != null && gameContext.getWorld() != null);
    }

    public Command createLoadGameCommand(String... parameters) {
        return new Command(CommandEnum.LOAD_GAME,
                MenuActionHandler.loadGame(),
                (command) -> command != null  && ((Command) command).getParameters() != null
                        && ((Command) command).getParameters().length == 1,
                parameters);
    }

    public Command createQuitGameCommand() {
        return new Command(CommandEnum.QUIT_GAME,
                MenuActionHandler.quitGame(),
                null);
    }

    public Command createMoveCommand(String[] parameters) {
        String locationName = "";
        if (parameters != null && parameters.length > 2 && "to".equalsIgnoreCase(parameters[1])) {
            locationName = String.join(" ", Arrays.asList(parameters).subList(2, parameters.length));
        } else if (parameters != null && parameters.length == 2) {
            locationName = String.join(" ", Arrays.asList(parameters).subList(1, parameters.length));
        }

        return new Command(CommandEnum.MOVE,
                WorldActionHandler.move(gameContext),
                (command) -> ((Command) command).getParameters() != null
                        && ((Command) command).getParameters().length > 0,
                locationName);
    }


}
