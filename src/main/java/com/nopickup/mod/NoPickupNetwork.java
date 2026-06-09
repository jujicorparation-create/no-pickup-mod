package com.nopickup.mod;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record NoPickupNetwork(boolean disable) implements CustomPayload {

    public static final Id<NoPickupNetwork> ID =
            new Id<>(Identifier.of("nopickup", "toggle_pickup"));

    public static final PacketCodec<RegistryByteBuf, NoPickupNetwork> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.BOOL, NoPickupNetwork::disable,
                    NoPickupNetwork::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
        }
