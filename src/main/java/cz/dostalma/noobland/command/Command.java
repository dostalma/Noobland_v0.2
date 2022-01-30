package cz.dostalma.noobland.command;

import cz.dostalma.noobland.context.ContextLocationEnum;
import cz.dostalma.noobland.context.GameContext;

import java.util.function.Consumer;
import java.util.function.Function;

import static cz.dostalma.noobland.util.ConsoleUtil.writeLine;

public class Command {

    public static Command EMPTY_COMMAND = new Command(null);

    private Consumer function;
    private Function validationFunction;
    private CommandEnum commandType;
    private String[] parameters;

    public Command(CommandEnum commandType) {
        this.commandType = commandType;
    }

    public Command(CommandEnum commandType, Consumer function, Function validationFunction,
                       String... parameters) {
        this.commandType = commandType;
        this.parameters = parameters;
        this.function = function;
        this.validationFunction = validationFunction;
    }

    public void execute(Object o) {
        if (validate(o)) {
            function.accept(o);
        }
    }

    public Boolean validate(Object o) {
        if (validationFunction == null) {
            return true;
        }
        if (ContextLocationEnum.UNIVERSAL.equals(commandType.getContextLocationEnum())
            || commandType.getContextLocationEnum().equals(GameContext.getInstance().getContextLocation())) {
            Boolean result = (Boolean) validationFunction.apply(o);
            if (result != true) {
                writeLine("Command validation failed");
            }
            return result;
        }
        writeLine("You cannot use that command in this context");
        return false;
    }

    public CommandEnum getCommandType() {
        return commandType;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
}
