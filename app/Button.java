package app;

import processing.core.PApplet;
import processing.core.PVector;


public class Button {

    public interface VoidMethod {
        void actionPerformed();
    }

    private PApplet sketch;
    private PVector pos;
    private int width;
    private int height;

    private String text;
    private int textSize;

    private VoidMethod action = null;

    private boolean clicked = false;
    private boolean active = true;

    private Color strokeColor = new Color(0, 0, 0);
    private Color fillColor = new Color(255, 255, 255);
    private Color textColor = new Color(0, 0, 0);
    private Color clickedColor = new Color(230, 230, 230);

    public Button(PApplet sketch, String text, int textSize, int x, int y, int w, int h) {
        this.sketch = sketch;
        pos = new PVector(x, y);

        width = w;
        height = h;

        this.text = text;
        this.textSize = textSize;

    }

    public void show() {
        if (!active) return;

        sketch.stroke(strokeColor.r, strokeColor.g, strokeColor.b);
        sketch.strokeWeight(1);


        if (clicked)
            sketch.fill(clickedColor.r, clickedColor.g, clickedColor.b);
        else
            sketch.fill(fillColor.r, fillColor.g, fillColor.b);

        sketch.rect(pos.x, pos.y, width, height);
        sketch.textSize(textSize);

        float w = sketch.textWidth(text);

        float x = pos.x + width / 2f - w / 2f;
        float y = pos.y + height / 2f + textSize / 2f;

        sketch.fill(textColor.r, textColor.g, textColor.b);

        sketch.text(text, x, y);
    }


    private void action() {
        if (action != null) {
            action.actionPerformed();
        }
    }

    public void clicked(int mouseX, int mouseY) {
        if (!active) return;
        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + height) clicked = true;
        }
    }

    public void released(int mouseX, int mouseY) {
        if (!active) return;
        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + height) {
                if (clicked) {
                    action();

                }
            }
        }

        clicked = false;
    }

    public void setAction(VoidMethod action) {
        this.action = action;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setClickedColor(Color clickedColor) {
        this.clickedColor = clickedColor;
    }
}
