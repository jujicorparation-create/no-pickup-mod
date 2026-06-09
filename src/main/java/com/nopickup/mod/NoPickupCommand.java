package com.nopickup.mod;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoPickupCommand {

    public static final Set<UUID> disabledPlayers = new HashSet<>();

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                NoPickupNetwork.TOGGLE_PACKET_ID,
                (server, player, handler, buf, responseSender) -> {
                    boolean disable = buf.readBoolean();
                    server.execute(() -> {
                        if (disable) {
                            disabledPlayers.add(player.getUuid());
                        } else {
                            disabledPlayers.remove(player.getUuid());
                        }
                    });
                }
        );
    }

    public static boolean isPickupDisabled(ServerPlayerEntity player) {
        return disabledPlayers.contains(player.getUuid());
    }
}
