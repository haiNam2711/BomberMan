package uet.oop.bomberman.keyboarddetect;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class KeyboardDetect {
    public static void keyboardPressed(Bomber bomberman, Scene scene) {
        scene.setOnKeyPressed(event -> {
                    if (event.getCode().toString().equals("RIGHT")) {
                        bomberman.setDirection(2);
                    } else if (event.getCode().toString().equals("LEFT")) {
                        bomberman.setDirection(3);
                    } else if (event.getCode().toString().equals("UP")) {
                        bomberman.setDirection(1);
                    } else if (event.getCode().toString().equals("DOWN")) {
                        bomberman.setDirection(0);
                    } else if (event.getCode().toString().equals("SPACE")) {
                        bomberman.setDirection(4);
                    }
                }
        );
    }
}
