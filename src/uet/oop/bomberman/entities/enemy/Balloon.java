package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.Random;

public class Balloon extends Enemy {

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3};
        this.spritesSet2 = new Sprite[]{Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3};
    }


    @Override
    public void update() {
        if (!isExisting()) {
            new BombermanGame().setGamePoint(BombermanGame.getGamePoint()+100);

            return;

        }
        randomMove();
        if (checkCollideBomber(this,GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }

}