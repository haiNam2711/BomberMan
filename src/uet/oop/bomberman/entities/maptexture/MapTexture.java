package uet.oop.bomberman.entities.maptexture;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class MapTexture extends Entity {

    public MapTexture(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public MapTexture() {
    }

    @Override
    public void update() {
        if (!isExisting()) {
            return;
        }
    }
}
