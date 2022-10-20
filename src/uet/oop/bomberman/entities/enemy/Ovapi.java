package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;


public class Ovapi extends Enemy {
    public Ovapi(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.ovapi_right1, Sprite.ovapi_right2, Sprite.ovapi_right3};
        this.spritesSet2 = new Sprite[]{Sprite.ovapi_left1, Sprite.ovapi_left2, Sprite.ovapi_left3};
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