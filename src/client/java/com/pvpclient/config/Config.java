package com.pvpclient.config;

public final class Config {
    public static final class ModuleConfig {
        public boolean enabled = true;
        public int x = 4;
        public int y = 4;
        public int textColor = 0xFFFFFFFF;
        public boolean chroma = false;
        public boolean backgroundEnabled = true;
    }

    public ModuleConfig fps = new ModuleConfig();
    public ModuleConfig cps = new ModuleConfig();
    public ModuleConfig keystrokes = new ModuleConfig();

    // misc performance / legacy fields preserved
    public boolean disableParticles = false;
    public boolean disableWeather = false;
    public boolean disableRainParticles = false;
}
