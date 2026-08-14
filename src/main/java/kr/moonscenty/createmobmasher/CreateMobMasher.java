package kr.moonscenty.createmobmasher;

import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.CreateRegistrate;
import kr.moonscenty.createmobmasher.registry.ModBlockEntities;
import kr.moonscenty.createmobmasher.registry.ModBlocks;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(CreateMobMasher.MOD_ID)
public class CreateMobMasher {

    public static final String MOD_ID = "createmobmasher";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public CreateMobMasher(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);

        ModBlocks.register();
        ModBlockEntities.register();
/*
        if (FMLEnvironment.dist.isClient()) {
            CreateMobMasherClient.init(modEventBus);
        }*/
        LOG("Create Mob Masher loaded.");
    }

    public static void LOG(String message) {
        System.out.println("[CreateMobMasher] " + message);
    }
}