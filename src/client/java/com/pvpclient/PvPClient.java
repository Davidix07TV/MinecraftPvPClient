package com.pvpclient;

import com.pvpclient.hud.HudManager;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point client-side del PvP Client.
 * Registra il sistema HUD e inizializza i moduli visuali legittimi.
 */
public class PvPClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(PvPClientMod.MOD_ID + "-client");

	private final HudManager hudManager = new HudManager();

	@Override
	public void onInitializeClient() {
		hudManager.register();
		LOGGER.info("PvP Client HUD inizializzato (CPS, FPS, Keystrokes)");
	}
}
