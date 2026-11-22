package com.lautish.wyrmwing.init.mixin;

import com.lautish.wyrmwing.display.AuraEffects;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickAura(CallbackInfo ci) {
        AuraEffects.tick((AbstractClientPlayer) (Object) this);
    }
}
