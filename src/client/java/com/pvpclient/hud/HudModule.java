package com.pvpclient.hud;

import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.minecraft.client.DeltaTracker;

public interface HudModule {
	void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

	default boolean isEnabled() {
		return true;
	}
}
