package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.keyboarddetect.KeyboardDetect;
import uet.oop.bomberman.soundplayer.SoundPlayer;

import java.util.ArrayList;
import java.util.List;


public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();



    public static void main(String[] args) {
        SoundPlayer.playSound("soundtrack");
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());

        entities.add(bomberman);

        KeyboardDetect.keyboardPressed(bomberman, scene);
    }

    public void createMap() {

        for (int i = 0; i < mapLv1.length; i++) {

            for (int j = 0; j < mapLv1[i].length(); j++) {
                Entity object;
                if (mapLv1[i].charAt(j) == '#') object = new Wall(j, i, Sprite.wall.getFxImage());
                else if (mapLv1[i].charAt(j) == 'x') {
                    Portal portal = new Portal(j, i, Sprite.portal.getFxImage());
                    object = new Grass(j, i, Sprite.grass.getFxImage());

                    entities.add(portal);
                } else if (mapLv1[i].charAt(j) == '*') {
                    Brick brick = new Brick(j, i, Sprite.brick.getFxImage());
                    object = new Grass(j, i, Sprite.grass.getFxImage());

                    entities.add(brick);
                } else if (mapLv1[i].charAt(j) == '1') {
                    Balloon balloon = new Balloon(j, i, Sprite.balloom_dead.getFxImage());
                    object = new Grass(j, i, Sprite.grass.getFxImage());

                    entities.add(balloon);
                } else if (mapLv1[i].charAt(j) == '2') {
                    Oneal oneal = new Oneal(j, i, Sprite.oneal_dead.getFxImage());
                    object = new Grass(j, i, Sprite.grass.getFxImage());

                    entities.add(oneal);
                } else if (mapLv1[i].charAt(j) == 'f') {
                    Flame flame = new Flame(j, i, Sprite.powerup_flames.getFxImage());
                    object = new Grass(j, i, Sprite.grass.getFxImage());

                    entities.add(flame);
                } else if (mapLv1[i].charAt(j) == 's') {
                    Speed speed = new Speed(j, i, Sprite.powerup_speed.getFxImage());
                    object = new Grass(j, i, Sprite.grass.getFxImage());

                    entities.add(speed);
                } else object = new Grass(j, i, Sprite.grass.getFxImage());

                stillObjects.add(object);
            }
        }

    }


    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public static String[] mapLv1 = {
            "###############################",
            "#p     ** *  1 * 2 *  * * *   #",
            "# # # #*# # #*#*# # # #*#*#*# #",
            "#  x*     ***  *  1   * 2 * * #",
            "# # # # # #*# # #*#*# # # # #*#",
            "#f         x **  *  *   1     #",
            "# # # # # # # # # #*# #*# # # #",
            "#*  *      *  *      *        #",
            "# # # # #*# # # #*#*# # # # # #",
            "#*    **  *       *           #",
            "# #*# # # # # # #*# # # # # # #",
            "#           *   *  *          #",
            "###############################"
    };
}
