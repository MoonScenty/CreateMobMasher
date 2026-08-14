package kr.moonscenty.createmobmasher.client;

import kr.moonscenty.createmobmasher.CreateMobMasher;
import kr.moonscenty.createmobmasher.client.ponder.CreateMobMasherPonderPlugin;
import kr.moonscenty.createmobmasher.registry.ModBlockEntities;
import kr.moonscenty.createmobmasher.registry.ModBlocks;
import mob_grinding_utils.client.ModelLayers;
import mob_grinding_utils.models.ModelSawBase;
import mob_grinding_utils.models.ModelSawBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.createmod.ponder.foundation.PonderIndex;

@EventBusSubscriber(
        modid = CreateMobMasher.MOD_ID,
        value = Dist.CLIENT
)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> PonderIndex.addPlugin(new CreateMobMasherPonderPlugin()));
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private MechanicalMobMasherItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new MechanicalMobMasherItemRenderer(
                            Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                            Minecraft.getInstance().getEntityModels()
                    );
                }

                return renderer;
            }
        }, ModBlocks.MECHANICAL_MOB_MASHER.get().asItem());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.MECHANICAL_MOB_MASHER.get(),
                MechanicalMobMasherRenderer::new
        );
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.SAW_BASE, ModelSawBase::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.SAW_BLADE, ModelSawBlade::createBodyLayer);
    }
}
