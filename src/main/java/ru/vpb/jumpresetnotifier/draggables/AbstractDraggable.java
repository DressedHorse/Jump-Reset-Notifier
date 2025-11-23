package ru.vpb.jumpresetnotifier.draggables;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractDraggable extends AbstractUIUXComponent {
    boolean dragging;
    double width, height;
    double startX, startY;
    double relX, relY;
    double targetX, targetY;

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float delta) {
        relX = MathHelper.clamp(relX, 0, 1);
        relY = MathHelper.clamp(relY, 0, 1);

        if (dragging) {
            double newTargetX = mouseX - startX;
            double newTargetY = mouseY - startY;

            targetX = newTargetX;
            targetY = newTargetY;
        } else {
            double deltaX = Math.abs(targetX - x);
            double deltaY = Math.abs(targetY - y);
            if (deltaX < 0.01 && deltaY < 0.01) {
                targetX = relX * mc.getWindow().getScaledWidth();
                targetY = relY * mc.getWindow().getScaledHeight();
            }
        }

        setRelXToTargetX();
        setRelYToTargetY();

        x += (targetX - x) * Math.min(1, delta * 1);
        y += (targetY - y) * Math.min(1, delta * 1);

        x = MathHelper.clamp(x, 0, mc.getWindow().getScaledWidth() - width);
        y = MathHelper.clamp(y, 0, mc.getWindow().getScaledHeight() - height);

        renderDraggable(context, mouseX, mouseY, delta);
    }

    protected abstract void renderDraggable(DrawContext context, int mouseX, int mouseY, float delta);

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isHovered(mouseX, mouseY)) {
            dragging = true;
            startX = mouseX - x;
            startY = mouseY - y;

        }

        return true;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging) {
            dragging = false;

            relX = x / mc.getWindow().getScaledWidth();
            relY = y / mc.getWindow().getScaledHeight();
        }

        return true;
    }

    protected boolean isHovered(double mx, double my) {
        return isHovered(x, y, width, height, mx, my);
    }

    public void setX(double x) {
        this.x = MathHelper.clamp(x, 0, mc.getWindow().getScaledWidth() - width);
        setRelXToTargetX();
    }

    public void setTargetX(double y) {
        targetX = MathHelper.clamp(y, 0, mc.getWindow().getScaledWidth() - width);
        setRelXToTargetX();
    }

    public void setY(double y) {
        this.y = MathHelper.clamp(y, 0, mc.getWindow().getScaledHeight() - height);

        if (mc.player != null) {
            this.relY = this.y / mc.getWindow().getScaledHeight();
        }
    }

    public void setRelYToTargetY() {
        relY = targetY / mc.getWindow().getScaledHeight();
    }

    public void setRelXToTargetX() {
        relX = targetX / mc.getWindow().getScaledWidth();
    }

    public void setTargetY(double y) {
        targetY = MathHelper.clamp(y, 0, mc.getWindow().getScaledHeight() - height);
        setRelYToTargetY();
    }

    public void setWidth(double width) {
        this.width = width;

        if (!dragging) {
            x = relX * mc.getWindow().getScaledWidth();
            y = relY * mc.getWindow().getScaledHeight();
        }

        x = MathHelper.clamp(x, 0, mc.getWindow().getScaledWidth() - width);
        y = MathHelper.clamp(y, 0, mc.getWindow().getScaledHeight() - height);
    }

    public void setHeight(double height) {
        this.height = height;

        if (!dragging) {
            x = relX * mc.getWindow().getScaledWidth();
            y = relY * mc.getWindow().getScaledHeight();
        }

        x = MathHelper.clamp(x, 0, mc.getWindow().getScaledWidth() - width);
        y = MathHelper.clamp(y, 0, mc.getWindow().getScaledHeight() - height);
    }
}
