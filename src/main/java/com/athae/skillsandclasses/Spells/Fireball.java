package com.athae.skillsandclasses.Spells;

import net.minecraft.world.entity.player.Player;

public class Fireball extends Skill {

    public Fireball() {
        super("Fireball", 10, 20, "fire_particle", "fire", 50, 30);
    }

    @Override
    public void activate(Player player) {
        // Implement the fireball skill activation logic with particle effects, damage, etc.
    }
}