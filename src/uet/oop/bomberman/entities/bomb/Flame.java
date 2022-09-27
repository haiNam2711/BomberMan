package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameMap;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    public Flame() {
    }

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
//        System.out.println(xUnit);
//        System.out.println(yUnit);
    }

    public boolean checkCollideBomber(Bomber b) {
        if (Math.abs(b.getX()-x) + Math.abs(b.getY()-y) <= b.getMoveLength()) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        for (Brick brick: GameMap.bricks) {
            if (this.checkCollide(brick)) {
                this.setExisting(false);
                brick.setExisting(false);
            }
        }
        for (Enemy enemy: GameMap.enemies) {
            if (this.checkCollide(enemy)) {
                this.setExisting(false);
                enemy.setExisting(false);
            }
        }
        if (!this.checkCollideBomber(GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }
}
