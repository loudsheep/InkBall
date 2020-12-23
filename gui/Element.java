package gui;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Element {
    protected PApplet sketch;
    protected PVector pos;

    public abstract void show();

    public abstract void clicked(int mouseX, int mouseY);

    public abstract void released(int mouseX, int mouseY);
}
