package uet.oop.bomberman.keyboarddetect;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class KeyboardDetect {
    public static void keyboardPressed(Group root, Stage stage, Scene scene, BombermanGame bG) {

        scene.setOnKeyPressed(event -> {
            Bomber bomberman = GameMap.bomberMan;
                    if (BombermanGame.isInMenu()) {
                        if (event.getCode().toString().equals("RIGHT")) {
                            bG.showGameMenu(root);
                        } else if (event.getCode().toString().equals("LEFT")) {
                            bG.showGameMenu(root);
                        } else if (event.getCode().toString().equals("UP")) {
                            bG.showGameMenu(root);
                        } else if (event.getCode().toString().equals("DOWN")) {
                            bG.showGameMenu(root);
                        } else if (event.getCode().toString().equals("SPACE")) {
                            BombermanGame.setInMenu(false);
                        }
                    } else {
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
                }

        );
    }
}
