package ru.vpb.jumpresetnotifier.draggables;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import ru.vpb.jumpresetnotifier.JumpResetNotifier;

public class JRNotiferDraggable extends AbstractDraggable{
    @Override
    protected void renderDraggable(DrawContext context, int mouseX, int mouseY, float delta) {
        if (mc.player == null || mc.world == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        String anus = "...";

        long sinceBestTiming = System.currentTimeMillis() - JumpResetNotifier.hurt9Time;
        long sinceJump = System.currentTimeMillis() - JumpResetNotifier.jumpTime;
        long dif = sinceBestTiming - sinceJump;

        if (Math.abs(dif) < 77) {
            anus = "Perfect: " + dif;
        } else if (Math.abs(dif) > 600) {
            anus = "...";
        } else if (dif < 500) {
            anus = "Too Late: " + dif;
        } else if (dif > 500) {
            anus = "Too Early: " + dif;
        }

        int padding = 5;
        int textWidth = textRenderer.getWidth(anus);
        int textHeight = textRenderer.fontHeight;

        this.width = textWidth + padding * 2;
        this.height = textHeight + padding * 2;

        context.fill(
                (int) x,
                (int) y,
                (int) (x + width),
                (int) (y + height),
                0xAA000000
        );

        int textX = (int) (x + (width - textWidth) / 2f);
        int textY = (int) (y + (height - textHeight) / 2f);

        context.drawTextWithShadow(textRenderer, anus, textX, textY, 0xFFFFFFFF);
    }


    @Override
    protected boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    protected boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return false;
    }

    @Override
    public void tick() {
    }
}
