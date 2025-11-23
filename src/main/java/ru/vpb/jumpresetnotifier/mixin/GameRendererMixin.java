package ru.vpb.jumpresetnotifier.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.vpb.jumpresetnotifier.JumpResetNotifier;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique
    MinecraftClient mc = MinecraftClient.getInstance();

    @Shadow
    @Final
    private BufferBuilderStorage buffers;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", shift = At.Shift.AFTER, ordinal = 0))
    private void hookDraw(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        DrawContext drawContext = new DrawContext(mc, buffers.getEntityVertexConsumers());
        float i = (float) (mc.mouse.getX() * (double) mc.getWindow().getScaledWidth() / (double) mc.getWindow().getWidth());
        float j = (float) (mc.mouse.getY() * (double) mc.getWindow().getScaledHeight() / (double) mc.getWindow().getHeight());

        JumpResetNotifier.jrnDraggable.render(drawContext, (int) i, (int) j, tickCounter.getLastFrameDuration());

        drawContext.draw();
    }
}
