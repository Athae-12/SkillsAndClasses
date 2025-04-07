package com.athae.skillsandclasses.unit;

import com.athae.skillsandclasses.Classes.StatContainer.StatData;
import com.athae.skillsandclasses.entity.data.EntityData;
import com.athae.skillsandclasses.items.GearItemData;
import com.athae.skillsandclasses.util.StackSaving;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GearData {

    public ItemStack stack;
    public GearItemData gear;
    public EquipmentSlot slot;
    public int percentStatUtilization = 100; // todo if stats change stat utilization, they need special handling..

    public List<StatContext> cachedStats = new ArrayList<>();

    // public List<ExactStatData> cachedEnchantStats = new ArrayList<>();

    public GearData(ItemStack stack, EquipmentSlot slot, EntityData data) {
        this.stack = stack;

        if (stack != null) {
            this.gear = StackSaving.GEARS.loadFrom(stack);
        }


        this.slot = slot;

        if (stack != null) {
            var ex = ExileStack.of(stack);

            var gear = ex.get(StackKeys.GEAR).get();

            if (gear != null) {
                calcStatUtilization(data);

                List<StatData> stats = gear.GetAllStats(ex);
                if (percentStatUtilization != 100) {
                    // multi stats like for offfhand weapons
                    float multi = percentStatUtilization / 100F;
                    stats.forEach(s -> s.multiplyBy(multi));
                }
                cachedStats.add(GearStatCtx.of(gear, stats));
                /*
                var ench = gear.getEnchantCompatStats(stack);
                if (ench != null) {
                    cachedStats.add(ench);
                }

                 */
            } else {
                percentStatUtilization = 0;
            }
        } else {
            percentStatUtilization = 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GearData == false) {
            return false;
        }
        GearData other = (GearData) obj;

        return (ItemStack.matches(stack, other.stack));
    }

    private void calcStatUtilization(EntityData data) {
        if (slot == EquipmentSlot.OFFHAND) {
            if (gear != null) {
                if (gear.GetBaseGearType().getTags().contains(SlotTags.offhand_family)) {
                    percentStatUtilization = 100;
                }
                if (gear.GetBaseGearType().weaponType().can_dual_wield) {
                    if (gear.GetBaseGearType().getTags().contains(SlotTags.weapon_family)) {
                        percentStatUtilization = ServerContainer.get().PERC_OFFHAND_WEP_STAT.get();
                    }
                }
            }
        }
    }

    public boolean isUsableBy(EntityData data) {
        if (stack == null) {
            return false;
        }
        if (gear == null) {
            return false;
        }

        if (stack.isDamageableItem()) {
            if (RepairUtils.isItemBroken(stack)) {
                return false;
            }
        }
        if (!gear.isValidItem()) {
            return false;
        }

        if (gear.lvl > data.getLevel()) {
            return false;
        }

        BaseGearType type = gear.GetBaseGearType();

        if (type.isWeapon()) {
            if (type.weaponType().can_dual_wield) {
                if (slot == EquipmentSlot.OFFHAND) {
                    return true;
                }
            }

            return slot == EquipmentSlot.MAINHAND; // ranged weapon
        }
        if (type.tags.contains(SlotTags.chest)) {
            return slot == EquipmentSlot.CHEST;
        }
        if (type.tags.contains(SlotTags.pants)) {
            return slot == EquipmentSlot.LEGS;
        }
        if (type.tags.contains(SlotTags.boots)) {
            return slot == EquipmentSlot.FEET;
        }
        if (type.tags.contains(SlotTags.helmet)) {
            return slot == EquipmentSlot.HEAD;
        }
        if (type.tags.contains(SlotTags.jewelry_family)) {
            return slot == null;
        }
        if (type.tags.contains(SlotTags.offhand_family)) {
            return slot == EquipmentSlot.OFFHAND;
        }

        // if this causes problems, return to false instead
        return true;
    }
}