package uet.oop.bomberman;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

public interface CheckCollideBomber {
    default boolean checkCollideBomber(Entity e, Bomber b) {
        if (Math.abs(b.getX()-e.getX()) + Math.abs(b.getY()-e.getY()) <= b.getMoveLength()) {
            //b.setExisting(false);
            return true;
        }
        return false;
    }
}
