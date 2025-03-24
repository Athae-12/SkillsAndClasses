package com.athae.skillsandclasses.items;

import com.athae.skillsandclasses.playerStats.PlayerStats;
import com.athae.skillsandclasses.playerStats.PlayerStatsCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PotionOfTrueSelf extends Item {
    public PotionOfTrueSelf(Properties properties) {
        super(properties);
    }


    public ItemStack finishUsingItem(ItemStack stack, Level world, Player player) {
        if (!world.isClientSide) {
            PlayerStats stats = player.getCapability(PlayerStatsCapability.STATS_CAPABILITY).orElse(null);
            if (stats != null) {
                stats.resetStats();
            }
        }
        return super.finishUsingItem(stack, world, player);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}