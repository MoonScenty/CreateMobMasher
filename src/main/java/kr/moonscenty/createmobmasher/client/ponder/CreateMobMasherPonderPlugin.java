package kr.moonscenty.createmobmasher.client.ponder;

import kr.moonscenty.createmobmasher.CreateMobMasher;
import kr.moonscenty.createmobmasher.registry.ModBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CreateMobMasherPonderPlugin implements PonderPlugin {

    private static final ResourceLocation SCHEMATIC =
            ResourceLocation.fromNamespaceAndPath("create", "gearbox");

    @Override
    public String getModId() {
        return CreateMobMasher.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.addStoryBoard(
                ModBlocks.MECHANICAL_MOB_MASHER.getId(),
                SCHEMATIC,
                MechanicalMobMasherScenes::mechanicalMobMasher
        );
    }
}
