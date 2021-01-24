package app.scene;

import app.Test;
import processing.core.PApplet;

public abstract class Scene {
    public interface Swap {
        void swap(Test.SceneType type);
    }

    public interface Title {
        void setTitle(String title);
    }

    PApplet sketch;
    Swap swap;
    Title title;
    protected SceneManager manager;
    protected Settings config;
    protected int width, height;
    protected int panelHeight;

    public Scene(PApplet sketch, int width, int height, int panelHeight) {
        this.sketch = sketch;
        this.width = width;
        this.height = height;
        this.panelHeight = panelHeight;
    }

    public abstract void init();

    public abstract void show();

    public abstract void mousePressed();

    public abstract void mouseReleased();

    public void applySettings(Settings settings) {
        this.config = settings;
    }

    public void clear() {
        System.gc();
    }

    public void swapFunction(Swap swap) {
        this.swap = swap;
    }

    public void titleFunction(Title title) {
        this.title = title;
    }

    protected void setTitle(String title) {
        if(manager != null) {
            manager.setTitle(title);
        }
    }

    public void setSceneManager(SceneManager manager) {
        this.manager = manager;
    }
}
