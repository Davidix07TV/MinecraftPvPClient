package com.pvpclient.hud;

import com.pvpclient.PvPClientMod;
import com.pvpclient.hud.modules.CpsHudModule;
import com.pvpclient.hud.modules.FpsHudModule;
import com.pvpclient.hud.modules.KeystrokesHudModule;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

import java.util.List;

public final class HudManager {
	private static final Identifier HUD_LAYER = Identifier.fromNamespaceAndPath(PvPClientMod.MOD_ID, "hud_overlay");

	private final List<HudModule> modules = List.of(
			new CpsHudModule(),
			new FpsHudModule(),
			new KeystrokesHudModule()
	);

	public void register() {
		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				HUD_LAYER,
				this::renderAll
		);
	}

	private void renderAll(GuiGraphicsExtractor graphics, net.minecraft.client.DeltaTracker deltaTracker) {
		for (HudModule module : modules) {
			if (module.isEnabled()) {
				module.render(graphics, deltaTracker);
			}
		}
	}
}
