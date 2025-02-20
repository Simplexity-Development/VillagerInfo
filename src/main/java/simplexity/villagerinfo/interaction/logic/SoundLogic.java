package simplexity.villagerinfo.interaction.logic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import simplexity.villagerinfo.configurations.functionality.VillConfig;
import simplexity.villagerinfo.events.SoundEffectEvent;

public class SoundLogic {

    public static void outputSound(Player player){
        SoundEffectEvent soundEvent = callSoundEvent(player);
        if (soundEvent == null) return;
        player.playSound(
                soundEvent.getLocation(),
                soundEvent.getSound(),
                soundEvent.getVolume(),
                soundEvent.getPitch()
        );
    }

    private static SoundEffectEvent callSoundEvent(Player player) {
        SoundEffectEvent soundEvent = new SoundEffectEvent(
                player,
                VillConfig.getInstance().getConfiguredSound(),
                VillConfig.getInstance().getConfiguredSoundVolume(),
                VillConfig.getInstance().getConfiguredSoundPitch(),
                player.getLocation());
        Bukkit.getPluginManager().callEvent(soundEvent);
        if (soundEvent.isCancelled()) return null;
        return soundEvent;
    }


}
