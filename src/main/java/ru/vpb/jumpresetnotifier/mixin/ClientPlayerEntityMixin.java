package ru.vpb.jumpresetnotifier.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.vpb.jumpresetnotifier.JumpResetNotifier;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void hookLocalPosTickEvent(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.hurtTime == 9 && player.isSprinting()) {
            JumpResetNotifier.hurt9Time = System.currentTimeMillis();
        }
    }
}
