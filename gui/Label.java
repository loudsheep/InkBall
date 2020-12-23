package gui;

import processing.core.PApplet;
import processing.core.PVector;

public class Label extends Element {

    int width;
    int height;
    String text;
    private Element element;

    public Label(PApplet sketch, int x, int y, int width, int height, String text) {
        this.sketch = sketch;
        this.pos = new PVector(x, y);
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public void setParentElement(Element e) {
        this.element = e;
    }

    @Override
    public void show() {

    }

    @Override
    public void clicked(float mouseX, float mouseY) {
        if (element != null) {
            if (!active) return;
            if (mouseX >= pos.x && mouseX <= pos.x + width) {
                if (mouseY >= pos.y && mouseY <= pos.y + width) {
                    clicked = true;
                    element.clicked(element.pos.x, element.pos.y);
                }
            }
        }
    }

    @Override
    public void released(float mouseX, float mouseY) {
        if (!active) return;
        if (mouseX >= pos.x && mouseX <= pos.x + width) {
            if (mouseY >= pos.y && mouseY <= pos.y + width) {
                if (clicked) {
                    element.released(element.pos.x, element.pos.y);
                }
            }
        }

        clicked = false;
    }
}
