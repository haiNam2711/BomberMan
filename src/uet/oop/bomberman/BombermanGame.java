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
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Grass;
import uet.oop.bomberman.entities.unmovableobject.Portal;
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


    public static void main(String[] args) {
        //SoundPlayer.playSound("/soundtrack");
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
                GameMap.render(gc,canvas);
            }
        };
        timer.start();
        createMap();

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        GameMap.bomberMan = bomberman;
        KeyboardDetect.keyboardPressed(bomberman, scene);
        
    }

    public void createMap() {

        String[] mapLv1 = GameMap.mapLv1;


        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (mapLv1[i].charAt(j) == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    GameMap.walls.add((Wall) object);
                } else if (mapLv1[i].charAt(j) == 'x') {
                    object = new Portal(j, i, Sprite.portal.getFxImage());
                    GameMap.portals.add((Portal) object);
                } else if (mapLv1[i].charAt(j) == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick)object);
                } else if (mapLv1[i].charAt(j) == '1') {
                    object = new Balloon(j, i, Sprite.balloom_dead.getFxImage());
                    GameMap.enemies.add((Balloon) object);
                } else if (mapLv1[i].charAt(j) == '2') {
                    Oneal oneal = new Oneal(j, i, Sprite.oneal_dead.getFxImage());
                    GameMap.enemies.add((Oneal)oneal);
                }
                if (mapLv1[i].charAt(j) != '#'){
                    GameMap.grasses.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
    }

//    public void update() {
//        entities.forEach(Entity::update);
//    }

//    public void render() {
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        stillObjects.forEach(g -> g.render(gc));
//        entities.forEach(g -> g.render(gc));
//    }

//    public List<Entity> getEntities() {
//        return entities;
//    }

}
