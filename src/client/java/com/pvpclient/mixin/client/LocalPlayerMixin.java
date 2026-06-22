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
    private void pvpclient$beforeTurn(float yaw, float pitch, CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer)(Object)this;
        pvpclient$preTurnYaw = self.getYaw();
        pvpclient$preTurnPitch = self.getPitch();
    }

    @Inject(method = "turn", at = @At("RETURN"))
    private void pvpclient$afterTurn(float yaw, float pitch, CallbackInfo ci) {
        if (FreelookHandler.INSTANCE.isActive()) {
            LocalPlayer self = (LocalPlayer)(Object)this;
            // let camera use the new rotation, but restore player rotation to saved server-facing values
            FreelookHandler.INSTANCE.setCameraRotation(self.getYaw(), self.getPitch());
            self.setYaw(FreelookHandler.INSTANCE.getSavedYaw());
            self.setPitch(FreelookHandler.INSTANCE.getSavedPitch());
        }
    }
}
