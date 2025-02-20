package simplexity.villagerinfo.interaction.logic;

public class SoundLogic {
    private static SoundLogic instance;

    private SoundLogic() {
    }

    public static SoundLogic getInstance() {
        if (instance == null) instance = new SoundLogic();
        return instance;
    }

   // public void runSoundEffect(org.bukkit.entity.Player player) {
   //     SoundEffectEvent soundEvent = callSoundEvent(player);
    //    if (soundEvent == null) return;
        //soundEvent.playSoundEffect();
    //}

    //public SoundEffectEvent callSoundEvent(org.bukkit.entity.Player player) {
        //SoundEffectEvent soundEffectEvent = new SoundEffectEvent(player);
        //Bukkit.getServer().getPluginManager().callEvent(soundEffectEvent);
        //if (soundEffectEvent.isCancelled()) return null;
        //return soundEffectEvent;
    //}
}
