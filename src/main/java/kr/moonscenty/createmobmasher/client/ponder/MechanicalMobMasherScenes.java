package kr.moonscenty.createmobmasher.client.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import kr.moonscenty.createmobmasher.content.MechanicalMobMasherBlockEntity;
import kr.moonscenty.createmobmasher.registry.ModBlocks;
import mob_grinding_utils.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class MechanicalMobMasherScenes {

    public static void mechanicalMobMasher(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("mechanical_mob_masher", "Using the Mechanical Mob Masher");
        scene.configureBasePlate(1, 1, 5);
        scene.setSceneOffsetY(-1);

        BlockPos masherPos = util.grid().at(3, 2, 3);
        BlockPos gearboxPos = masherPos.below();
        BlockPos motorPos = gearboxPos.west();

        Selection masher = util.select().position(masherPos);
        Selection gearbox = util.select().position(gearboxPos);
        Selection motor = util.select().position(motorPos);
        Selection kinetics = masher.add(gearbox).add(motor);

        scene.world().setBlocks(util.select().fromTo(0, 1, 0, 6, 5, 6), Blocks.AIR.defaultBlockState(), false);
        scene.world().setBlock(masherPos, ModBlocks.MECHANICAL_MOB_MASHER.getDefaultState(), false);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);
        scene.world().showSection(masher, Direction.DOWN);
        scene.idle(15);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().centerOf(masherPos))
                .text("The Mechanical Mob Masher requires rotational force to operate");
        scene.idle(80);

        scene.world().setBlock(
                gearboxPos,
                AllBlocks.GEARBOX.getDefaultState()
                        .setValue(RotatedPillarKineticBlock.AXIS, Direction.Axis.Z),
                false
        );
        scene.world().setBlock(
                motorPos,
                AllBlocks.CREATIVE_MOTOR.getDefaultState()
                        .setValue(DirectionalKineticBlock.FACING, Direction.EAST),
                false
        );
        scene.world().showSection(gearbox, Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(motor, Direction.EAST);
        scene.idle(10);
        scene.world().setKineticSpeed(kinetics, 128);
        scene.effects().rotationSpeedIndicator(masherPos);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .colored(PonderPalette.GREEN)
                .placeNearTarget()
                .pointAt(util.vector().centerOf(masherPos))
                .text("It starts working at a minimum speed of 128 RPM");
        scene.idle(80);

        ItemStack sharpnessUpgrade = new ItemStack(ModItems.SAW_UPGRADE_SHARPNESS.get());
        scene.overlay().showControls(util.vector().topOf(masherPos), Pointing.DOWN, 45)
                .rightClick()
                .withItem(sharpnessUpgrade);
        scene.world().modifyBlockEntity(
                masherPos,
                MechanicalMobMasherBlockEntity.class,
                blockEntity -> blockEntity.addUpgrade(sharpnessUpgrade.copy())
        );
        scene.idle(10);

        scene.overlay().showText(65)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(masherPos))
                .text("Right-click with Mob Grinding Utils saw upgrades to install them");
        scene.idle(75);

        scene.overlay().showControls(util.vector().topOf(masherPos), Pointing.DOWN, 45)
                .rightClick()
                .whileSneaking();
        scene.world().modifyBlockEntity(
                masherPos,
                MechanicalMobMasherBlockEntity.class,
                MechanicalMobMasherBlockEntity::removeLastUpgrade
        );
        scene.world().createItemEntity(
                util.vector().topOf(masherPos),
                util.vector().of(0.12, 0.18, 0),
                sharpnessUpgrade.copy()
        );
        scene.idle(10);

        scene.overlay().showText(65)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(masherPos))
                .text("Sneak-right-click with an empty hand to remove an installed upgrade");
        scene.idle(75);

        Vec3 zombiePosition = util.vector().centerOf(masherPos).subtract(0, 0.45, 0);
        ElementLink<EntityElement> zombie = scene.world().createEntity(level -> {
            Zombie entity = Objects.requireNonNull(EntityType.ZOMBIE.create(level));
            entity.setPos(zombiePosition.x, zombiePosition.y, zombiePosition.z);
            entity.setNoAi(true);
            entity.setYRot(210);
            entity.yRotO = 210;
            return entity;
        });
        scene.idle(20);

        scene.overlay().showText(75)
                .attachKeyFrame()
                .colored(PonderPalette.RED)
                .placeNearTarget()
                .pointAt(zombiePosition.add(0, 1, 0))
                .text("It attacks every nearby living entity, whether friend or foe");
        scene.idle(75);
        scene.effects().indicateSuccess(masherPos);
        scene.world().modifyEntity(zombie, Entity::discard);
        scene.effects().emitParticles(
                zombiePosition.add(0, 0.8, 0),
                scene.effects().simpleParticleEmitter(ParticleTypes.POOF, util.vector().of(0, 0.08, 0)),
                2,
                8
        );
        scene.idle(20);

        scene.world().createItemEntity(
                zombiePosition.add(0, 0.3, 0),
                util.vector().of(0.08, 0.18, 0),
                new ItemStack(Items.ROTTEN_FLESH)
        );
        scene.overlay().showText(65)
                .attachKeyFrame()
                .colored(PonderPalette.GREEN)
                .placeNearTarget()
                .pointAt(zombiePosition.add(0, 0.4, 0))
                .text("Defeated mobs drop their normal loot");
        scene.idle(75);
        scene.markAsFinished();
    }
}
