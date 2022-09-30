package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloon extends Enemy {
    private final int moveLength = Sprite.SCALED_SIZE /32;
    private int direction = -1;



    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }



    public int generate(){
        Random generator = new Random();
        int value = generator.nextInt((3 - 0) + 1);
        return  value;
    }
    @Override
    public void update() {
        if (direction != -1) {
            x += changeX[direction] * moveLength;
            y += changeY[direction] * moveLength;
            if (!checkValidMove()) {
                return;
            }
            x -= changeX[direction] * moveLength;
            y -= changeY[direction] * moveLength;
        }

        this.setDirection(generate());
        x += changeX[direction] * moveLength;
        y += changeY[direction] * moveLength;
        if (checkValidMove()) {
            x -= changeX[direction] * moveLength;
            y -= changeY[direction] * moveLength;
        } else {
            switch (direction) {
                //        changeX = {0, 0, 1, -1};     D-U-R-L
                //        changeY = {1, -1, 0, 0};     0-1-2-3
                case 0:
                case 2: {
                    setImg(Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, this.getX(), 60).getFxImage());
                    break;
                }
                case 1:
                case 3: {
                    setImg(Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, this.getX(), 60).getFxImage());
                    break;
                }
            }
        }
        //direction = -1;
    }


    public boolean checkValidMove() {

        boolean collide = false;
        if (checkCollideWall() || checkCollideBrick() || checkCollideBomb() ) collide = true;
        return collide;
    }
}