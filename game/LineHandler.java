package game;

import processing.core.PApplet;

import java.util.ArrayList;

public class LineHandler {

    private PApplet sketch;
    public ArrayList<Ink> lines;

    private boolean drawing = false;
    private Ink newLine = null;

    public LineHandler(PApplet sketch) {
        lines = new ArrayList<>();
        this.sketch = sketch;
    }

    public void draw(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        if (!drawing) return;
        if (mouseX < 0 || mouseY < 0) return;
        if (!(mouseX == pmouseX && mouseY == pmouseY)) newLine.append(mouseX, mouseY, pmouseX, pmouseY);


        newLine.show();
    }

    public void startDraw(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        drawing = true;
        lines.add(new Ink(sketch));
        newLine = lines.get(lines.size() - 1);

        if (mouseX >= 0 && mouseY >= 0) {
            newLine.append(mouseX, mouseY, pmouseX, pmouseY);
        }
    }

    public void stopDraw() {
        drawing = false;
        if (newLine != null) {
            //lines.add(newLine);
            newLine = null;
        }
    }

    public void stopDraw(boolean hit) {
        drawing = false;
        newLine = null;
    }

    public void stopDraw(Ink hit) {
        if (newLine == hit) {
            for (Ink i : lines) {
                if (i == hit) {
                    lines.remove(hit);
                    break;
                }
            }
            stopDraw(true);
        } else {
            for (Ink i : lines) {
                if (i == hit) {
                    lines.remove(hit);
                    return;
                }
            }
        }
    }

    public void show() {
        for (Ink i : lines) {
            i.show();
        }
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
