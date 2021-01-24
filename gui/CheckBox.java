package gui;

import processing.core.PApplet;
import processing.core.PVector;

public class CheckBox extends Element {

    public interface Action {
        void actionPerformed(boolean state);
    }

    private int width;
    private Action action;
    private boolean state;

    private Color strokeColor = new Color(0, 0, 0);
    private Color fillColor = new Color(255, 255, 255);
    private Color checkColor = new Color(0, 0, 0);

    public CheckBox(PApplet sketch, int x, int y, int w, boolean state) {
        super(sketch, x, y);
        this.width = w;
        this.state = state;
    }

    public void show() {
        if (!active) return;

        sketch.stroke(strokeColor.r, strokeColor.g, strokeColor.b);
        sketch.strokeWeight(2);

        sketch.fill(fillColor.r, fillColor.g, fillColor.b);

        sketch.rect(pos.x, pos.y, width, width);

        if (state) {
            sketch.stroke(checkColor.r, checkColor.g, checkColor.b);
            sketch.line(pos.x, pos.y, pos.x + width, pos.y + width);
            sketch.line(pos.x, pos.y + width, pos.x + width, pos.y);
        }
    }

    public void action() {
        if (action != null) {
            action.actionPerformed(state);
        }
        state = !state;
    }

    public void clicked(float mouseX, float mouseY) {
        if (!active) return;
        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + width) {
                clicked = true;
            }
        }
    }

    public void released(float mouseX, float mouseY) {
        if (!active) return;
        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + width) {
                if (clicked) {
                    action();

                }
            }
        }
        clicked = false;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setCheckColor(Color checkColor) {
        this.checkColor = checkColor;
    }
}
