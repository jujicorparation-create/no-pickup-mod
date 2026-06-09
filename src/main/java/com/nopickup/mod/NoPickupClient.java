package com.nopickup.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class NoPickupClient implements ClientModInitializer {

    private static KeyBinding enableKey;
    private static KeyBinding disableKey;

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playC2S().register(
                NoPickupNetwork.ID, NoPickupNetwork.CODEC);

        enableKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nopickup.enable",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                "category.nopickup"
        ));

        disableKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nopickup.disable",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "category.nopickup"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (enableKey.wasPressed()) {
                ClientPlayNetworking.send(new NoPickupNetwork(true));
                client.player.sendMessage(
                        net.minecraft.text.Text.literal("§c[NoPickup] §fPickup DISABLED"),
                        true
                );
            }

            while (disableKey.wasPressed()) {
                ClientPlayNetworking.send(new NoPickupNetwork(false));
                client.player.sendMessage(
                        net.minecraft.text.Text.literal("§a[NoPickup] §fPickup ENABLED"),
                        true
                );
            }
        });
    }
    }
