package com.pvpclient;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvPClientMod implements ModInitializer {
	public static final String MOD_ID = "pvpclient";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("PvP Client loaded (server-side stub)");
	}
}
