package kr.moonscenty.createmobmasher.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import kr.moonscenty.createmobmasher.content.MechanicalMobMasherBlockEntity;

import static kr.moonscenty.createmobmasher.CreateMobMasher.REGISTRATE;

public class ModBlockEntities {

    public static final BlockEntityEntry<MechanicalMobMasherBlockEntity> MECHANICAL_MOB_MASHER =
            REGISTRATE
                    .blockEntity("mechanical_mob_masher", MechanicalMobMasherBlockEntity::new)
                    .validBlocks(ModBlocks.MECHANICAL_MOB_MASHER)
                    .register();

    public static void register() {
    }
}
