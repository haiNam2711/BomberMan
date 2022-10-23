package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BrokenEntity;
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
            new BombermanGame().setGamePoint(BombermanGame.getGamePoint()+100);
            GameMap.brokenEntities.add(new BrokenEntity(x,y,6));
            return;
        }
        if (BombermanGame.isInRandomMapMode) randomMove(); else higherLevelRandomMove();
        if (checkCollideBomber(this,GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }



}