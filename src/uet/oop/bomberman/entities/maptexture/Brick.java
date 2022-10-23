package uet.oop.bomberman.entities.maptexture;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BrokenEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (!isExisting()) {
            return;
        }
    }
}
