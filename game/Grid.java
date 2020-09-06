package game;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class Grid {

    private int squaresX;
    private int squaresY;
    private int w;
    private int h;

    private PApplet sketch;

    private int gameFrame = 0;

    private ArrayList<Square> grid;

    public ArrayList<Ball> balls;
    public HashMap<Ball, Integer> waiting;

    private LineHandler lh;

    public Grid(PApplet sketch, int squaresX, int squaresY, int w, int h) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.w = w;
        this.h = h;

        this.lh = new LineHandler(sketch);

        this.sketch = sketch;

        grid = new ArrayList<>();
        balls = new ArrayList<>();

        ArrayList<PVector> used = new ArrayList<>();

        outer:
        for (int i = 0; i < squaresX; i++) {

            //outer:
//        for(int i=0; i<squaresX; i++) {
//            for(int j=0; j<)
//        }


            inner:
            for (int j = 0; j < squaresY; j++) {
                Square.TYPE type = null;

                for (PVector p : used) {
//                    if (i - p.x < 3 && i - p.x >= 0 && j-p.y <3&& j-p.y >=0) {
//                        type = Square.TYPE.HOLE;// outer;
//                    }
                    if (p.x == i && p.y == j) {
                        continue inner;
                        //type = Square.TYPE.HOLE;
                    }


//                    if (i - p.x < 3 && j - p.y < 3) {
//                        Square sq = new Square(sketch, w / (float) squaresX * i, h / (float) squaresY * j, w / (float) squaresX, h / (float) squaresY, Square.TYPE.HOLE, false, Square.HoleType.BLUE);
//                        grid.add(sq);
//                        continue con;
//                    }
                }
                if (type == Square.TYPE.HOLE) {
                    Square sq = new Square(sketch, w / (float) squaresX * i, h / (float) squaresY * j, w / (float) squaresX * 3, h / (float) squaresY * 3, type, false, Square.HoleType.BLUE);
                    grid.add(sq);
                    continue outer;
                }

                if (i == 0 || i == squaresX - 1 || j == 0 || j == squaresY - 1) type = Square.TYPE.WALL;
                else if (Math.random() < 0.01) type = Square.TYPE.SPAWN;
                else if (Math.random() < 0.01) type = Square.TYPE.WALL;
                    //else if(i==squaresX/2 && j != 0 && j != squaresY) type = Square.TYPE.ONE_WAY_LEFT;
                else type = Square.TYPE.FREE;

                if (Math.random() < 0.001) {
                    if (i > 0 && i < squaresX - 5 && j > 0 && j < squaresY - 5) {
                        type = Square.TYPE.HOLE;

                        used.add(new PVector(i, j));
                        used.add(new PVector(i + 1, j));
                        used.add(new PVector(i + 2, j));

                        used.add(new PVector(i, j + 1));
                        used.add(new PVector(i + 1, j + 1));
                        used.add(new PVector(i + 2, j + 1));

                        used.add(new PVector(i, j + 2));
                        used.add(new PVector(i + 1, j + 2));
                        used.add(new PVector(i + 2, j + 2));

                        Square sq = new Square(sketch, w / (float) squaresX * i, h / (float) squaresY * j, w / (float) squaresX * 3, h / (float) squaresY * 3, type, true, Square.HoleType.NEUTRAL
                        );
                        grid.add(sq);

                        System.out.println(i + "--" + j);
                    } else {

                        Square sq = new Square(sketch, w / (float) squaresX * i, h / (float) squaresY * j, w / (float) squaresX, h / (float) squaresY, type);
                        grid.add(sq);
                    }
                } else {


                    Square sq = new Square(sketch, w / (float) squaresX * i, h / (float) squaresY * j, w / (float) squaresX, h / (float) squaresY, type);
                    grid.add(sq);
                }
            }
        }
    }

    public Grid(int squaresX, int squaresY, int w, int h) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.w = w;
        this.h = h;

        grid = new ArrayList<>();
        balls = new ArrayList<>();
        waiting = new HashMap<>();

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

    public void update(int mouseX, int mouseY, int pmouseX, int pmouseY) {
//        PVector avg = new PVector();

        lh.draw(mouseX, mouseY, pmouseX, pmouseY);
        gameFrame++;

        try {
            for (Ball b : balls) {
//        for (int i = balls.size() - 1; i >= 0; i--) {

//            Ball bb = balls.get(i);
                Ball bb = b;


                try {
                    bb.move();
                    bb.collide(lh);
                    bb.collide(grid);
                } catch (Exception ignored) {
                    break;
                }


//            avg.x += b.pos.x;
//            avg.y += b.pos.y;
                //b.collide(l);
            }
        } catch (ConcurrentModificationException ignored) {
        }

        if (waiting.size() != 0) {
            for (Ball b : waiting.keySet()) {
                if (waiting.get(b) < gameFrame) {
                    b.setSketch(sketch);
                    balls.add(b);
                    waiting.remove(b);
                    break;
                }
            }
        }

//        avg.x /= balls.size();
//        avg.y /= balls.size();
//
//        sketch.strokeWeight(10);
//        sketch.stroke(255, 0, 0);
//        sketch.point(avg.x, avg.y);

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

        sketch.textSize(w/squaresX/3);
        sketch.text(gameFrame, 20,20);
    }

    public void addBall(PApplet sketch, float speed, float radius, float r, float g, float b) {

        ArrayList<Square> spawners = new ArrayList<>();

        for (Square s : grid) {
            if (s.getType() == Square.TYPE.SPAWN) spawners.add(s);
        }

        if (spawners.size() == 0) return;

        Square rand = spawners.get((int) sketch.random(spawners.size()));

        balls.add(new Ball(sketch, rand.getPosX() + rand.getW() / 2f, rand.getPosY() + rand.getH() / 2f, speed, radius, r, g, b));
    }

    public void addBall(float speed, float radius, float r, float g, float b) {
        addBall(speed, radius, r, g, b, 10);
    }

    public void addBall(float speed, float radius, float r, float g, float b, int frame) {

        ArrayList<Square> spawners = new ArrayList<>();

        for (Square s : grid) {
            if (s.getType() == Square.TYPE.SPAWN) spawners.add(s);
        }

        if (spawners.size() == 0) return;

        Square rand = spawners.get((int) (Math.random() * spawners.size()));

        waiting.put(new Ball(rand.getPosX() + rand.getW() / 2f, rand.getPosY() + rand.getH() / 2f, speed, radius, r, g, b), frame);
    }

    public void addBall(PApplet sketch, float posX, float posY, PVector initVel, float speed, float radius, float r, float g, float b) {
        int index = (int) (posY * squaresX + posX);
        float px, py;

        Square s = grid.get(index);

        px = s.getPosX() + s.getW() / 2;
        py = s.getPosY() + s.getH() / 2;

        balls.add(new Ball(sketch, px, py, initVel, speed, radius, r, g, b));
    }

    public void addBall(float posX, float posY, PVector initVel, float speed, float radius, float r, float g, float b) {
        int index = (int) (posY * squaresX + posX);
        float px, py;

        Square s = grid.get(index);
        //System.out.println(s + " square");

        px = s.getPosX() + s.getW() / 2;
        py = s.getPosY() + s.getH() / 2;

        balls.add(new Ball(px, py, initVel, speed, radius, r, g, b));
    }

    public int getSquaresX() {
        return squaresX;
    }

    public int getSquaresY() {
        return squaresY;
    }
}
