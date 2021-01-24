package gui;

import processing.core.PApplet;
import processing.core.PVector;

public class Label extends Element {

    private int width;
    private int height;
    private String text;
    private Element element;
    private int textSize;

    private Color strokeColor = new Color(255, 255, 255);
    private Color fillColor = new Color(255, 255, 255);
    private Color textColor = new Color(0, 0, 0);

    public Label(PApplet sketch, int x, int y, int width, int height, String text, int textSize) {
        super(sketch, x, y);
        this.width = width;
        this.height = height;
        this.text = text;
        this.textSize = textSize;
    }

    public void setParentElement(Element e) {
        if (e != this && e != null) {
            this.element = e;
        }
    }

    @Override
    public void show() {
        if (!active) return;

        sketch.stroke(strokeColor.r, strokeColor.g, strokeColor.b);
        sketch.fill(fillColor.r, fillColor.g, fillColor.b);
        sketch.rect(pos.x, pos.y, width, height);

        sketch.fill(textColor.r, textColor.g, textColor.b);
        sketch.textSize(textSize);

        float w = sketch.textWidth(text);
        float x = pos.x + width / 2f - w / 2f;
        float y = pos.y + height / 2f + textSize / 2f;
        sketch.fill(textColor.r, textColor.g, textColor.b);
        sketch.text(text, x, y);
    }

    @Override
    public void clicked(float mouseX, float mouseY) {
        if (element == null) return;
        if (!active) return;

        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + height) {
                element.clicked(element.pos.x, element.pos.y);
                clicked = true;
            }
        }

    }

    @Override
    public void released(float mouseX, float mouseY) {
        if (!active) return;
        if (element == null) return;
        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + height) {
                if (clicked) {
                    element.clicked = true;
                }
                element.released(element.pos.x, element.pos.y);
            }
        }
        clicked = false;
    }

    public void setText(String text) {
        sketch.textSize(textSize);
        float w = sketch.textWidth(text);
        this.width = Math.max(width, (int) w);
        this.text = text;
    }
}
