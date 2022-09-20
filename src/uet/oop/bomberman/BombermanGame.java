package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Balloon;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Grass;
import uet.oop.bomberman.entities.unmovableobject.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.keyboarddetect.KeyboardDetect;
import uet.oop.bomberman.soundplayer.SoundPlayer;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();


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

        KeyboardDetect.keyboardPressed(bomberman, scene);
        entities.add(bomberman);
    }

    public void createMap() {

        String[] mapLv1 = GameMap.mapLv1;


        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (mapLv1[i].charAt(j) == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    entities.add(object);
                } else if (mapLv1[i].charAt(j) == 'x') {
                    Brick portal = new Brick(j, i, Sprite.portal.getFxImage());
                    entities.add(portal);
                } else if (mapLv1[i].charAt(j) == '*') {
                    Brick brick = new Brick(j, i, Sprite.brick.getFxImage());
                    entities.add(brick);
                } else if (mapLv1[i].charAt(j) == '1') {
                    Balloon balloon = new Balloon(j, i, Sprite.balloom_dead.getFxImage());
                    entities.add(balloon);
                } else if (mapLv1[i].charAt(j) == '2') {
                    Oneal oneal = new Oneal(j, i, Sprite.oneal_dead.getFxImage());
                    entities.add(oneal);
                } else if (mapLv1[i].charAt(j) == 'f') {
                    Flame flame = new Flame(j, i, Sprite.powerup_flames.getFxImage());
                    entities.add(flame);
                } else if (mapLv1[i].charAt(j) == 's') {
                    Brick speed = new Brick(j, i, Sprite.powerup_speed.getFxImage());
                    entities.add(speed);
                }
                if (mapLv1[i].charAt(j) != '#'){
                    stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
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

    public List<Entity> getEntities() {
        return entities;
    }
}
