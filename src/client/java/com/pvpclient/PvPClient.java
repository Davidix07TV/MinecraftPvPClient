package com.pvpclient;

import com.pvpclient.gui.HudEditorScreen;
import com.pvpclient.hud.HudManager;
import com.pvpclient.config.ConfigManager;
import com.pvpclient.config.ClientConfig;
import com.pvpclient.features.FreelookHandler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point client-side del PvP Client.
 * Registra il sistema HUD, i keybind e inizializza i moduli visuali.
 */
public class PvPClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(PvPClientMod.MOD_ID + "-client");

	private final HudManager hudManager = new HudManager();

	// keybinds
	public static KeyMapping HUD_EDITOR_KEY;
	public static KeyMapping FREELOOK_KEY;

	@Override
	public void onInitializeClient() {
		// load config
		ConfigManager.INSTANCE.load();
		ClientConfig.INSTANCE.syncFrom(ConfigManager.INSTANCE.get());

		// register HUD + modules
		hudManager.register();

		// register keybinds
		HUD_EDITOR_KEY = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.pvpclient.hud_editor", InputConstants.Type.KEYSYM, org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT, KeyMapping.Category.MISC));
		FREELOOK_KEY = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.pvpclient.freelook", InputConstants.Type.KEYSYM, org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT, KeyMapping.Category.MISC));

		// client tick: listen for HUD editor toggle and freelook polling
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (HUD_EDITOR_KEY.consumeClick()) {
				Minecraft mc = Minecraft.getInstance();
				mc.setScreen(new HudEditorScreen());
			}

			FreelookHandler.INSTANCE.onTick(FREELOOK_KEY);
		});

		LOGGER.info("PvP Client HUD inizializzato (CPS, FPS, Keystrokes)");
	}
}
