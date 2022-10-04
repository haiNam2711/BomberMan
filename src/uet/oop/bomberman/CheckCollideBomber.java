package uet.oop.bomberman;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.item.Item;

public interface CheckCollideBomber {
    default boolean checkCollideBomber(Entity e, Bomber b) {
        if (e instanceof Item) {
            return b.getX()-e.getX() == 0 && b.getY()-e.getY() == 0;
        }
        if (Math.abs(b.getX()-e.getX()) + Math.abs(b.getY()-e.getY()) <= b.getMoveLength()) {
            //b.setExisting(false);
            return true;
        }
        return false;
    }
}
