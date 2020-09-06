package game;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Ball {

    private PApplet sketch;     // drawing windows reference
    private PVector pos;        // position vector
    private PVector vel;        // velocity vector
    private float speed;        // ball speed
    private float radius;       // ball radius
    private final float rad;    // const for init radius (see collide method)
    private float r, g, b;      // color r, color g, color b

    public Ball(PApplet sketch, float posX, float posY, float speed, float radius, float r, float g, float b) { // contructor
        this.sketch = sketch;
        this.pos = new PVector(posX, posY);
        this.vel = PVector.random2D().setMag(speed);
        this.speed = speed;
        this.radius = radius;
        rad = radius;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Ball(PApplet sketch, float posX, float posY, PVector initVel, float speed, float radius, float r, float g, float b) { // constructor
        this.sketch = sketch;
        this.pos = new PVector(posX, posY);
        this.vel = initVel;
        this.vel.setMag(speed);
        this.speed = speed;
        this.radius = radius;
        rad = radius;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Ball(float posX, float posY, float speed, float radius, float r, float g, float b) { // constructor
        this.pos = new PVector(posX, posY);
        this.vel = PVector.random2D().setMag(speed);
        this.speed = speed;
        this.radius = radius;
        rad = radius;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Ball(float posX, float posY, PVector initVel, float speed, float radius, float r, float g, float b) { // constructor
        this.pos = new PVector(posX, posY);
        this.vel = initVel;
        this.vel.setMag(speed);
        this.speed = speed;
        this.radius = radius;
        rad = radius;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setSketch(PApplet sketch) { // set windows reference
        this.sketch = sketch;
    }

    public boolean intersect(Square s) { // now unused

        if (pos.y + radius > s.getPosY() && pos.y < s.getPosY() + s.getH() && pos.x > s.getPosX() && pos.x < s.getPosX() + s.getW()) { // up side
            System.out.println("up");
            return true;
        }

        if (pos.y - radius < s.getPosY() + s.getH() && pos.y > s.getPosY() && pos.x > s.getPosX() && pos.x < s.getPosX() + s.getW()) { // down side
            System.out.println("down");
            return true;
        }

        if (pos.x + radius > s.getPosX() && pos.x < s.getPosX() + s.getW() && pos.y > s.getPosY() && pos.y < s.getPosY() + s.getH()) { // left side
            System.out.println("left");
            return true;
        }

        if (pos.x - radius < s.getPosX() + s.getW() && pos.x > s.getPosX() && pos.y > s.getPosY() && pos.y < s.getPosY() + s.getH()) { // right side
            System.out.println("right");
            return true;
        }


        return false;

    }

    private void applyForce(PVector f) { // apply force to velocity vector
        vel.add(f);
    }

    public void collide(ArrayList<Square> squares) { // check collision for all squares


        /*
            Square class parameters:
                - posX, posY (left upper corner of square) (get by getPosX(), and getPosY() methods)
                - w, h (width and height of square) (get by getW() and getH() methods)
                - type (type of square (WALL, FREE, SPAWN, HOLE, ONE_WAY_UP, ONE_WAY_RIGHT, ONE_WAY_DOWN, ONE_WAY_LEFT)) (get by getType() method) (for comparison use Square.TYPE.**SQUARE_TYPE**)
         */

        for (Square s : squares) { // for every square in squares array (s is reference to object)

            if (s.getType() == Square.TYPE.FREE || s.getType() == Square.TYPE.SPAWN) // skip free spaces and 'spawners'
                continue;

            /*
                check collision with 'one way' blocks
                !!! have to be improved (wierd things happen when inside that block and having opposite velocity ( for example 'one way up' block and velocity pointing down) !!!
             */
            if (s.getType() == Square.TYPE.ONE_WAY_DOWN && vel.y > 0) continue;
            if (s.getType() == Square.TYPE.ONE_WAY_UP && vel.y < 0) continue;
            if (s.getType() == Square.TYPE.ONE_WAY_RIGHT && vel.x > 0) continue;
            if (s.getType() == Square.TYPE.ONE_WAY_LEFT && vel.x < 0) continue;

            if (s.getType() == Square.TYPE.HOLE) {
                //continue;

                float d = PApplet.dist(pos.x, pos.y, s.getPosX() + s.getH() / 2, s.getPosY() + s.getH() / 2);

                if (d < s.attractionR) { // ball inside hole

                    if (d < s.attractionR / 3) { // when too close delete this ball
                        s.del(this);
                        return;
                    }

                    float deltaX = s.getPosX() + s.getW() / 2 - pos.x;
                    float deltaY = s.getPosY() + s.getH() / 2 - pos.y;

                    PVector f = new PVector(deltaX, deltaY).setMag(0.5f);

                    //vel.add(f).div(1.5f);
                    applyForce(f.div(1.5f));
                    radius = PApplet.map(d, 0, s.attractionR, rad/1.5f, rad); // for '3D' effect when sliding down the hole (maps ball radius acording to dist frmo hole center


                    return;
                }

                radius = rad; // when not close to hole make radius 'normal'

                continue;
            }


            if (pos.y + radius >= s.getPosY() && pos.y < s.getPosY() + s.getH() && pos.x >= s.getPosX() && pos.x <= s.getPosX() + s.getW()) { // up side collision
                vel.y = -vel.y;
                pos.y = s.getPosY() - radius;

                return;
            }

            if (pos.y - radius <= s.getPosY() + s.getH() && pos.y > s.getPosY() && pos.x >= s.getPosX() && pos.x <= s.getPosX() + s.getW()) { // down side collision
                vel.y = -vel.y;
                pos.y = s.getPosY() + s.getH() + radius;

                return;
            }

            if (pos.x + radius >= s.getPosX() && pos.x < s.getPosX() + s.getW() && pos.y >= s.getPosY() && pos.y <= s.getPosY() + s.getH()) { // left side collision
                vel.x = -vel.x;
                pos.x = s.getPosX() - radius;

                return;
            }

            if (pos.x - radius <= s.getPosX() + s.getW() && pos.x > s.getPosX() && pos.y >= s.getPosY() && pos.y <= s.getPosY() + s.getH()) { // right side collision
                vel.x = -vel.x;
                pos.x = s.getPosX() + s.getW() + radius;

                return;
            }


            float nearestX = PApplet.max(s.getPosX(), PApplet.min(pos.x, s.getPosX() + s.getW()));
            float nearestY = PApplet.max(s.getPosY(), PApplet.min(pos.y, s.getPosY() + s.getH()));


            PVector dist = new PVector(pos.x - nearestX, pos.y - nearestY);

            if (dist.mag() < radius && (nearestX == s.getPosX() || nearestX == s.getPosX() + s.getW() || nearestY == s.getPosY() || nearestY == s.getPosY() + s.getH())) { // collision for edges

                System.out.println("new collision =============================="); // debug

                PVector normal = new PVector(-dist.y, dist.x);

                float normalAngle = PApplet.atan2(normal.y, normal.x);
                float incoming = PApplet.atan2(vel.y, vel.x);

                float theta = normalAngle - incoming;

                vel = vel.rotate(2 * theta);

                while (dist.mag() < radius) {
                    move();
                    nearestX = PApplet.max(s.getPosX(), PApplet.min(pos.x, s.getPosX() + s.getW()));
                    nearestY = PApplet.max(s.getPosY(), PApplet.min(pos.y, s.getPosY() + s.getH()));

                    dist = new PVector(pos.x - nearestX, pos.y - nearestY);
                }

                return;
            }

        }
    }

    public void move() { // moves ball
        vel.setMag(speed);
        pos.add(vel);
    }

    public PVector between(PVector start, PVector end) { // returns vector between two vectors (used for line collision)
        return new PVector(end.x - start.x, end.y - start.y);
    }

    public PVector pointOnLineClosestToCircle(Ink.Line line) { // as name suggest returns pointOnLineClosestToCircle

        PVector endPoint1 = line.start;
        PVector endPoint2 = line.end;

        PVector lineUnitVector = between(endPoint1, endPoint2).normalize();

        PVector lineEnd = between(endPoint1, pos);

        float proj = PVector.dot(lineEnd, lineUnitVector);

        if (proj <= 0) {
            return endPoint1;
        }

        if (proj >= line.len) {
            return endPoint2;
        }

        return new PVector(endPoint1.x + lineUnitVector.x * proj, endPoint1.y + lineUnitVector.y * proj);

    }

    public boolean lineIntersecting(Ink.Line line) { // checks if intersecting with line
        PVector closest = pointOnLineClosestToCircle(line);

        float circleToLineDist = PVector.dist(pos, closest);

        return circleToLineDist < radius + 5; // ... + 5 is line thickness
    }

    public PVector bounceLineNormal(Ink.Line line) {
        PVector closest = between(pointOnLineClosestToCircle(line), pos);

        return closest.normalize();
    }

    public void collide(LineHandler handler) {
        if (handler.lines.size() == 0) { // no lines in hadler, no collision
            return;
        }
        for (int i = handler.lines.size() - 1; i >= 0; i--) { // reversed loop to secure from skipping lines after deleting one
            Ink ink = handler.lines.get(i);
            for (Ink.Line l : ink.line) {

                if (!lineIntersecting(l)) continue;

                PVector normal = bounceLineNormal(l);

                float dot = PVector.dot(vel, normal);

                vel.x -= 2 * dot * normal.x;
                vel.y -= 2 * dot * normal.y;

                vel.setMag(speed);
                pos.add(vel);

                handler.stopDraw(true);
                handler.lines.remove(i);

                return;

            }

        }

    }

    public void show() { // show ball on screen
        sketch.noStroke();
        sketch.fill(r, g, b);

        sketch.ellipse(pos.x, pos.y, radius * 2, radius * 2);
    }

}
