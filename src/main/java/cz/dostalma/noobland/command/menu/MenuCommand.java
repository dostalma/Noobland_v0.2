package cz.dostalma.noobland.command.menu;

import cz.dostalma.noobland.command.Command;
import cz.dostalma.noobland.command.CommandEnum;

import java.util.function.Consumer;
import java.util.function.Function;

public class MenuCommand extends Command {

    private Consumer function;
    private Function validationFunction;

    public MenuCommand(CommandEnum commandType, Consumer function, Function validationFunction) {
        super(commandType);
        this.function = function;
        this.validationFunction = validationFunction;
    }

    public MenuCommand(CommandEnum commandType, Consumer function, Function validationFunction,
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
        return (Boolean) validationFunction.apply(o);
    }
}
