package com.lautish.wyrmwing.display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AuraParticle extends SingleQuadParticle {
    private static final Identifier SPRITE = Identifier.withDefaultNamespace("generic_7");
    private static final float SIZE = 0.12f;
    private static final int FULL_BRIGHT = 0xF000F0;

    private final Player owner;
    private final Vec3 offset;
    private final float yaw;
    private final boolean followBody;

    private AuraParticle(ClientLevel level, Player owner, Vec3 offset, float yaw, boolean followBody, int color, float scale, int lifetime) {
        super(level, owner.getX(), owner.getY(), owner.getZ(), sprite());
        this.owner = owner;
        this.offset = offset;
        this.yaw = yaw;
        this.followBody = followBody;
        this.lifetime = lifetime;
        this.gravity = 0;
        this.hasPhysics = false;
        this.quadSize = SIZE * scale;
        setColor((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f);
        follow();
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
    }

    public static AuraParticle orbit(ClientLevel level, Player owner, Vec3 offset, float yaw, int color, float scale, int lifetime) {
        return new AuraParticle(level, owner, offset, yaw, false, color, scale, lifetime);
    }

    public static AuraParticle wing(ClientLevel level, Player owner, Vec3 offset, int color, float scale, int lifetime) {
        return new AuraParticle(level, owner, offset, 0, true, color, scale, lifetime);
    }

    private static TextureAtlasSprite sprite() {
        return Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.PARTICLES).getSprite(SPRITE);
    }

    private void follow() {
        float angle = (followBody ? owner.yBodyRot : yaw) * Mth.DEG_TO_RAD;
        double sin = Mth.sin(angle);
        double cos = Mth.cos(angle);
        this.x = owner.getX() + offset.x * cos - offset.z * sin;
        this.y = owner.getY() + offset.y;
        this.z = owner.getZ() + offset.x * sin + offset.z * cos;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime || owner.isRemoved()) {
            remove();
            return;
        }
        follow();
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    protected int getLightCoords(float partialTick) {
        return FULL_BRIGHT;
    }
}
