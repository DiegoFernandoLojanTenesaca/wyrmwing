package com.lautish.wyrmwing.display;

import com.lautish.wyrmwing.init.ModConfig;
import com.lautish.wyrmwing.util.WyrmwingFlyer;
import com.lautish.wyrmwing.util.WyrmwingSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AuraEffects {
    private static final double VISIBLE_RANGE = 64.0;
    private static final float ORBIT_STEP = 14f;
    private static final int ORBIT_LIFETIME = 20;
    private static final int WING_INTERVAL = 2;
    private static final int WING_LIFETIME = 3;
    private static final float WING_SCALE = 0.6f;
    private static final double PORTAL_SPREAD = 0.35;
    private static final int VIOLET = 0x7B3FE4;
    private static final int DARK_VIOLET = 0x3D1673;
    private static final int LILAC = 0xA77BFF;
    private static final int WING_INNER = 0x6A28D9;
    private static final Vec3 LOW_RING = new Vec3(0.85, 0.15, 0);
    private static final Vec3 HIGH_RING = new Vec3(0.75, 0.9, 0);
    private static final List<Vec3> WING_OUTLINE = List.of(
            new Vec3(0.25, 1.45, -0.35), new Vec3(0.55, 1.65, -0.42), new Vec3(0.85, 1.82, -0.48), new Vec3(1.15, 1.9, -0.52),
            new Vec3(1.45, 1.85, -0.55), new Vec3(1.72, 1.65, -0.55), new Vec3(1.85, 1.38, -0.52), new Vec3(1.7, 1.18, -0.5),
            new Vec3(1.45, 1.08, -0.48), new Vec3(1.15, 1.0, -0.45), new Vec3(0.85, 0.98, -0.42), new Vec3(0.55, 1.05, -0.38));
    private static final List<Vec3> WING_INNER_POINTS = List.of(new Vec3(0.6, 1.35, -0.42), new Vec3(0.95, 1.45, -0.46), new Vec3(1.3, 1.5, -0.5));

    public static void tick(AbstractClientPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity camera = minecraft.getCameraEntity();
        if (!ModConfig.showAura || camera == null || !WyrmwingSet.isFullSet(player) || player.distanceToSqr(camera) > VISIBLE_RANGE * VISIBLE_RANGE)
            return;
        if (player == camera && minecraft.options.getCameraType().isFirstPerson() && !ModConfig.showOwnAuraFirstPerson)
            return;
        ClientLevel level = (ClientLevel) player.level();
        float yaw = level.getGameTime() * ORBIT_STEP;
        add(minecraft, AuraParticle.orbit(level, player, LOW_RING, yaw, VIOLET, 0.7f, ORBIT_LIFETIME));
        add(minecraft, AuraParticle.orbit(level, player, LOW_RING, yaw + 180, DARK_VIOLET, 0.8f, ORBIT_LIFETIME));
        add(minecraft, AuraParticle.orbit(level, player, HIGH_RING, yaw + 90, LILAC, 0.6f, ORBIT_LIFETIME));
        add(minecraft, AuraParticle.orbit(level, player, HIGH_RING, yaw + 270, VIOLET, 0.6f, ORBIT_LIFETIME));
        RandomSource random = player.getRandom();
        level.addParticle(ParticleTypes.REVERSE_PORTAL, player.getX() + random.nextGaussian() * PORTAL_SPREAD, player.getY() + 0.1,
                player.getZ() + random.nextGaussian() * PORTAL_SPREAD, 0, 0, 0);
        if (((WyrmwingFlyer) player).isWyrmwingFlying() && level.getGameTime() % WING_INTERVAL == 0) {
            addWing(minecraft, level, player, WING_OUTLINE, LILAC);
            addWing(minecraft, level, player, WING_INNER_POINTS, WING_INNER);
        }
    }

    private static void addWing(Minecraft minecraft, ClientLevel level, AbstractClientPlayer player, List<Vec3> points, int color) {
        for (Vec3 point : points) {
            add(minecraft, AuraParticle.wing(level, player, point, color, WING_SCALE, WING_LIFETIME));
            add(minecraft, AuraParticle.wing(level, player, point.multiply(-1, 1, 1), color, WING_SCALE, WING_LIFETIME));
        }
    }

    private static void add(Minecraft minecraft, AuraParticle particle) {
        minecraft.particleEngine.add(particle);
    }
}
