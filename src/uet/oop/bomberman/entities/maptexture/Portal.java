package uet.oop.bomberman.entities.maptexture;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.CheckCollideBomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

import static uet.oop.bomberman.map.GameMap.*;

public class Portal extends Entity implements CheckCollideBomber {
    private boolean opened = false;

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (enemies.size() == 0) {
            new BombermanGame().setLevelState(0);
            opened = true;
        }
        if (opened) {
            if (checkCollideBomber(this, bomberMan)) {
                toNextLevel = true;
            }
        }
    }
}
