package com.pvpclient.config;

/**
 * Configurazione client-side per moduli HUD e ottimizzazioni FPS.
 * Valori modificabili a runtime (es. da schermata impostazioni futura).
 */
public final class ClientConfig {
	public static final ClientConfig INSTANCE = new ClientConfig();

	// --- Moduli HUD ---
	public boolean cpsEnabled = true;
	public boolean fpsEnabled = true;
	public boolean keystrokesEnabled = true;

	public int cpsX = 4;
	public int cpsY = 4;
	public int fpsX = 4;
	public int fpsY = 18;
	public int keystrokesX = 4;
	public int keystrokesY = 34;

	// --- FPS Boost (solo rendering client-side, nessun vantaggio competitivo) ---
	public boolean disableParticles = false;
	public boolean disableWeather = false;
	public boolean disableRainParticles = false;

	private ClientConfig() {
	}

	public void syncFrom(Config cfg) {
		this.cpsEnabled = cfg.cps.enabled;
		this.fpsEnabled = cfg.fps.enabled;
		this.keystrokesEnabled = cfg.keystrokes.enabled;

		this.cpsX = cfg.cps.x;
		this.cpsY = cfg.cps.y;
		this.fpsX = cfg.fps.x;
		this.fpsY = cfg.fps.y;
		this.keystrokesX = cfg.keystrokes.x;
		this.keystrokesY = cfg.keystrokes.y;

		this.disableParticles = cfg.disableParticles;
		this.disableWeather = cfg.disableWeather;
		this.disableRainParticles = cfg.disableRainParticles;
	}
}
