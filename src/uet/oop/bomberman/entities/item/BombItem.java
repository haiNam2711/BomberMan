package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.map.GameMap;

public class BombItem extends Item{

    public BombItem(int j, int i, Image fxImage) {
        super(j,i,fxImage);
    }

    @Override
    void buff() {
        Bomber b = GameMap.bomberMan;
        b.setBombsNumLimit(b.getBombsNumLimit()+1);
    }
}
