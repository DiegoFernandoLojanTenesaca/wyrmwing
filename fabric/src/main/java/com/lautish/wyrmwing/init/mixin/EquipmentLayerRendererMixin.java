package com.lautish.wyrmwing.init.mixin;

import com.lautish.wyrmwing.init.ModConfig;
import com.lautish.wyrmwing.init.ModMaterials;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

@Mixin(EquipmentLayerRenderer.class)
public abstract class EquipmentLayerRendererMixin {
    @Unique
    private static final Identifier GLOW = Identifier.fromNamespaceAndPath(MODID, "textures/entity/equipment/humanoid/wyrmwing_glow.png");
    @Unique
    private static final Identifier LEGGINGS_GLOW = Identifier.fromNamespaceAndPath(MODID, "textures/entity/equipment/humanoid_leggings/wyrmwing_glow.png");
    @Unique
    private static final int GLOW_ORDER = 3;

    @Inject(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At("TAIL"))
    private <S> void renderGlow(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> assetId, Model<? super S> model, S state, ItemStack stack, PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, Identifier playerTexture, int outlineColor, int order, CallbackInfo ci) {
        if (!ModConfig.armorGlow || !ModMaterials.Armor.WYRMWING.assetId().equals(assetId)) return;
        Identifier texture = layerType == EquipmentClientInfo.LayerType.HUMANOID ? GLOW
                : layerType == EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS ? LEGGINGS_GLOW : null;
        if (texture != null)
            collector.order(order + GLOW_ORDER).submitModel(model, state, poseStack, RenderTypes.armorCutoutNoCull(texture), LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, outlineColor);
    }
}
