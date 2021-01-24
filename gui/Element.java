package gui;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Element {
    protected PApplet sketch;
    protected PVector pos;
    protected boolean active;
    protected boolean clicked;

    Element(PApplet sketch, int x, int y) {
        this.sketch = sketch;
        this.pos = new PVector(x, y);
    }

    public abstract void show();

    public abstract void clicked(float mouseX, float mouseY);

    public abstract void released(float mouseX, float mouseY);


    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
