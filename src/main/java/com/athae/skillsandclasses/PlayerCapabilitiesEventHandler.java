   @Mod.EventBusSubscriber(modid = Skillsandclasses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
   public class PlayerEventHandler {

       @SubscribeEvent
       public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
           if (event.getObject() instanceof Player) {
               event.addCapability(new ResourceLocation(Skillsandclasses.MODID, "player_stats"), new PlayerStatsCapability.Provider());
           }
       }
   }