package ru.vpb.jumpresetnotifier.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.vpb.jumpresetnotifier.JumpResetNotifier;

import java.util.Map;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Shadow @Final private static Map<InputUtil.Key, KeyBinding> KEY_TO_BINDINGS;

    @Inject(method = "onKeyPressed", at = @At("HEAD"))
    private static void hookKeyPress(InputUtil.Key key, CallbackInfo ci) {
        KeyBinding keyBinding = KEY_TO_BINDINGS.get(key);
        if (keyBinding != null && keyBinding.getTranslationKey().equals("key.jump")) {
            JumpResetNotifier.jumpTime = System.currentTimeMillis();
        }
    }
}
