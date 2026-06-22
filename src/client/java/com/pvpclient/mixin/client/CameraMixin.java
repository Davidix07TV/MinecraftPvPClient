package com.pvpclient.mixin.client;

import com.pvpclient.features.FreelookHandler;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow private float yaw;
    @Shadow private float pitch;

    @Inject(method = "update", at = @At("TAIL"))
    private void pvpclient$afterUpdate(CallbackInfo ci) {
        if (FreelookHandler.INSTANCE.isActive()) {
            this.yaw = FreelookHandler.INSTANCE.getCameraYaw();
            this.pitch = FreelookHandler.INSTANCE.getCameraPitch();
        }
    }
}
