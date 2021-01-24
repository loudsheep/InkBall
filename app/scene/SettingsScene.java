package app.scene;

import app.Test;
import gui.Button;
import gui.CheckBox;
import gui.Label;
import gui.Slider;
import processing.core.PApplet;

public class SettingsScene extends Scene {


    Button cancel;
    Button save;

    Slider slider;
    CheckBox box;
    Label b;

    public SettingsScene(PApplet sketch, int width, int height, int panelHeight) {
        super(sketch, width, height, panelHeight);
        slider = new Slider(sketch, 100, 100, 100, -56, 100, 255);
        slider.setAction(this::smth);
        slider.setActive(true);

        box = new CheckBox(sketch, 150, 150, 10, true);
        box.setActive(true);

        b = new Label(sketch, 250, 250, 100, 10, "Label for box", 9);

    }

    private void cancel() {
        manager.swapScenes(SceneManager.SceneType.MENU);
    }

    private void save() {

    }

    public void smth(float v) {
        b.setText(String.valueOf(Math.round(v)));
    }

    @Override
    public void init() {
        sketch.textSize(height / 35f);
        manager.fps(config.menuFps);

        setTitle("InkBall");

        float w = sketch.textWidth("Cancel");
        cancel = new Button(sketch, "Cancel", height / 35, (int) (width - w * 1.5f), height - height / 20, (int) (w * 1.5f), height / 20);
        cancel.setAction(this::cancel);

        w = sketch.textWidth("Save");
        save = new Button(sketch, "Save", height / 35, 0, height - height / 20, (int) (w * 1.5f), height / 20);
        save.setAction(this::save);

        cancel.setActive(true);
        save.setActive(true);

        b.setActive(true);
        b.setParentElement(box);
    }

    @Override
    public void show() {
        cancel.show();
        save.show();

        slider.show();
        box.show();
        b.show();
    }

    @Override
    public void mousePressed() {
        cancel.clicked(sketch.mouseX, sketch.mouseY);
        save.clicked(sketch.mouseX, sketch.mouseY);
        slider.clicked(sketch.mouseX, sketch.mouseY);

        box.clicked(sketch.mouseX, sketch.mouseY);
        b.clicked(sketch.mouseX, sketch.mouseY);
    }

    @Override
    public void mouseReleased() {
        cancel.released(sketch.mouseX, sketch.mouseY);
        save.released(sketch.mouseX, sketch.mouseY);
        slider.released(sketch.mouseX, sketch.mouseY);

        box.released(sketch.mouseX, sketch.mouseY);
        b.released(sketch.mouseX, sketch.mouseY);
    }
}
