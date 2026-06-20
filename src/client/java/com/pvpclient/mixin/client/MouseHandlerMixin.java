package com.pvpclient.mixin.client;

import com.pvpclient.cps.ClickTracker;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Traccia i click fisici del giocatore per il contatore CPS.
 * Non modifica né simula input — solo osservazione passiva.
 */
@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
	@Inject(method = "onButton", at = @At("HEAD"))
	private void pvpclient$trackCps(long handle, MouseButtonInfo rawButtonInfo, int action, CallbackInfo ci) {
		if (action != GLFW.GLFW_PRESS) {
			return;
		}

		int button = rawButtonInfo.button();
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			ClickTracker.INSTANCE.registerClick(button);
		}
	}
}
