package com.nopickup.mod.mixin;

import com.nopickup.mod.NoPickupCommand;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemPickupMixin {

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void nopickup_cancelPickup(PlayerEntity player, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (NoPickupCommand.isPickupDisabled(serverPlayer)) {
                ci.cancel();
            }
        }
    }
}
