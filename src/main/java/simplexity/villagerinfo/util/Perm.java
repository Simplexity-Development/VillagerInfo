package simplexity.villagerinfo.util;

public enum Perm {
    VILL_INFO_OUTPUT("villagerinfo.output"),
    VILL_COMMAND_BASE("villagerinfo.commands"),
    VILL_COMMAND_TOGGLE("villagerinfo.commands.toggle"),
    VILL_COMMAND_TOGGLE_OUTPUT("villagerinfo.commands.toggle.output"),
    VILL_COMMAND_TOGGLE_HIGHLIGHT("villagerinfo.commands.toggle.highlight"),
    VILL_COMMAND_TOGGLE_SOUND("villagerinfo.commands.toggle.sound"),
    VILL_COMMAND_TOGGLE_INTERACT_TYPE("villagerinfo.commands.toggle.interact-type");
    final String perm;

    Perm(String perm) {
        this.perm = perm;
    }

    public String getPerm() {
        return this.perm;
    }

}
