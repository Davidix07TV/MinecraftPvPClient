package com.pvpclient.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.network.ClientPlayerEntity;

public final class FreelookHandler {
    public static final FreelookHandler INSTANCE = new FreelookHandler();

    private boolean active = false;
    private float savedYaw;
    private float savedPitch;
    private float cameraYaw;
    private float cameraPitch;

    private FreelookHandler() {}

    public void onTick(KeyBinding key) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        boolean pressed = key.isPressed();
        if (pressed && !active) {
            // start freelook
            active = true;
            savedYaw = mc.player.getYaw();
            savedPitch = mc.player.getPitch();
            cameraYaw = savedYaw;
            cameraPitch = savedPitch;
        } else if (!pressed && active) {
            // end freelook, smoothly snap back
            active = false;
            // simple snap: restore player rotation
            mc.player.setYaw(savedYaw);
            mc.player.setPitch(savedPitch);
        }
    }

    public boolean isActive() { return active; }

    public float getSavedYaw() { return savedYaw; }
    public float getSavedPitch() { return savedPitch; }

    public void setCameraRotation(float yaw, float pitch) { this.cameraYaw = yaw; this.cameraPitch = pitch; }
    public float getCameraYaw() { return cameraYaw; }
    public float getCameraPitch() { return cameraPitch; }
}
