package com.lautish.wyrmwing.init.mixin;

import com.lautish.wyrmwing.init.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {

    @Unique
    private static final double SCALE_RANGE = 192.0;

    protected EnderDragonMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tickDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void giveWyrmScales(CallbackInfo ci) {
        if (ModConfig.dragonScaleDrops <= 0 || !(level() instanceof ServerLevel level))
            return;
        Item scale = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(MODID, "wyrm_scale"));
        for (ServerPlayer player : level.getPlayers(player -> player.isAlive() && player.distanceToSqr(this) <= SCALE_RANGE * SCALE_RANGE)) {
            ItemStack stack = new ItemStack(scale, ModConfig.dragonScaleDrops);
            player.getInventory().add(stack);
            if (!stack.isEmpty())
                player.spawnAtLocation(level, stack);
        }
    }
}
