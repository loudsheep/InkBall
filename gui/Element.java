package gui;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Element {
    protected PApplet sketch;
    protected PVector pos;
    protected boolean active;
    protected boolean clicked;

    public abstract void show();

    public abstract void clicked(float mouseX, float mouseY);

    public abstract void released(float mouseX, float mouseY);
}
