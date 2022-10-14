package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.Random;

public class Doll extends Enemy {
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3};
        this.spritesSet2 = new Sprite[]{Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3};
    }

    @Override
    public boolean checkValidMove() {
        boolean collide = true;
        if (checkCollideWall() || checkCollideBomb() ) collide = false;
        return collide;
    }

    @Override
    public void update() {
        if (!isExisting()) {
            return;
        }
        higherLevelRandomMove();
        if (checkCollideBomber(this,GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }



}