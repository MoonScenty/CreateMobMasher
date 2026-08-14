package kr.moonscenty.createmobmasher;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import kr.moonscenty.createmobmasher.registry.ModBlockEntities;
import kr.moonscenty.createmobmasher.registry.ModBlocks;
import kr.moonscenty.createmobmasher.registry.ModCreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CreateMobMasher.MOD_ID)
public class CreateMobMasher {

    public static final String MOD_ID = "createmobmasher";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .setTooltipModifierFactory(item -> TooltipModifier.mapNull(KineticStats.create(item)));

    public CreateMobMasher(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
        REGISTRATE.defaultCreativeTab(ModCreativeModeTabs.MAIN_KEY);

        ModBlocks.register();
        ModBlockEntities.register();
        ModCreativeModeTabs.register(modEventBus);

        LOG("Create Mob Masher loaded.");
    }

    public static void LOG(String message) {
        System.out.println("[CreateMobMasher] " + message);
    }
}
