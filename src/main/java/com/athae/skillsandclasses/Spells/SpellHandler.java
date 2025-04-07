package com.athae.skillsandclasses.Spells;

import com.athae.skillsandclasses.Skillsandclasses;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skillsandclasses.MODID)
public class SpellHandler {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ServerLevel level = player.getLevel();
        Vec3 position = player.position();

        // Example: Summon Undead Warrior
        SummonSpell summonSpell = new SummonSpell(10);
        summonSpell.summon(level, position.x, position.y, position.z, "summon_undead_warrior");

        // Example: Summon Army
        SummonArmy.summonArmy(level);
    }
}