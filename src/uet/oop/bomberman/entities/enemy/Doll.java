package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.maptexture.BrokenEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Doll extends Enemy {
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3};
        this.spritesSet2 = new Sprite[]{Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3};
    }

    @Override
    public boolean checkValidMove() {
        try {
            boolean collide = true;
            if (checkCollideWall() || checkCollideBomb()) collide = false;
            return collide;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void update() {
        if (!isExisting()) {
            new BombermanGame().setGamePoint(BombermanGame.getGamePoint() + 100);
            GameMap.brokenEntities.add(new BrokenEntity(x,y,3));
            return;
        }
        if (BombermanGame.isInRandomMapMode) randomMove(); else higherLevelRandomMove();
        if (checkCollideBomber(this, GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }


}