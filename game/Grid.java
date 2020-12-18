package game;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;

public class Grid {

    private int squaresX;
    private int squaresY;
    private int w;
    private int h;
    private int gameFrame = 0;

    private PApplet sketch;
    private ArrayList<Square> grid;
    public ArrayList<Ball> balls;
    public TreeMap<Integer, Ball> waiting;

    private LineHandler lh;

    public Grid(int squaresX, int squaresY, int w, int h) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.w = w;
        this.h = h;

        grid = new ArrayList<>();
        balls = new ArrayList<>();
        waiting = new TreeMap<>();

        this.lh = null;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;

        for (Square s : grid) {
            s.setSketch(sketch);
        }

        for (Ball b : balls) {
            b.setSketch(sketch);
        }

        lh.setSketch(sketch);
    }

    public void setAll(PApplet sketch) {
        this.sketch = sketch;

        for (Square s : grid) {
            s.setSketch(sketch);
            s.setGrid(this);
        }

        for (Ball b : balls) {
            b.setSketch(sketch);
        }
        this.lh = new LineHandler(sketch);
    }

    public void setGrid(ArrayList<Square> grid) {
        this.grid = grid;
    }

    public ArrayList<Square> getGrid() {
        return grid;
    }

    public void update(int mouseX, int mouseY, int pmouseX, int pmouseY) {

        lh.draw(mouseX, mouseY, pmouseX, pmouseY);
        gameFrame++;

        try {
            for (Ball b : balls) {
                Ball bb = b;

                try {
                    bb.move();
                    bb.collide(lh);
                    bb.collide(grid);
                } catch (Exception ignored) {
                    break;
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }

        if (waiting.size() != 0) {
            for (int i : waiting.keySet()) {
                if (i < 0) continue;
                if (i < gameFrame) {
                    waiting.get(i).setSketch(sketch);
                    balls.add(waiting.get(i));
                    waiting.remove(i);
                    break;
                }
            }
        }

    }

    public void startDraw(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        lh.startDraw(mouseX, mouseY, pmouseX, pmouseY);
    }

    public void stopDraw() {
        lh.stopDraw();
    }

    public void show() {
        for (Square square : grid) {
            square.show();
        }
        for (Ball b : balls) {
            b.show();
        }
        lh.show();

//        sketch.textSize(w / (float) squaresX / 3f);
//        sketch.text(gameFrame, 20, 20);
    }

    public void addBall(float speed, float radius, Ball.COLOR c) {
        addBall(speed, radius, c, 10);
    }

    public void addBall(float speed, float radius, Ball.COLOR c, int frame) {

        ArrayList<Square> spawners = new ArrayList<>();

        for (Square s : grid) {
            if (s.getType() == Square.TYPE.SPAWN) spawners.add(s);
        }

        if (spawners.size() == 0) {
            waiting.put(-(waiting.size() + 1), new Ball(0, 0, speed, radius, c));
            return;
        }

        Square rand = spawners.get((int) (Math.random() * spawners.size()));

        waiting.put(frame, new Ball(rand.getPosX() + rand.getW() / 2f, rand.getPosY() + rand.getH() / 2f, speed, radius, c));
    }

    public void addBall(Ball ball, int frame) {
        addBall(ball.getSpeed(), ball.getRadius(), ball.color, frame);
    }

    public void addBall(float posX, float posY, PVector initVel, float speed, float radius, Ball.COLOR c) {
        int index = (int) (posY * squaresX + posX);
        float px, py;

        Square s = grid.get(index);
        //System.out.println(s + " square");

        px = s.getPosX() + s.getW() / 2;
        py = s.getPosY() + s.getH() / 2;

        balls.add(new Ball(px, py, initVel, speed, radius, c));
    }

    public int getSquaresX() {
        return squaresX;
    }

    public int getSquaresY() {
        return squaresY;
    }

    public int getGameFrame() {
        return gameFrame;
    }
}
