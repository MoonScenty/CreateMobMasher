package kr.moonscenty.createmobmasher.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import kr.moonscenty.createmobmasher.content.MechanicalMobMasherBlockEntity;
import kr.moonscenty.createmobmasher.registry.ModBlocks;
import mob_grinding_utils.client.ModelLayers;
import mob_grinding_utils.models.ModelSawBase;
import mob_grinding_utils.models.ModelSawBlade;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalMobMasherRenderer implements BlockEntityRenderer<MechanicalMobMasherBlockEntity> {

    private static final ResourceLocation BASE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mob_grinding_utils", "textures/tiles/saw_base.png");

    private static final ResourceLocation BLADE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mob_grinding_utils", "textures/tiles/saw_blade.png");

    private static final int COLOR_WHITE = -1;

    private final ModelSawBase sawBase;
    private final ModelSawBlade sawBlade;

    public MechanicalMobMasherRenderer(BlockEntityRendererProvider.Context context) {
        this.sawBase = new ModelSawBase(context.bakeLayer(ModelLayers.SAW_BASE));
        this.sawBlade = new ModelSawBlade(context.bakeLayer(ModelLayers.SAW_BLADE));
    }

    @Override
    public void render(
            MechanicalMobMasherBlockEntity be,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay
    ) {
        if (be == null || !be.hasLevel()) {
            return;
        }

        renderShaftHalf(be, poseStack, buffer, packedLight);
        renderMobMasher(be, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderShaftHalf(
            MechanicalMobMasherBlockEntity be,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        BlockState shaftHalfState = ModBlocks.SHAFT_HALF.getDefaultState();

        SuperByteBuffer shaftBuffer =
                CachedBuffers.block(
                        KineticBlockEntityRenderer.KINETIC_BLOCK,
                        shaftHalfState
                );

        KineticBlockEntityRenderer.renderRotatingBuffer(
                be,
                shaftBuffer,
                poseStack,
                buffer.getBuffer(RenderType.solid()),
                packedLight
        );
    }

    private void renderMobMasher(
            MechanicalMobMasherBlockEntity be,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        VertexConsumer baseBuffer = buffer.getBuffer(RenderType.entitySolid(BASE_TEXTURE));

        poseStack.pushPose();

        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0F, -1.0F, 0.0F);

        sawBase.renderToBuffer(
                poseStack,
                baseBuffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                COLOR_WHITE
        );

        poseStack.pushPose();

        /*
        float speed = Math.abs(be.getSpeed());
        float angle = (be.getLevel().getGameTime() + partialTicks) * speed * 2.0F;

        poseStack.mulPose(Axis.YP.rotationDegrees(angle));*/

        float speed = Math.abs(be.getSpeed());

        float angle = 0.0F;

        if (speed >= 128.0F) {
            float renderMultiplier = net.minecraft.util.Mth.clamp(speed, 128.0F, 256.0F) / 256.0F;

            angle = (be.getLevel().getGameTime() + partialTicks)
                    * speed
                    * 2.0F
                    * renderMultiplier;
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

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