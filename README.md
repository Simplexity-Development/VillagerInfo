# <img src="https://user-images.githubusercontent.com/45906780/183763500-443408eb-fc1d-4cd8-a489-cf7dbe6be898.png" width="50" height="50"> VillagerInfo

[![wakatime](https://wakatime.com/badge/github/Simplexity-Development/VillagerInfo.svg?style=flat-square)](https://wakatime.com/badge/github/Simplexity-Development/VillagerInfo)
[![Discord](https://img.shields.io/badge/Discord-join-7289DA?logo=discord&logoColor=7289DA&style=flat-square)](https://discord.gg/qe3YQrbegA)
[![Ko-Fi Support Link](https://img.shields.io/badge/Simplexity-Ko--fi-FF5E5B?logo=ko-fi&style=flat-square)](https://ko-fi.com/simple_dev)

### This plugin aims to make accessing useful information that is stored in villagers brains, easier.

Accessing the information in villager's brains is useful, but `/data get` has much more information than is needed for most things.

## **Default list provided through VillagerInfo**

| =+=+=                                                                                                                                                                        | =+=+=                                                                                                                                                                           | =+=+=                                                                                                                                                                      |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Hover Element                                                                                                                                                                | Inventory Hover Element                                                                                                                                                         | Baby Villager Display                                                                                                                                                      |
| <img src="https://github.com/Simplexity-Development/VillagerInfo-Rewrite/assets/45906780/5c70f995-7843-4009-afc5-0b391fbd51ba" width = 50% alt = "Hover Element">            | <img src="https://github.com/Simplexity-Development/VillagerInfo-Rewrite/assets/45906780/8ab98daf-b2a9-453d-83e9-dd0afe55ef42" width = 50% alt = "Inventory Hover Element">     | <img src="https://github.com/Simplexity-Development/VillagerInfo-Rewrite/assets/45906780/69f67abf-0316-402f-9bb4-9c71d1b806df" width = 50% alt = "Child Villager Display"> |
| Villager Output With Job                                                                                                                                                     | Villager Output Without Job                                                                                                                                                     | Zombie Villager Output                                                                                                                                                     |
| <img src="https://github.com/Simplexity-Development/VillagerInfo-Rewrite/assets/45906780/5a544d24-520e-43ba-ac82-4112990c59a5" width = 50% alt = "Villager Output With Job"> | <img src="https://github.com/Simplexity-Development/VillagerInfo-Rewrite/assets/45906780/68ada584-a19e-498d-949e-bfadc965d4ab" width = 50% alt = "Villager Output Without Job"> | <img src="https://github.com/Simplexity-Development/VillagerInfo-Rewrite/assets/45906780/fe97f18a-f40a-40d0-bdfa-078e245c0fc5" width = 50% alt = "Zombie Villager Output"> |

### **Default minecraft /data get**!


<img src="https://user-images.githubusercontent.com/45906780/204659030-d2ffabb8-1a1c-461b-8032-209441c2dad5.png" width = 50% alt="A large wall of text showing all the attributes and information of a minecraft villager">

## Permissions

* `villagerinfo.reload`
    - Default: **OP only**
    - Description: Allows the Player to use `/villreload` to reload the VillagerInfo configurations.
    - Unlocks: Reloads the VillagerInfo configurations.

* `villagerinfo.output`
    - Default: **Enabled**
    - Description: Allows the player to see the information from a villager using crouch-interact.
    - Unlocks: Ability to view information from a villager using crouch-interact.

* `villagerinfo.commands`
    - Default: **Enabled**
    - Description: Allows the player to use commands to adjust their preferences on how VillagerInfo will behave for them (`/villinfo`).
    - Unlocks: Access to command-based adjustments for VillagerInfo behavior.

* `villagerinfo.commands.help`
    - Default: **Enabled**
    - Description: Allows the player to use the help command to see how the plugin functions.
    - Unlocks: Access to the help command for understanding the plugin's functionality.

* `villagerinfo.commands.toggle`
    - Default: **Enabled**
    - Description: Allows the player to use commands to toggle certain behaviors in the VillagerInfo plugin (`/villinfo toggle`).
    - Unlocks: Ability to toggle specific behaviors within the VillagerInfo plugin.

* `villagerinfo.commands.toggle.output`
    - Default: **Enabled**
    - Description: Allows the player to toggle whether they want VillagerInfo to output on crouch-interact or not (`/villinfo toggle output`).
    - Unlocks: Toggles the output of VillagerInfo on crouch-interact.

* `villagerinfo.commands.toggle.highlight`
    - Default: **Enabled**
    - Description: Allows the player to toggle whether they want VillagerInfo to highlight a villager's workstation on crouch-interact or not (`/villinfo toggle highlight`).
    - Unlocks: Toggles the highlighting of a villager's workstation on crouch-interact.

* `villagerinfo.commands.toggle.sound`
    - Default: **Enabled**
    - Description: Allows the player to toggle whether they want VillagerInfo to output sound on crouch-interact or not (`/villinfo toggle sound`).
    - Unlocks: Toggles the sound output of VillagerInfo on crouch-interact.

## Commands

* `villreload`
    - Permission: `villagerinfo.reload`
    - Description: Reloads the VillagerInfo configuration
    - Aliases: `none`

* `villagerinfo`
    - Permission: `villagerinfo.commands`
    - Description: Base command for VillagerInfo player commands
    - Aliases:
        - `vinfo`
        - `villinfo`
        - `vi`

* `villagerinfo help`
    - Permission: `villagerinfo.commands.help`
    - Description: Command that shows a player the help screen

* `villagerinfo toggle`
    - Permission: `villagerinfo.commands.toggle`
    - Description: Command that allows a player to toggle personal settings related to Villager Info functionality

* `villagerinfo toggle highlight`
    - Permission: `villagerinfo.commands.toggle.highlight`
    - Description: Command that allows a player to prevent a Villager's workstation from being highlighted when they crouch-interact with a villager

* `villagerinfo toggle output`
    - Permission: `villagerinfo.commands.toggle.output`
    - Description: Command that allows a player to prevent a Villager's information from being output when they crouch-interact with a villager

* `villagerinfo toggle sound`
    - Permission: `villagerinfo.commands.toggle.sound`
    - Description: Command that allows a player to prevent sound from playing when they crouch-interact with a villager

## API
For the time being, the current way to import this project is through the modrinth maven
Remember to check what the current version is - these may not be up-to-date
### [Javadocs](https://simplexity-development.github.io/VillagerInfo/)
### Maven Import

```xml
<repository>
  <id>modrinth-repo</id>
  <url>https://api.modrinth.com/maven/</url>
</repository>

<dependency>
  <groupId>maven.modrinth</groupId>
  <artifactId>villager-info</artifactId>
  <version>3.0.1</version>
  <scope>provided</scope>
</dependency>

```

### Gradle Import
```gradle
exclusiveContent {
    forRepository { maven { url = "https://api.modrinth.com/maven" } }
    filter { includeGroup "maven.modrinth" }
}

dependencies {
    compileOnly 'maven.modrinth:villager-info:3.0.1'
}
```
