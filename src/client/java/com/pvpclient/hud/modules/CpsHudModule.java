package com.pvpclient.hud.modules;

import com.pvpclient.config.ClientConfig;
import com.pvpclient.config.ConfigManager;
import com.pvpclient.cps.ClickTracker;
import com.pvpclient.hud.HudModule;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class CpsHudModule implements HudModule {
	@Override
	public boolean isEnabled() {
		return ClientConfig.INSTANCE.cpsEnabled;
	}

	@Override
	public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		com.pvpclient.config.Config cfg = ConfigManager.INSTANCE.get();
		Minecraft minecraft = Minecraft.getInstance();

		int left = ClickTracker.INSTANCE.getLeftCps();
		int right = ClickTracker.INSTANCE.getRightCps();

		String text = "CPS: " + left + " | " + right;

		int x = cfg.cps.x;
		int y = cfg.cps.y;
		int textColor = cfg.cps.textColor;
		if (cfg.cps.chroma) {
			textColor = getChromaColor(minecraft, x, y);
		}

		int padding = 6;
		int textWidth = minecraft.font.width(text);
		int boxW = textWidth + padding * 2;
		int boxH = minecraft.font.lineHeight + padding * 2;

		if (cfg.cps.backgroundEnabled) {
			int bg = 0xAA000000;
			graphics.fill(x - padding, y - padding, x - padding + boxW, y - padding + boxH, bg);
		}

		// Draw drop-shadowed text
		graphics.text(minecraft.font, text, x, y, textColor, true);
	}

	private int getChromaColor(Minecraft mc, int x, int y) {
		float time = (System.currentTimeMillis() % 2000L) / 2000f;
		float hue = (time + (x + y) * 0.002f) % 1.0f;
		return hsvToRgb(hue, 0.9f, 1.0f);
	}

	private static int hsvToRgb(float h, float s, float v) {
		int i = (int)(h * 6);
		float f = h * 6 - i;
		float p = v * (1 - s);
		float q = v * (1 - f * s);
		float t = v * (1 - (1 - f) * s);
		float r=0,g=0,b=0;
		switch(i % 6){
			case 0: r=v; g=t; b=p; break;
			case 1: r=q; g=v; b=p; break;
			case 2: r=p; g=v; b=t; break;
			case 3: r=p; g=q; b=v; break;
			case 4: r=t; g=p; b=v; break;
			case 5: r=v; g=p; b=q; break;
		}
		int ri = (int)(r * 255) & 0xFF;
		int gi = (int)(g * 255) & 0xFF;
		int bi = (int)(b * 255) & 0xFF;
		return 0xFF << 24 | (ri << 16) | (gi << 8) | bi;
	}
}
