package kr.moonscenty.createmobmasher.mixin;

import mob_grinding_utils.tile.TileEntitySaw;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntitySaw.class)
public abstract class TileEntitySawMixin {

    @Inject(
            method = "activateBlock",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void createMobMasher$beforeActivate(CallbackInfo ci) {
        System.out.println("[CreateMobMasher] activateBlock() Hooked!");
    }
}