package app;

import app.scene.LevelScene;
import processing.core.PApplet;


public class Test extends PApplet {

    int w = 600;
    int panelHeight;

    LevelScene scene;

    public void settings() {
        panelHeight = w / 20;
        size(w, w + 30);
    }

    public void setup() {
        scene = new LevelScene(this, w, w, panelHeight);
        scene.setGameStatus(true);
    }

    public void draw() {
        scene.show();
    }

    public void mousePressed() {
        scene.mousePressed();
    }

    public void mouseReleased() {
        scene.mouseReleased();
    }


    public static void main(String[] args) {
        PApplet.main("app.Test", args);
    }

}
