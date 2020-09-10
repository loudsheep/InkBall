package base;

import game.Grid;
import processing.core.PApplet;
import reader.MapFromFile;

public class Base extends PApplet {

    Grid grid;

    //Square sq;

    public void settings() {
        size(800, 800);
    }

    public void setup() {

//        grid = new Grid(this, 40, 40, width, height);
//        grid.addBall(this, 3, 9, 0, 0, 255);
//        grid.addBall(this, 1, 1, new PVector(5, 0), 3, 9, 255, 111, 0);

        MapFromFile m = new MapFromFile("levels/level1.map", width, height);
        grid = m.generate();
        grid.setAll(this);
        //grid.addBall(this, 3, 18, 0, 0, 255);


        //sq = new Square(0,0,20,20, Square.TYPE.WALL);
    }

    public void draw() {

        background(220);
        //sq.show();
        grid.show();
        grid.update(mouseX, mouseY, pmouseX, pmouseY);


        //noLoop();

        //if(random(1) < 0.01)grid.addBall(this, 3, 9, 0, 0, 255);
    }

    public void mousePressed() {
        grid.startDraw(mouseX,mouseY,pmouseX,pmouseY);
        System.out.println("Start drawing");
    }

    public void mouseReleased() {
        grid.stopDraw();
        System.out.println("Stop drawing");
    }


    public static void main(String[] args) {
        PApplet.main("base.Base", args);
    }
}