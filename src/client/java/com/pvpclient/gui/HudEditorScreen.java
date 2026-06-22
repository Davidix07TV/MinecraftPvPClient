package com.pvpclient.gui;

import com.pvpclient.config.ConfigManager;
import com.pvpclient.config.Config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class HudEditorScreen extends Screen {
    private enum Module { FPS, CPS, KEYS }

    private final MinecraftClient client = MinecraftClient.getInstance();
    private Module dragging = null;
    private Module selected = null;
    private int dragOffsetX, dragOffsetY;

    protected HudEditorScreen() {
        super(Text.literal("HUD Editor"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        Config cfg = ConfigManager.INSTANCE.get();

        // Draw semi-transparent overlay for each module
        drawModuleBox(matrices, cfg.fps.x, cfg.fps.y, "FPS", cfg.fps.enabled);
        drawModuleBox(matrices, cfg.cps.x, cfg.cps.y, "CPS", cfg.cps.enabled);
        drawModuleBox(matrices, cfg.keystrokes.x, cfg.keystrokes.y, "KEYS", cfg.keystrokes.enabled);

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawModuleBox(MatrixStack ms, int x, int y, String label, boolean enabled) {
        int w = 80;
        int h = 20;
        int bg = 0x66000000;
        int border = enabled ? 0xFF00FF00 : 0xAA888888;
        DrawableHelper.fill(ms, x, y, x + w, y + h, bg);
        DrawableHelper.fill(ms, x, y, x + 1, y + h, border);
        DrawableHelper.fill(ms, x, y, x + w, y + 1, border);
        client.textRenderer.draw(ms, label, x + 6, y + 5, 0xFFFFFFFF);
        if (selected != null && ((selected == Module.FPS && label.equals("FPS")) || (selected == Module.CPS && label.equals("CPS")) || (selected == Module.KEYS && label.equals("KEYS")))) {
            // draw small context hint
            client.textRenderer.draw(ms, "[C] colore, [B] bg", x + w + 6, y + 5, 0xFFFFFFAA);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Config cfg = ConfigManager.INSTANCE.get();
        if (clickedInside(mouseX, mouseY, cfg.fps.x, cfg.fps.y, 80, 20)) {
            if (button == 0) startDragging(Module.FPS, (int)mouseX - cfg.fps.x, (int)mouseY - cfg.fps.y);
            if (button == 1) { selected = Module.FPS; cyclePreset(Module.FPS); }
            return true;
        }
        if (clickedInside(mouseX, mouseY, cfg.cps.x, cfg.cps.y, 80, 20)) {
            if (button == 0) startDragging(Module.CPS, (int)mouseX - cfg.cps.x, (int)mouseY - cfg.cps.y);
            if (button == 1) { selected = Module.CPS; cyclePreset(Module.CPS); }
            return true;
        }
        if (clickedInside(mouseX, mouseY, cfg.keystrokes.x, cfg.keystrokes.y, 80, 20)) {
            if (button == 0) startDragging(Module.KEYS, (int)mouseX - cfg.keystrokes.x, (int)mouseY - cfg.keystrokes.y);
            if (button == 1) { selected = Module.KEYS; cyclePreset(Module.KEYS); }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // C cycles color preset, B toggles background
        if (selected != null) {
            if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_C) {
                cyclePreset(selected);
                return true;
            }
            if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_B) {
                toggleBackground(selected);
                return true;
            }
        }
        if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE) {
            onClose();
            client.setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
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
        // presets: White, Red, Green, Blue, Chroma
        if (!mc.chroma && mc.textColor != 0xFFFFFFFF) { mc.textColor = 0xFFFFFFFF; mc.chroma = false; return; }
        if (mc.textColor == 0xFFFFFFFF && !mc.chroma) { mc.textColor = 0xFFFF5555; mc.chroma = false; return; }
        if (mc.textColor == 0xFFFF5555) { mc.textColor = 0xFF55FF55; mc.chroma = false; return; }
        if (mc.textColor == 0xFF55FF55) { mc.textColor = 0xFF5555FF; mc.chroma = false; return; }
        // else set chroma
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

    private boolean clickedInside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && my >= y && mx <= x + w && my <= y + h;
    }

    private void startDragging(Module m, int offsetX, int offsetY) {
        this.dragging = m;
        this.dragOffsetX = offsetX;
        this.dragOffsetY = offsetY;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging != null) {
            Config cfg = ConfigManager.INSTANCE.get();
            int nx = (int)mouseX - dragOffsetX;
            int ny = (int)mouseY - dragOffsetY;
            switch (dragging) {
                case FPS -> { cfg.fps.x = nx; cfg.fps.y = ny; }
                case CPS -> { cfg.cps.x = nx; cfg.cps.y = ny; }
                case KEYS -> { cfg.keystrokes.x = nx; cfg.keystrokes.y = ny; }
            }
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging != null) {
            dragging = null;
            ConfigManager.INSTANCE.save();
            // sync legacy ClientConfig
            com.pvpclient.config.ClientConfig.INSTANCE.syncFrom(ConfigManager.INSTANCE.get());
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        // ensure saved
        ConfigManager.INSTANCE.save();
        com.pvpclient.config.ClientConfig.INSTANCE.syncFrom(ConfigManager.INSTANCE.get());
    }
}
