package simplexity.villagerinfo.configurations.locale;

/**
 * Feedback messages.
 */
public enum ServerMessage {
    TOGGLE_COMMAND_FEEDBACK("<plugin_prefix> <gray><value> Toggled <u><state>"),
    LOGGER_INVALID_LOCALE_KEY("Invalid locale key found: "),
    LOGGER_INVALID_TOGGLE_KEY("Invalid toggle key found: "),
    NOT_ENOUGH_ARGUMENTS("<red>Not enough arguments were provided."),
    NO_PERMISSION("<red>You don't have permission to use this command!"),
    SUBCOMMAND_DOES_NOT_EXIST("<red>No known subcommand by the name of: "),
    CONFIG_AND_LOCALE_RELOADED("<plugin_prefix> <gold>VillagerInfo Config and Locale Reloaded!"),
    HELP_MAIN("<plugin_prefix> <#4dd5ff> • How to use Villager Info\n<grey>Crouch-interact with a villager while one or more toggles are enabled to have a villager's information displayed"),
    HELP_TOGGLE_BASE("\n<#4dd5ff><click:suggest_command:'/vi toggle'><hover:show_text:'<#4dd5ff>/vi toggle'><u> • /vi toggle</u></hover></click>\n<grey>Sets your preference on what parts of the plugin should be enabled for you"),
    HELP_TOGGLE_HIGHLIGHT("\n<#4dd5ff><click:suggest_command:'/vi toggle highlight'><hover:show_text:'<#4dd5ff>/vi toggle highlight'><u> • /vi toggle highlight</u>\n<grey>Sets your preference on whether or not you would like the plugin to highlight a villager's workstation when you crouch-interact with them"),
    HELP_TOGGLE_SOUND("\n<#4dd5ff><click:suggest_command:'/vi toggle sound'><hover:show_text:'<#4dd5ff>/vi toggle sound'><u> • /vi toggle sound</u></hover></click>\n<grey>Sets your preference on whether or not you would like a sound to play when you crouch-interact with a villager"),
    HELP_TOGGLE_OUTPUT("\n<#4dd5ff><click:suggest_command:'/vi toggle output'><hover:show_text:'<#4dd5ff>/vi toggle output'><u> • /vi toggle output</u></hover></click>\n<grey>Sets your preference on whether or not you would like text output to display when you crouch-interact with a villager"),
    NOT_A_PLAYER("<red>Sorry, you must be a player to use this command"),
    CONFIGURATION_ERROR_PREFIX("Configuration Error: "),
    CONFIGURED_SOUND_ERROR(" is not a valid sound! Setting sound to 'BLOCK_AMETHYST_BLOCK_BREAK' until a valid sound is provided"),
    CONFIGURED_HIGHLIGHT_TIME_ERROR("Invalid highlight time. If you would like to disable this feature, please set 'highlight-workstation' to 'false'. Otherwise please use an integer greater than zero. Setting value to 10s until a valid number is supplied"),
    CONFIGURED_VOLUME_ERROR("'sound-volume' must be between 0.0 and 2.0. Setting to 0.5 until a valid number is provided"),
    CONFIGURED_PITCH_ERROR("'sound-pitch' must be between 0.0 and 2.0. Setting to 1.5 until a valid number is provided"),
    NO_CONFIG_TOGGLES_FOUND("No info toggles could be found in the configuration file."),
    NO_LOCALE_SECTION_FOUND("No locale section was found for: "),
    NO_COLOR_SECTION_FOUND("No highlight colors section was found in your config."),
    ERROR_NOT_A_MATERIAL(" is not a material. Please be sure to check your syntax."),
    ERROR_CHECK_FOR_TABS("Please check that you did not use TAB instead of SPACE"),
    ERROR_COLOR_DECLARED_INCORRECTLY("There was not 3 elements in your configuration for your highlight color. Please check that you have declared this in the RR, GG, BB format. Error caused by: "),
    ERROR_NO_FUNCTIONALITY_ENABLED(" Your config settings have 'output-enabled', 'play-sound-on-output', and 'highlight-workstation-on-output' all set to false - this disables all functionality within this plugin."),
    PLUGIN_PREFIX("<#3256a8><bold>[</bold><#4dd5ff>Villager Info<#3256a8><bold>]<reset>");

    String message;

    ServerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
