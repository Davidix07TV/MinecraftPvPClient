package com.pvpclient.hud.modules;

import com.pvpclient.config.ClientConfig;
import com.pvpclient.cps.ClickTracker;
import com.pvpclient.hud.HudModule;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class CpsHudModule implements HudModule {
	private static final int TEXT_COLOR = 0xFFFFFFFF;

	@Override
	public boolean isEnabled() {
		return ClientConfig.INSTANCE.cpsEnabled;
	}

	@Override
	public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		ClientConfig config = ClientConfig.INSTANCE;
		Minecraft minecraft = Minecraft.getInstance();

		int left = ClickTracker.INSTANCE.getLeftCps();
		int right = ClickTracker.INSTANCE.getRightCps();

		String text = "CPS: " + left + " | " + right;
		graphics.text(minecraft.font, text, config.cpsX, config.cpsY, TEXT_COLOR, true);
	}
}
