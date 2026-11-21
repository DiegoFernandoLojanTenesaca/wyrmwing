package com.lautish.wyrmwing.init.mixin;

import com.lautish.wyrmwing.init.ModConfig;
import com.lautish.wyrmwing.util.WyrmwingFlyer;
import com.lautish.wyrmwing.util.WyrmwingSet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements WyrmwingFlyer {

    @Unique
    private static final EntityDataAccessor<Boolean> WYRMWING_FLYING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private boolean wyrmwingFlightGranted;

    @Unique
    private boolean wyrmwingSafeFall;

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Shadow
    public abstract Abilities getAbilities();

    @Shadow
    public abstract void onUpdateAbilities();

    @Shadow
    public abstract boolean isCreative();

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineWyrmwingFlying(SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(WYRMWING_FLYING, false);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickWyrmwingFlight(CallbackInfo ci) {
        if (!(level() instanceof ServerLevel))
            return;
        Abilities abilities = getAbilities();
        boolean wearingSet = ModConfig.flightEnabled && WyrmwingSet.isFullSet(this);
        if (wearingSet && !abilities.mayfly) {
            abilities.mayfly = true;
            wyrmwingFlightGranted = true;
            onUpdateAbilities();
        } else if (!wearingSet && wyrmwingFlightGranted) {
            wyrmwingFlightGranted = false;
            if (!isCreative() && !isSpectator()) {
                wyrmwingSafeFall = !onGround();
                abilities.mayfly = false;
                abilities.flying = false;
                onUpdateAbilities();
            }
        }
        if (onGround() || isInLiquid())
            wyrmwingSafeFall = false;
        else if (wyrmwingSafeFall)
            resetFallDistance();
        entityData.set(WYRMWING_FLYING, wyrmwingFlightGranted && abilities.flying);
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelWyrmwingLandingDamage(double fallDistance, float damageModifier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (wyrmwingSafeFall) {
            wyrmwingSafeFall = false;
            cir.setReturnValue(false);
        }
    }

    @Override
    public boolean isWyrmwingFlying() {
        return entityData.get(WYRMWING_FLYING);
    }
}
