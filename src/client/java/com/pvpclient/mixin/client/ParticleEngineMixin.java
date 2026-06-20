package com.pvpclient.mixin.client;

import com.pvpclient.config.ClientConfig;

import net.minecraft.client.particle.ParticleEngine;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Salta l'aggiornamento delle particelle quando l'FPS Boost è attivo.
 * Riduce il carico CPU/GPU senza alterare la gameplay logic lato server.
 */
@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void pvpclient$skipParticleTick(CallbackInfo ci) {
		if (ClientConfig.INSTANCE.disableParticles) {
			ci.cancel();
		}
	}
}
