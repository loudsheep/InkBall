package game;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Ink {
    static class Line {
        PVector start, end;
        float angle;
        float len;

        public Line(PVector start, PVector end) {
            this.start = start;
            this.end = end;
            angle = PApplet.atan2(start.y - end.y, start.x - end.x);
            len = start.dist(end);
        }
    }

    public ArrayList<Line> line;
    private PApplet sketch;
    public float r = 5;

    public Ink(PApplet sketch) {
        this.sketch = sketch;
        line = new ArrayList<>();
    }

    public void append(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        line.add(new Line(new PVector(mouseX, mouseY), new PVector(pmouseX, pmouseY)));
    }

    public void show() {
        sketch.stroke(0);
        sketch.strokeWeight(r);
        sketch.fill(0);
        for (Line p : line) {
            sketch.line(p.start.x, p.start.y, p.end.x, p.end.y);
        }
    }
}
