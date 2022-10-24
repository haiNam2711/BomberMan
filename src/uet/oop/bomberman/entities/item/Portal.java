package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.CheckCollideBomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

import static uet.oop.bomberman.map.GameMap.*;

public class Portal extends Item {

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (used) existing = false;
        else {
            if (GameMap.enemies.size() == 0 && this.checkCollide(GameMap.bomberMan)) {
                used = true;
                buff();
            }
        }
    }

    @Override
    void buff() {
        new BombermanGame().setLevelState(0);
        toNextLevel = true;
    }
}
