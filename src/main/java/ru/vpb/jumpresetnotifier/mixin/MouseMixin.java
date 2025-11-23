package ru.vpb.jumpresetnotifier.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.vpb.jumpresetnotifier.JumpResetNotifier;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow private boolean cursorLocked;

    @Shadow private double x;

    @Shadow private double y;

    @Inject(method = "onMouseButton", at = @At(value = "HEAD"))
    private void hookDraggableManager(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
        Mouse self = (Mouse) (Object) this;

        boolean bl = action == 1;

        if (client.getOverlay() == null) {
            if (client.currentScreen == null) {
            } else {
                double d = x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                double e = y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                Screen screen = this.client.currentScreen;
                if (bl) {
                } else {
                    JumpResetNotifier.jrnDraggable.mouseReleased(d, e, button);
                }
            }
        }
    }
}
