package com.pvpclient.mixin.client;

import com.pvpclient.features.FreelookHandler;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    private float pvpclient$preTurnYaw;
    private float pvpclient$preTurnPitch;

    @Inject(method = "turn", at = @At("HEAD"))
    private void pvpclient$beforeTurn(double yaw, double pitch, CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer)(Object)this;
        pvpclient$preTurnYaw = self.getYRot();
        pvpclient$preTurnPitch = self.getXRot();
    }

    @Inject(method = "turn", at = @At("RETURN"))
    private void pvpclient$afterTurn(double yaw, double pitch, CallbackInfo ci) {
        if (FreelookHandler.INSTANCE.isActive()) {
            LocalPlayer self = (LocalPlayer)(Object)this;
            FreelookHandler.INSTANCE.setCameraRotation(self.getYRot(), self.getXRot());
            self.setYRot(FreelookHandler.INSTANCE.getSavedYaw());
            self.setXRot(FreelookHandler.INSTANCE.getSavedPitch());
        }
    }
}
