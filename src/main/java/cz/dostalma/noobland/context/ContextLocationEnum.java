package cz.dostalma.noobland.context;

public enum ContextLocationEnum {

    MENU("menu"),
    WORLD("world"),
    BATTLE("battle"),
    DIALOG("dialog");


    String name;

    ContextLocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
