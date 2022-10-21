package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;



public class Kondoria extends Enemy {
    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3};
        this.spritesSet2 = new Sprite[]{Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3};
    }

    public void superMove() {
        if (direction != -1) {
            x += changeX[direction] * moveLength * 2;
            y += changeY[direction] * moveLength * 2;
            if (checkValidMove()) {
                return;
            }
            x -= changeX[direction] * moveLength * 2;
            y -= changeY[direction] * moveLength * 2;
        }
        switch (direction) {
            //        changeX = {0, 0, 1, -1};     D-U-R-L
            //        changeY = {1, -1, 0, 0};     0-1-2-3
            case 0:
            case 2: {
                setImg(Sprite.movingSprite(spritesSet1[0], spritesSet1[1], spritesSet1[2], this.getX(), 60).getFxImage());
                break;
            }
            case 1:
            case 3: {
                setImg(Sprite.movingSprite(spritesSet2[0], spritesSet2[1], spritesSet2[2], this.getX(), 60).getFxImage());
                break;
            }
        }
    }

    @Override
    public void update() {
        if (!isExisting()) {
            new BombermanGame().setGamePoint(BombermanGame.getGamePoint()+100);
            return;
        }
        if (this.getX() == GameMap.bomberMan.getX()) {
            if (GameMap.bomberMan.getY() - this.getY() > 0) direction = 0;
            else if (GameMap.bomberMan.getY() - this.getY() <= 0) direction = 1;
            superMove();
        }
        if (this.getY() == GameMap.bomberMan.getY()) {
            if (GameMap.bomberMan.getX() - this.getX() >= 0) direction = 2;
            else if (GameMap.bomberMan.getX() - this.getX() <= 0) direction = 3;
            superMove();
        }
        randomMove();
        if (checkCollideBomber(this, GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }


}