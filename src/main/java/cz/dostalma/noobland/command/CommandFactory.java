package cz.dostalma.noobland.command;

import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.command.menu.MenuCommand;
import cz.dostalma.noobland.command.menu.MenuActionHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    @Autowired
    GameContext gameContext;

    public Command createCreateCharacterCommand() {
        return new MenuCommand(CommandEnum.CREATE_CHARACTER,
                MenuActionHandler.createCharacter(gameContext),
                null);
    }

    public Command createSaveCharacterCommand() {
        return new MenuCommand(CommandEnum.SAVE_CHARACTER,
                MenuActionHandler.savePlayerCharacter(gameContext),
                (o) -> gameContext.getPlayerCharacter() != null);
    }

    public Command createSaveGameCommand() {
        return new MenuCommand(CommandEnum.SAVE_GAME,
                MenuActionHandler.saveGame(gameContext),
                (o) -> gameContext.getPlayerCharacter() != null && gameContext.getWorld() != null);
    }

    public Command createLoadCharacterCommand(String... parameters) {
        return new MenuCommand(CommandEnum.LOAD_CHARACTER,
                MenuActionHandler.loadPlayerCharacter(gameContext),
                (o) -> parameters != null && parameters.length == 1,
                parameters);
    }

    public Command createQuitGameCommand() {
        return new WorldActionCommand(CommandEnum.QUIT_GAME,
                MenuActionHandler.quitGame(gameContext),
                null);
    }

    public Command createMoveCommand(String[] parameters) {
        return new WorldActionCommand(CommandEnum.MOVE,
                WorldActionHandler.move(gameContext, parameters),
                (o) -> parameters != null && (parameters.length == 2 || parameters.length == 3),
                parameters);
    }

}
