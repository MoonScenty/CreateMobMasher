package kr.moonscenty.createmobmasher.registry;

import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.entry.BlockEntry;
import kr.moonscenty.createmobmasher.CreateMobMasher;
import kr.moonscenty.createmobmasher.content.MechanicalMobMasherBlock;
import kr.moonscenty.createmobmasher.content.MechanicalMobMasherBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import static kr.moonscenty.createmobmasher.CreateMobMasher.REGISTRATE;

public class ModBlocks {

    public static final BlockEntry<MechanicalMobMasherBlock> MECHANICAL_MOB_MASHER =
            REGISTRATE.block("mechanical_mob_masher", MechanicalMobMasherBlock::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .properties(p -> p
                            .mapColor(MapColor.METAL)
                            .strength(5.0F, 6.0F)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                    )
                    .item(MechanicalMobMasherBlockItem::new)
                    .build()
                    .onRegister(block ->
                            BlockStressValues.IMPACTS.register(block, () -> 16.0)
                    )
                    .register();

    public static final BlockEntry<Block> SHAFT_HALF =
            REGISTRATE.block("shaft_half", Block::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .properties(p -> p
                            .mapColor(MapColor.METAL)
                            .strength(5.0F, 6.0F)
                            .noOcclusion()
                    )
                    .blockstate((ctx, prov) -> prov.simpleBlock(
                            ctx.get(),
                            prov.models().getExistingFile(
                                    ResourceLocation.fromNamespaceAndPath(
                                            CreateMobMasher.MOD_ID,
                                            "block/shaft_half"
                                    )
                            )
                    ))
                    .register();

    public static void register() {
        CreateMobMasher.LOG("Registering blocks");
    }
}