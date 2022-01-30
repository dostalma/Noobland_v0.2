package cz.dostalma.noobland.command;

import cz.dostalma.noobland.context.ContextLocationEnum;

public enum CommandEnum {

    EMPTY_COMMAND(ContextLocationEnum.UNIVERSAL),
    WRONG_COMMAND(ContextLocationEnum.UNIVERSAL),

    MENU(ContextLocationEnum.UNIVERSAL),

    CREATE_CHARACTER(ContextLocationEnum.MENU),
    SAVE_CHARACTER(ContextLocationEnum.MENU),
    LOAD_CHARACTER(ContextLocationEnum.MENU),
    CREATE_NEW_GAME(ContextLocationEnum.MENU),
    SAVE_GAME(ContextLocationEnum.MENU),
    LOAD_GAME(ContextLocationEnum.MENU),
    QUIT_GAME(ContextLocationEnum.MENU),

    MOVE(ContextLocationEnum.WORLD);

    ContextLocationEnum contextLocationEnum;

    CommandEnum(ContextLocationEnum contextLocationEnum) {
        this.contextLocationEnum = contextLocationEnum;
    }

    public ContextLocationEnum getContextLocationEnum() {
        return contextLocationEnum;
    }
}
