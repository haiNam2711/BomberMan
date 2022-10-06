package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.keyboarddetect.KeyboardDetect;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.map.MapReader;
import uet.oop.bomberman.soundplayer.SoundPlayer;

import java.io.IOException;

public class BombermanGame extends Application {

    private boolean isRunning = true;
    public static int WIDTH = GameMap.WIDTH;
    public static int HEIGHT = GameMap.HEIGHT;
    private GraphicsContext gc;
    private Canvas canvas;


    public static void main(String[] args) throws IOException {
        MapReader.reader(1);
        WIDTH = GameMap.WIDTH;
        HEIGHT = GameMap.HEIGHT;
        SoundPlayer.playSound("/soundt");
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
            private long lastFrameTime = 0;

            @Override
            public void handle(long nowTime) {
                if (nowTime - lastFrameTime >= 28000000) { // 120 fps
                    run();
                    if (isRunning == false) {
                        this.stop();
                    }
                    lastFrameTime = nowTime ;
                }
            }
        };
        timer.start();
        GameMap.createMap();

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        GameMap.bomberMan = bomberman;
        KeyboardDetect.keyboardPressed(bomberman, scene);

    }

    public void run() {
        GameMap.render(gc, canvas);
        if (!GameMap.bomberMan.isExisting()) {
            isRunning = false;
        }
        if (GameMap.toNextLevel) {
            isRunning = false;
            System.out.println(1);
        }
    }

}
