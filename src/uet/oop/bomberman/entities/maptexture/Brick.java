package uet.oop.bomberman.entities.maptexture;

import javafx.scene.image.Image;

public class Brick extends MapTexture {

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
