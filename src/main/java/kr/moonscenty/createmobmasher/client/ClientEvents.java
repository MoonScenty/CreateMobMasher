package kr.moonscenty.createmobmasher.client;

import kr.moonscenty.createmobmasher.CreateMobMasher;
import kr.moonscenty.createmobmasher.registry.ModBlockEntities;
import mob_grinding_utils.client.ModelLayers;
import mob_grinding_utils.models.ModelSawBase;
import mob_grinding_utils.models.ModelSawBlade;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(
        modid = CreateMobMasher.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.MECHANICAL_MOB_MASHER.get(),
                MechanicalMobMasherRenderer::new
        );

        CreateMobMasher.LOG("Registered Mechanical Mob Masher renderer");
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.SAW_BASE, ModelSawBase::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.SAW_BLADE, ModelSawBlade::createBodyLayer);

        CreateMobMasher.LOG("Registered Mechanical Mob Masher model layers");
    }
}