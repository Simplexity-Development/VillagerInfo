package simplexity.villagerinfo.configurations.locale;

/**
 * The messages that get inserted into other messages throughout the plugin.
 */
public enum MessageInsert {
    TOGGLE_TYPE_HIGHLIGHT("Highlight"),
    TOGGLE_TYPE_OUTPUT("Output"),
    TOGGLE_TYPE_SOUND("Sound"),
    TOGGLE_TYPE_INTERACT_PUNCH("Attack"),
    TOGGLE_TYPE_INTERACT_RIGHT_CLICK("Interact"),
    TOGGLE_TYPE_INTERACT_BOTH("Both"),
    ENABLED_MESSAGE_FORMAT("<green>Enabled</green>"),
    DISABLED_MESSAGE_FORMAT("<red>Disabled</red>"),
    TRUE_MESSAGE_FORMAT("<grey>True"),
    FALSE_MESSAGE_FORMAT("<grey>False"),
    NONE_MESSAGE_FORMAT("<grey>None"),
    NEVER_MESSAGE_FORMAT("<grey>Never"),
    EMPTY_MESSAGE_FORMAT("<grey>Empty"),
    JUST_NOW_FORMAT("<grey>Just Now"),
    POSITIVE_REPUTATION_BAR_FORMAT("<green>▬</green>"),
    NEUTRAL_REPUTATION_BAR_FORMAT("<grey>•</grey>"),
    NEGATIVE_REPUTATION_BAR_FORMAT("<red>▬</red>"),
    REPUTATION_TOTAL_FORMAT("<white>[<value>]</white>"),
    HOUR_MESSAGE_FORMAT("<value>H "),
    MINUTE_MESSAGE_FORMAT("<value>m "),
    SECOND_MESSAGE_FORMAT("<value>s "),
    AGO_MESSAGE_FORMAT("Ago"),
    LOCATION_X_FORMAT("<value>x, "),
    LOCATION_Y_FORMAT("<value>y, "),
    LOCATION_Z_FORMAT("<value>z");
    String message;
    MessageInsert(String message) {
        this.message = message;
    }

    public String getMessage() { return this.message; }

    public void setMessage(String message) { this.message = message; }
}
