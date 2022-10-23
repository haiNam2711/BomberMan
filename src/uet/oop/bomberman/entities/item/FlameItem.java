package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomb.Bomb;

public class FlameItem extends Item{

    public FlameItem(int j, int i, Image fxImage) {
        super(j, i, fxImage);
    }

    @Override
    void buff() {
        (new Bomb(32,32)).setFlameLength();
    }
}
