package kr.moonscenty.createmobmasher.content;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import mob_grinding_utils.ModItems;
import mob_grinding_utils.components.MGUComponents;
import mob_grinding_utils.config.ServerConfig;
import mob_grinding_utils.items.ItemSawUpgrade;
import mob_grinding_utils.util.FakePlayerHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MechanicalMobMasherBlockEntity extends KineticBlockEntity  {

    private static final float MIN_SPEED = 128.0F;
    private static final int UPGRADE_SLOTS = ItemSawUpgrade.SawUpgradeType.values().length;

    private final ItemStackHandler upgrades = new ItemStackHandler(UPGRADE_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sendData();
        }

        @Override
        public int getSlotLimit(int slot) {
            return ServerConfig.MASHER_MAX_UPGRADES.get();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (!(stack.getItem() instanceof ItemSawUpgrade upgrade)) {
                return false;
            }
            return upgrade.upgradeType.ordinal() == slot;
        }
    };

    private UUID placer = null;
    private WeakReference<FakePlayer> fakePlayer = new WeakReference<>(null);

    public MechanicalMobMasherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

        float speed = Math.abs(getSpeed());

        if (speed < MIN_SPEED) {
            return;
        }

        int interval = getWorkInterval(speed);

        if (level.getGameTime() % interval != 0) {
            return;
        }

        activateMasher();
    }

    private void activateMasher() {
        if (!(level instanceof ServerLevel serverLevel)) return;

        AABB area = new AABB(
                worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(),
                worldPosition.getX() + 1.0D, worldPosition.getY() + 1.0D, worldPosition.getZ() + 1.0D
        ).inflate(0.0625D);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);
        if (entities.isEmpty()) return;

        fakePlayer = FakePlayerHandler.get(fakePlayer, serverLevel, placer, worldPosition.atY(-100));

        FakePlayer player = fakePlayer.get();
        if (player == null) return;

        ItemStack sword = createSword();

        player.setItemInHand(InteractionHand.MAIN_HAND, sword);
        player.detectEquipmentUpdates();

        for (LivingEntity entity : entities) {
            player.attackStrengthTicker = 100;
            player.attack(entity);
        }

        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    }

    private ItemStack createSword() {
        ItemStack sword = new ItemStack(ModItems.NULL_SWORD.get(), 1);

        int sharpness = getUpgradeCount(ItemSawUpgrade.SawUpgradeType.SHARPNESS);
        if (sharpness > 0)
            sword.enchant(level.holderOrThrow(Enchantments.SHARPNESS), sharpness * 10);

        int looting = getUpgradeCount(ItemSawUpgrade.SawUpgradeType.LOOTING);
        if (looting > 0)
            sword.enchant(level.holderOrThrow(Enchantments.LOOTING), looting);

        int flame = getUpgradeCount(ItemSawUpgrade.SawUpgradeType.FIRE);
        if (flame > 0)
            sword.enchant(level.holderOrThrow(Enchantments.FIRE_ASPECT), flame);

        int smite = getUpgradeCount(ItemSawUpgrade.SawUpgradeType.SMITE);
        if (smite > 0)
            sword.enchant(level.holderOrThrow(Enchantments.SMITE), smite * 10);

        int arthropod = getUpgradeCount(ItemSawUpgrade.SawUpgradeType.ARTHROPOD);
        if (arthropod > 0)
            sword.enchant(level.holderOrThrow(Enchantments.BANE_OF_ARTHROPODS), arthropod * 10);

        int beheading = getUpgradeCount(ItemSawUpgrade.SawUpgradeType.BEHEADING);
        if (beheading > 0)
            sword.set(MGUComponents.BEHEADING, beheading);

        return sword;
    }

    public boolean addUpgrade(ItemStack heldStack) {
        if (!(heldStack.getItem() instanceof ItemSawUpgrade upgrade)) return false;

        int slot = upgrade.upgradeType.ordinal();
        ItemStack remainder = upgrades.insertItem(slot, heldStack.copyWithCount(1), false);

        if (!remainder.isEmpty()) return false;

        heldStack.shrink(1);
        return true;
    }

    public ItemStack removeLastUpgrade() {
        for (int slot = upgrades.getSlots() - 1; slot >= 0; slot--) {
            if (!upgrades.getStackInSlot(slot).isEmpty()) {
                return upgrades.extractItem(slot, 1, false);
            }
        }

        return ItemStack.EMPTY;
    }

    public int getUpgradeCount(ItemSawUpgrade.SawUpgradeType type) {
        return upgrades.getStackInSlot(type.ordinal()).getCount();
    }

    public List<ItemStack> getUpgradeDrops() {
        List<ItemStack> drops = new ArrayList<>();

        for (int slot = 0; slot < upgrades.getSlots(); slot++) {
            ItemStack stack = upgrades.getStackInSlot(slot);
            if (!stack.isEmpty()) drops.add(stack.copy());
        }

        return drops;
    }

    public void setPlacer(UUID placer) {
        this.placer = placer;
        setChanged();
    }
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.createmobmasher.mechanical_mob_masher.title")
                .withStyle(ChatFormatting.GOLD));

        boolean hasEnoughSpeed = Math.abs(getSpeed()) >= MIN_SPEED;

        tooltip.add(Component.translatable(
                "tooltip.createmobmasher.mechanical_mob_masher.status",
                Component.translatable(hasEnoughSpeed
                                ? "tooltip.createmobmasher.mechanical_mob_masher.status.active"
                                : "tooltip.createmobmasher.mechanical_mob_masher.status.inactive")
                        .withStyle(hasEnoughSpeed ? ChatFormatting.GREEN : ChatFormatting.RED)
        ).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable(
                "tooltip.createmobmasher.mechanical_mob_masher.required_speed",
                Component.literal("128 RPM")
                        .withStyle(hasEnoughSpeed ? ChatFormatting.GREEN : ChatFormatting.RED)
        ).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable(
                "tooltip.createmobmasher.mechanical_mob_masher.stress_impact",
                Component.literal("16 SU/RPM").withStyle(ChatFormatting.AQUA)
        ).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.createmobmasher.mechanical_mob_masher.upgrades")
                .withStyle(ChatFormatting.YELLOW));

        boolean hasAny = false;

        for (ItemSawUpgrade.SawUpgradeType type : ItemSawUpgrade.SawUpgradeType.values()) {
            int count = getUpgradeCount(type);

            if (count <= 0) {
                continue;
            }

            hasAny = true;

            tooltip.add(Component.translatable(
                            "tooltip.createmobmasher.mechanical_mob_masher.upgrade_entry",
                            Component.translatable("tooltip.createmobmasher.mechanical_mob_masher.upgrade."
                                    + type.name().toLowerCase(Locale.ROOT)),
                            count)
                    .withStyle(ChatFormatting.WHITE));
        }

        if (!hasAny) {
            tooltip.add(Component.translatable("tooltip.createmobmasher.mechanical_mob_masher.upgrade.none")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }

        return true;
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);

        placer = tag.hasUUID("placer") ? tag.getUUID("placer") : null;

        if (tag.contains("upgrades")) {
            upgrades.deserializeNBT(registries, tag.getCompound("upgrades"));
        }
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);

        if (placer != null) {
            tag.putUUID("placer", placer);
        }

        tag.put("upgrades", upgrades.serializeNBT(registries));
    }

    private int getWorkInterval(float speed) {
        float clamped = Math.max(128.0F, Math.min(speed, 256.0F));
        return Math.round(50.0F - clamped / 6.4F);
    }
}
