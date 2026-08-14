package kr.moonscenty.createmobmasher.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mob_grinding_utils.client.ModelLayers;
import mob_grinding_utils.models.ModelSawBase;
import mob_grinding_utils.models.ModelSawBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MechanicalMobMasherItemRenderer extends BlockEntityWithoutLevelRenderer {

    private static final ResourceLocation BASE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mob_grinding_utils", "textures/tiles/saw_base.png");

    private static final ResourceLocation BLADE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mob_grinding_utils", "textures/tiles/saw_blade.png");

    private static final int COLOR_WHITE = -1;

    private final ModelSawBase sawBase;
    private final ModelSawBlade sawBlade;

    public MechanicalMobMasherItemRenderer(
            BlockEntityRenderDispatcher renderer,
            EntityModelSet modelSet
    ) {
        super(renderer, modelSet);

        EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
        this.sawBase = new ModelSawBase(entityModels.bakeLayer(ModelLayers.SAW_BASE));
        this.sawBlade = new ModelSawBlade(entityModels.bakeLayer(ModelLayers.SAW_BLADE));
    }

    @Override
    public void renderByItem(
            @Nonnull ItemStack stack,
            @Nonnull ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay
    ) {
        VertexConsumer baseBuffer =
                buffer.getBuffer(RenderType.entitySolid(BASE_TEXTURE));

        poseStack.pushPose();

        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        sawBase.renderToBuffer(
                poseStack,
                baseBuffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                COLOR_WHITE
        );

        sawBase.renderAxle(
                poseStack,
                baseBuffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                COLOR_WHITE
        );

        renderMace(poseStack, baseBuffer, packedLight, 45.0F);
        renderMace(poseStack, baseBuffer, packedLight, 165.0F);
        renderMace(poseStack, baseBuffer, packedLight, 285.0F);

        renderBlade(poseStack, buffer, packedLight, 0.0F, 0.20F, -0.16F, 8.0F);
        renderBlade(poseStack, buffer, packedLight, 0.0F, 0.00F, 0.16F, -8.0F);
        renderBlade(poseStack, buffer, packedLight, 0.0F, -0.20F, -0.16F, 8.0F);

        poseStack.popPose();
    }

    private void renderMace(
            PoseStack poseStack,
            VertexConsumer buffer,
            int packedLight,
            float yRotation
    ) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(yRotation));

        sawBase.renderMace(
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                COLOR_WHITE
        );

        poseStack.popPose();
    }

    private void renderBlade(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            float x,
            float y,
            float z,
            float xRotation
    ) {
        poseStack.pushPose();

        poseStack.translate(x, y, z);
        poseStack.mulPose(Axis.XP.rotationDegrees(xRotation));

        sawBlade.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entitySolid(BLADE_TEXTURE)),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                COLOR_WHITE
        );

        poseStack.popPose();
    }
}