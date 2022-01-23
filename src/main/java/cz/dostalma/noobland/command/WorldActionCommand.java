package cz.dostalma.noobland.command;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

import static cz.dostalma.noobland.util.ConsoleUtil.*;

public class WorldActionCommand extends Command {

    private Consumer function;
    private Function validationFunction;

    public WorldActionCommand(CommandEnum commandType, Consumer function, Function validationFunction) {
        super(commandType);
        this.function = function;
        this.validationFunction = validationFunction;
    }

    public WorldActionCommand(CommandEnum commandType, Consumer function, Function validationFunction,
                                String... parameters) {
        super(commandType, parameters);
        this.function = function;
        this.validationFunction = validationFunction;
    }

    @Override
    public void execute(Object o) {
        if (validate(o)) {
            function.accept(o);
        }
    }

    @Override
    public Boolean validate(Object o) {
        if (validationFunction == null) {
            return true;
        }
        Boolean result = (Boolean) validationFunction.apply(o);
        if (result != true) {
            writeLine("Command validation failed");
        }
        return result;
    }
}
