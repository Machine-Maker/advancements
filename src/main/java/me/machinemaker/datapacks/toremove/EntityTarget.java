package me.machinemaker.datapacks.toremove;

// TODO remove when api is added
public enum EntityTarget {
    THIS("this"),
    KILLER("killer"),
    DIRECT_KILLER("direct_killer"),
    KILLER_PLAYER("killer_player"),
    ;

    private final String name;

    EntityTarget(final String name) {
        this.name = name;
    }
}
