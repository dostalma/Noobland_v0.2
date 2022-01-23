package cz.dostalma.noobland.command;

public abstract class Command {

    public static Command EMPTY_COMMAND = new WorldActionCommand(null, null, null);

    private CommandEnum commandType;
    private String[] parameters;


    public Command(CommandEnum commandType) {
        this.commandType = commandType;
    }

    public Command(CommandEnum commandType, String... parameters) {
        this.commandType = commandType;
        this.parameters = parameters;
    }

    public abstract void execute(Object o);

    public abstract Boolean validate(Object o);

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
