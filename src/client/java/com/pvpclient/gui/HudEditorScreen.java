package com.pvpclient.gui;

import com.pvpclient.config.ClientConfig;
import com.pvpclient.config.Config;
import com.pvpclient.config.ConfigManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class HudEditorScreen extends Screen {
    private enum Module { FPS, CPS, KEYS }

    private Module dragging = null;
    private Module selected = null;
    private int dragOffsetX, dragOffsetY;

    public HudEditorScreen() {
        super(Component.literal("HUD Editor"));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, 0x88000000);

        Config cfg = ConfigManager.INSTANCE.get();
        drawModuleBox(graphics, cfg.fps.x, cfg.fps.y, Component.literal("FPS"), cfg.fps.enabled);
        drawModuleBox(graphics, cfg.cps.x, cfg.cps.y, Component.literal("CPS"), cfg.cps.enabled);
        drawModuleBox(graphics, cfg.keystrokes.x, cfg.keystrokes.y, Component.literal("KEYS"), cfg.keystrokes.enabled);

        if (selected != null) {
            graphics.text(this.font, Component.literal("[C] colore, [B] bg"), 10, this.height - 30, 0xFFFFFFAA, true);
        }
    }

    private void drawModuleBox(GuiGraphicsExtractor graphics, int x, int y, Component label, boolean enabled) {
        int w = 88;
        int h = 24;
        int bg = 0x66000000;
        int border = enabled ? 0xFF00FF00 : 0xAA888888;

        graphics.fill(x, y, x + w, y + h, bg);
        graphics.fill(x, y, x + 1, y + h, border);
        graphics.fill(x, y, x + w, y + 1, border);
        graphics.text(this.font, label, x + 6, y + 6, 0xFFFFFFFF, true);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        int button = event.button();
        int mouseX = (int) event.x();
        int mouseY = (int) event.y();
        Config cfg = ConfigManager.INSTANCE.get();

        if (clickedInside(mouseX, mouseY, cfg.fps.x, cfg.fps.y, 88, 24)) {
            if (button == 0) startDragging(Module.FPS, mouseX - cfg.fps.x, mouseY - cfg.fps.y);
            if (button == 1) {
                selected = Module.FPS;
                cyclePreset(Module.FPS);
            }
            return true;
        }
        if (clickedInside(mouseX, mouseY, cfg.cps.x, cfg.cps.y, 88, 24)) {
            if (button == 0) startDragging(Module.CPS, mouseX - cfg.cps.x, mouseY - cfg.cps.y);
            if (button == 1) {
                selected = Module.CPS;
                cyclePreset(Module.CPS);
            }
            return true;
        }
        if (clickedInside(mouseX, mouseY, cfg.keystrokes.x, cfg.keystrokes.y, 88, 24)) {
            if (button == 0) startDragging(Module.KEYS, mouseX - cfg.keystrokes.x, mouseY - cfg.keystrokes.y);
            if (button == 1) {
                selected = Module.KEYS;
                cyclePreset(Module.KEYS);
            }
            return true;
        }

        return super.mouseClicked(event, consumed);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        int keyCode = event.key();

        if (selected != null) {
            if (keyCode == GLFW.GLFW_KEY_C) {
                cyclePreset(selected);
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_B) {
                toggleBackground(selected);
                return true;
            }
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            onClose();
            Minecraft.getInstance().setScreen(null);
            return true;
        }

        return super.keyPressed(event);
    }

    private void cyclePreset(Module m) {
        Config cfg = ConfigManager.INSTANCE.get();
        switch (m) {
            case FPS -> cycleFor(cfg.fps);
            case CPS -> cycleFor(cfg.cps);
            case KEYS -> cycleFor(cfg.keystrokes);
        }
        ConfigManager.INSTANCE.save();
    }

    private void cycleFor(Config.ModuleConfig mc) {
        if (!mc.chroma && mc.textColor != 0xFFFFFFFF) {
            mc.textColor = 0xFFFFFFFF;
            mc.chroma = false;
            return;
        }
        if (mc.textColor == 0xFFFFFFFF && !mc.chroma) {
            mc.textColor = 0xFFFF5555;
            mc.chroma = false;
            return;
        }
        if (mc.textColor == 0xFFFF5555) {
            mc.textColor = 0xFF55FF55;
            mc.chroma = false;
            return;
        }
        if (mc.textColor == 0xFF55FF55) {
            mc.textColor = 0xFF5555FF;
            mc.chroma = false;
            return;
        }
        mc.chroma = !mc.chroma;
    }

    private void toggleBackground(Module m) {
        Config cfg = ConfigManager.INSTANCE.get();
        switch (m) {
            case FPS -> cfg.fps.backgroundEnabled = !cfg.fps.backgroundEnabled;
            case CPS -> cfg.cps.backgroundEnabled = !cfg.cps.backgroundEnabled;
            case KEYS -> cfg.keystrokes.backgroundEnabled = !cfg.keystrokes.backgroundEnabled;
        }
        ConfigManager.INSTANCE.save();
    }

    private boolean clickedInside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && my >= y && mx <= x + w && my <= y + h;
    }

    private void startDragging(Module m, int offsetX, int offsetY) {
        this.dragging = m;
        this.dragOffsetX = offsetX;
        this.dragOffsetY = offsetY;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        if (dragging != null) {
            Config cfg = ConfigManager.INSTANCE.get();
            int nx = (int) event.x() - dragOffsetX;
            int ny = (int) event.y() - dragOffsetY;

            switch (dragging) {
                case FPS -> {
                    cfg.fps.x = nx;
                    cfg.fps.y = ny;
                }
                case CPS -> {
                    cfg.cps.x = nx;
                    cfg.cps.y = ny;
                }
                case KEYS -> {
                    cfg.keystrokes.x = nx;
                    cfg.keystrokes.y = ny;
                }
            }

            return true;
        }
        return super.mouseDragged(event, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (dragging != null) {
            dragging = null;
            ConfigManager.INSTANCE.save();
            ClientConfig.INSTANCE.syncFrom(ConfigManager.INSTANCE.get());
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public void onClose() {
        super.onClose();
        ConfigManager.INSTANCE.save();
        ClientConfig.INSTANCE.syncFrom(ConfigManager.INSTANCE.get());
    }
}
