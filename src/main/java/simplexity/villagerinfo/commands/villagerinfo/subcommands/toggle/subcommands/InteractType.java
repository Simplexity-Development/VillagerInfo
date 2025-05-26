package simplexity.villagerinfo.commands.villagerinfo.subcommands.toggle.subcommands;

import simplexity.villagerinfo.configurations.locale.MessageInsert;

public enum InteractType {
    PUNCH("attack", MessageInsert.TOGGLE_TYPE_INTERACT_PUNCH),
    RIGHT_CLICK("interact", MessageInsert.TOGGLE_TYPE_INTERACT_RIGHT_CLICK),
    BOTH("both", MessageInsert.TOGGLE_TYPE_INTERACT_BOTH);

    final String commandName;
    final MessageInsert messageInsert;

    InteractType(String name, MessageInsert messageInsert) {
        this.commandName = name;
        this.messageInsert = messageInsert;

    }

    public String getCommandName() {
        return this.commandName;
    }

    public MessageInsert getMessageInsert(){
        return this.messageInsert;
    }


    public static InteractType getInteractType(String string){
        for (InteractType type : InteractType.values()) {
            if (string.equals(type.getCommandName())) return type;
        }
        return null;
    }
}
