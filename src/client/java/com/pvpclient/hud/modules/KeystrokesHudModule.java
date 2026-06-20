package com.pvpclient.hud.modules;

import com.pvpclient.config.ClientConfig;
import com.pvpclient.hud.HudModule;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class KeystrokesHudModule implements HudModule {
	private static final int KEY_SIZE = 22;
	private static final int KEY_GAP = 2;
	private static final int PADDING = 4;

	private static final int BG_COLOR = 0xAA000000;
	private static final int PRESSED_COLOR = 0xCCFFFFFF;
	private static final int RELEASED_COLOR = 0x55FFFFFF;
	private static final int LABEL_COLOR = 0xFF000000;

	@Override
	public boolean isEnabled() {
		return ClientConfig.INSTANCE.keystrokesEnabled;
	}

	@Override
	public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		ClientConfig config = ClientConfig.INSTANCE;
		Minecraft minecraft = Minecraft.getInstance();
		Options options = minecraft.options;

		int baseX = config.keystrokesX;
		int baseY = config.keystrokesY;

		boolean w = options.keyUp.isDown();
		boolean a = options.keyLeft.isDown();
		boolean s = options.keyDown.isDown();
		boolean d = options.keyRight.isDown();
		boolean lmb = minecraft.mouseHandler.isLeftPressed();
		boolean rmb = minecraft.mouseHandler.isRightPressed();

		int rowWidth = KEY_SIZE * 3 + KEY_GAP * 2;
		int panelWidth = rowWidth + PADDING * 2;
		int panelHeight = KEY_SIZE * 3 + KEY_GAP * 2 + PADDING * 2;

		graphics.fill(baseX, baseY, baseX + panelWidth, baseY + panelHeight, BG_COLOR);

		int keysX = baseX + PADDING;
		int keysY = baseY + PADDING;

		drawKey(graphics, minecraft, "W", keysX + KEY_SIZE + KEY_GAP, keysY, w);
		drawKey(graphics, minecraft, "A", keysX, keysY + KEY_SIZE + KEY_GAP, a);
		drawKey(graphics, minecraft, "S", keysX + KEY_SIZE + KEY_GAP, keysY + KEY_SIZE + KEY_GAP, s);
		drawKey(graphics, minecraft, "D", keysX + (KEY_SIZE + KEY_GAP) * 2, keysY + KEY_SIZE + KEY_GAP, d);

		int mouseY = keysY + (KEY_SIZE + KEY_GAP) * 2;
		drawKey(graphics, minecraft, "L", keysX, mouseY, lmb);
		drawKey(graphics, minecraft, "R", keysX + KEY_SIZE + KEY_GAP, mouseY, rmb);
	}

	private void drawKey(GuiGraphicsExtractor graphics, Minecraft minecraft, String label, int x, int y, boolean pressed) {
		int fillColor = pressed ? PRESSED_COLOR : RELEASED_COLOR;
		graphics.fill(x, y, x + KEY_SIZE, y + KEY_SIZE, fillColor);

		int textWidth = minecraft.font.width(label);
		int textX = x + (KEY_SIZE - textWidth) / 2;
		int textY = y + (KEY_SIZE - minecraft.font.lineHeight) / 2;
		graphics.text(minecraft.font, label, textX, textY, LABEL_COLOR, false);
	}
}
