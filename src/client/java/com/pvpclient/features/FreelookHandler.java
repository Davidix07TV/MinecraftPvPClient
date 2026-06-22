package com.pvpclient.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;

public final class FreelookHandler {
    public static final FreelookHandler INSTANCE = new FreelookHandler();

    private boolean active = false;
    private float savedYaw;
    private float savedPitch;
    private float cameraYaw;
    private float cameraPitch;

    private FreelookHandler() {}

    public void onTick(KeyMapping key) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        boolean pressed = key.isDown();
        if (pressed && !active) {
            active = true;
            savedYaw = player.getYRot();
            savedPitch = player.getXRot();
            cameraYaw = savedYaw;
            cameraPitch = savedPitch;
        } else if (!pressed && active) {
            active = false;
            player.setYRot(savedYaw);
            player.setXRot(savedPitch);
        }
    }

    public boolean isActive() { return active; }

    public float getSavedYaw() { return savedYaw; }
    public float getSavedPitch() { return savedPitch; }

    public void setCameraRotation(float yaw, float pitch) { this.cameraYaw = yaw; this.cameraPitch = pitch; }
    public float getCameraYaw() { return cameraYaw; }
    public float getCameraPitch() { return cameraPitch; }
}
