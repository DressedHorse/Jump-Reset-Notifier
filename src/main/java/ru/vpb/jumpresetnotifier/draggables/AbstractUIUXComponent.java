package ru.vpb.jumpresetnotifier.draggables;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import org.joml.Matrix4f;

@Setter
@Getter
public abstract class AbstractUIUXComponent implements Drawable {
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    protected AbstractUIUXComponent parent;
    protected double x, y, z;

    protected abstract boolean keyPressed(int keyCode, int scanCode, int modifiers);

    protected abstract boolean mouseClicked(double mouseX, double mouseY, int button);

    protected abstract boolean mouseReleased(double mouseX, double mouseY, int button);

    protected abstract boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount);

    public abstract void tick();

    protected boolean charTyped(char chr, int modifiers) {
        return true;
    }

    protected Matrix4f getLastMatrix(DrawContext context) {
        return context.getMatrices().peek().getPositionMatrix();
    }

    public void onClose() {

    }

    public AbstractUIUXComponent setXYZ(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public static boolean isHovered(final double x, final double y, final double width, final double height, final double mouseX, final double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
