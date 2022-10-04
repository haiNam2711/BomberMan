package uet.oop.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.CheckCollideBomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

public abstract class Item extends Entity implements CheckCollideBomber {
    protected boolean used = false;

    public Item(int j, int i, Image fxImage) {
        super(j,i,fxImage);
    }

    @Override
    public void update() {
        if (used) existing = false;
        else {
            if (checkCollideBomber(this, GameMap.bomberMan)) {
                used = true;
                buff();
            }
        }
    }

    abstract void buff();
}
