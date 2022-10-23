package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.map.GameMap;

public class SpeedItem extends Item{
    public SpeedItem(int j, int i, Image fxImage) {
        super(j,i,fxImage);
    }


    @Override
    void buff() {
        Bomber b = GameMap.bomberMan;
        b.setMoveLength(b.getMoveLength() + 3);
    }
}
