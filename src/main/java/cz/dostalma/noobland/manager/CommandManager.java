package cz.dostalma.noobland.manager;

import cz.dostalma.noobland.command.Command;
import cz.dostalma.noobland.command.CommandFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static cz.dostalma.noobland.util.ConsoleUtil.*;

@Component
public class CommandManager {

    @Autowired
    private CommandFactory commandFactory;

    public Command getCommandFromPlayerInput() {
        // get String input
        String input = readLine();
        // parse input into command and parameters
        Command command = getCommandFromString(input);
        // validate command

        return command;
    }

    private Command getCommandFromString(String inputStr) {
        if (StringUtils.isBlank(inputStr)) {
            return Command.EMPTY_COMMAND;
        }

        String[] parts = inputStr.split(" ");
        if (parts.length == 0) {
            return Command.EMPTY_COMMAND;
        }

        switch (parts[0].toLowerCase()) {
            case "quit":
                return commandFactory.createQuitGameCommand();
            case "new":
                return commandFactory.createCreateNewGameCommand();
            case "load":
                if (parts.length < 2) {
                    writeLine("Error: Load command must have a saved character name parameter");
                    return Command.EMPTY_COMMAND;
                }
                return commandFactory.createLoadGameCommand(parts[1]);
            case "save":
                return commandFactory.createSaveGameCommand();
            case "go": case "goto":
                return commandFactory.createMoveCommand(parts);


            default:
                // NOTHING
        }
        return Command.EMPTY_COMMAND;
    }

}
