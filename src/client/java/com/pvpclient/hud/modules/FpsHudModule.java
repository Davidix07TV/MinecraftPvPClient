package com.pvpclient.hud.modules;

import com.pvpclient.config.ClientConfig;
import com.pvpclient.hud.HudModule;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class FpsHudModule implements HudModule {
	private static final int TEXT_COLOR = 0xFFFFFFFF;

	@Override
	public boolean isEnabled() {
		return ClientConfig.INSTANCE.fpsEnabled;
	}

	@Override
	public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		ClientConfig config = ClientConfig.INSTANCE;
		Minecraft minecraft = Minecraft.getInstance();

		int fps = minecraft.getFps();
		String text = "FPS: " + fps;
		graphics.text(minecraft.font, text, config.fpsX, config.fpsY, TEXT_COLOR, true);
	}
}
