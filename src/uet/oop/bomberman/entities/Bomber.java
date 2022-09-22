package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GameMap;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends Entity {

    private final int moveLength = Sprite.SCALED_SIZE / 4;
    private final int[] changeX = {0, 0, 1, -1};
    private final int[] changeY = {1, -1, 0, 0};

//    public Bomber() {
//        this.x = 1;
//        this.y = 1;
//        this.img = Sprite.player_right.getFxImage();
//    }
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }



    @Override
    public void update() {

    }

    public boolean checkMove() {
        for (Brick brick : GameMap.bricks) {

            System.out.println(brick.x/32);
            System.out.println(brick.y/32);
            if (this.checkCollide(brick)) {
                return false;
            }
        }
        for (Wall wall : GameMap.walls) {
            if (this.checkCollide(wall)) {
                return false;
            }
        }
        for (Enemy enemy : GameMap.enemies) {
            if (this.checkCollide(enemy)) {
                return false;
            }
        }
        return true;
    }

    public void move(int direction) {
        System.out.println(GameMap.bricks.size());
        for (int i = 1; i <= moveLength; i++) {
            x += changeX[direction];
            y += changeY[direction];
            boolean flag = checkMove();
            if (flag == false) {
                x -= changeX[direction];
                y -= changeY[direction];
            }
        }

        switch (direction) {
            case 0: {
                setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, this.getY(), 60).getFxImage());
                break;
            }
            case 1: {
                setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, this.getY(), 60).getFxImage());
                break;
            }
            case 2: {
                setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, this.getX(), 60).getFxImage());
                break;
            }
            case 3: {
                setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, this.getX(), 60).getFxImage());
                break;
            }
        }
    }
}
