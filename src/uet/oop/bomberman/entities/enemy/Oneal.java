package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.map.GameMap;

public class Oneal extends Enemy {
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Oneal(int scaleX, int scaleY) {
        super(scaleX, scaleY);
    }

    @Override
    public void update() {
        Bomber bomber = GameMap.bomberMan;
        int bomberX = bomber.getX();
        int bomberY = bomber.getY();

    }
}
