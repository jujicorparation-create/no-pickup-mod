package com.nopickup.mod;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoPickupCommand {

    public static final Set<UUID> disabledPlayers = new HashSet<>();

    public static void register() {
        PayloadTypeRegistry.playC2S().register(
                NoPickupNetwork.ID, NoPickupNetwork.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                NoPickupNetwork.ID,
                (payload, context) -> {
                    context.server().execute(() -> {
                        if (payload.disable()) {
                            disabledPlayers.add(context.player().getUuid());
                        } else {
                            disabledPlayers.remove(context.player().getUuid());
                        }
                    });
                }
        );
    }

    public static boolean isPickupDisabled(ServerPlayerEntity player) {
        return disabledPlayers.contains(player.getUuid());
    }
            }
