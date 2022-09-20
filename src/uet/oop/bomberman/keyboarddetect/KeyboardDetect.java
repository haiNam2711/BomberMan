package uet.oop.bomberman.keyboarddetect;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class KeyboardDetect {
    public static void keyboardPressed(Bomber bomberman, Scene scene) {
        scene.setOnKeyPressed(event -> {
                    if (event.getCode().toString().equals("RIGHT")) {
                        bomberman.move(2);
                    } else if (event.getCode().toString().equals("LEFT")) {
                        bomberman.move(3);
                    } else if (event.getCode().toString().equals("UP")) {
                        bomberman.move(1);
                    } else if (event.getCode().toString().equals("DOWN")) {
                        bomberman.move(0);
                    }
                }
        );
    }
}
