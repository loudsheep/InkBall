package app.scene;

import processing.core.PApplet;

public abstract class Scene {
    PApplet sketch;

    public Scene(PApplet sketch) {
        this.sketch = sketch;
    }

    public abstract void show();

    public abstract void mousePressed();

    public abstract void mouseReleased();
}
