package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.keyboarddetect.KeyboardDetect;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.map.MapReader;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


public class BombermanGame extends Application {

    private static MediaPlayer mediaPlayer;
    private static boolean ttGameMenu = true;
    private static boolean isInMenu = true;
    private boolean changeRoot = false;
    private boolean isRunning = true;
    private static int gamePoint = 0;
    public static int WIDTH = GameMap.WIDTH;
    public static int HEIGHT = GameMap.HEIGHT;
    private GraphicsContext gc;
    private Canvas canvas;

    public static boolean isInMenu() {
        return isInMenu;
    }

    public void setGamePoint(int gamePoint) {
        this.gamePoint = gamePoint;
    }

    public static int getGamePoint() {
        return gamePoint;
    }

    public static void setInMenu(boolean inMenu) {
        isInMenu = inMenu;
    }

    public void setLevelState(int lS) {
        levelState = lS;
    }

    private static int levelState = 0;


    public static void main(String[] args) throws IOException {
        MapReader.reader(1);
        MapReader.reader(2);
        WIDTH = GameMap.WIDTH;
        HEIGHT = GameMap.HEIGHT;
        Media sound = new Media(new File("res/soundtrack/soundt.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        Application.launch(BombermanGame.class);

    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 32);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        Text text = new Text(100, 440, "Point: " + gamePoint);
        Font font = new Font("Serif", 23);
        text.setFont(font);
        text.setFill(Color.WHITE);

        // Tao scene
        Scene scene = new Scene(root, Color.BLACK);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        showGameMenu(root);

        AnimationTimer timer = new AnimationTimer() {
            private long lastFrameTime = 0;

            @Override
            public void handle(long nowTime) {

                if (nowTime - lastFrameTime >= 28000000) {// 120 fps
                    if (isRunning) {
                        if (!isInMenu) {
                            if (!changeRoot) {
                                mediaPlayer.play();
                                changeRoot = true;
                                root.getChildren().clear();
                                root.getChildren().add(canvas);
                            }
                            root.getChildren().remove(text);
                            text.setText("Point : " + gamePoint);
                            root.getChildren().add(text);
                            run();
                        }
                    } else {
                        if (GameMap.toNextLevel) {
                            if (GameMap.gameLvl == 3) {
                                if (levelState == 1) {
                                    root.getChildren().clear();
                                    Text text2 = new Text(440, 220, "");
                                    Font font2 = new Font("Serif", 23);
                                    text2.setText("Congratulation! ");
                                    text2.setFont(font2);
                                    text2.setFill(Color.WHITE);
                                    root.getChildren().add(text2);
                                    TimerTask timerTask1 = new TimerTask() {
                                        @Override
                                        public void run() {
                                            System.exit(1);
                                        }
                                    };
                                    Timer timer1 = new Timer();
                                    timer1.schedule(timerTask1, 1000L);
                                    levelState = 2;
                                }

                            } else {
                                mediaPlayer.stop();
                                GameMap.clearAll();
                                gc.clearRect(0, 0, WIDTH * 32, HEIGHT * 32);
                                root.getChildren().clear();
                                Text text2 = new Text(440, 220, "");
                                Font font2 = new Font("Serif", 23);
                                text2.setText("Next Level");
                                text2.setFont(font2);
                                text2.setFill(Color.WHITE);
                                root.getChildren().add(text2);
                                if (levelState == 0) {
                                    GameMap.gameLvl++;
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                                        if (GameMap.gameLvl != 3) {
                                            mediaPlayer.play();
                                            root.getChildren().add(canvas);
                                            GameMap.clearAll();
                                            GameMap.createMap();
                                            isRunning = true;
                                            GameMap.toNextLevel = false;
                                        }
                                    }));
                                    timeline.setCycleCount(1);
                                    timeline.play();

                                    levelState = 1;
                                }
                            }
                        } else {
                            if (levelState == 0) {
                                mediaPlayer.stop();
                                GameMap.clearAll();
                                gc.clearRect(0, 0, WIDTH * 32, HEIGHT * 32);
                                root.getChildren().clear();
                                Text text2 = new Text(440, 220, "");
                                Font font2 = new Font("Serif", 23);
                                text2.setText("Game Over! ");
                                text2.setFont(font2);
                                text2.setFill(Color.WHITE);
                                root.getChildren().add(text2);
                                levelState++;
                            } else {
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), ev -> {
                                    this.stop();
                                    System.exit(1);
                                }));
                                timeline.setCycleCount(1);
                                timeline.play();
                            }
                        }
                    }
                    lastFrameTime = nowTime;
                }
            }
        };
        timer.start();
        GameMap.createMap();

        KeyboardDetect.keyboardPressed(root, stage, scene, this);

    }

    public void run() {
        GameMap.render(gc, canvas);
        if (!GameMap.bomberMan.isExisting()) {
            isRunning = false;
        }
        if (GameMap.toNextLevel) {
            isRunning = false;
        }
    }

    public void showGameMenu(Group root) {


        //creating the image object
        InputStream stream = null;
        try {
            if (ttGameMenu == true) {
                stream = new FileInputStream("res/levels/title.png");
                ttGameMenu = !ttGameMenu;
            } else {
                stream = new FileInputStream("res/levels/title2.png");
                ttGameMenu = !ttGameMenu;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(stream);
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(image);
        //Setting the image view parameters
        imageView.setX(0);
        imageView.setY(16);
        imageView.setFitWidth(31 * 32);
        imageView.setPreserveRatio(true);
        //Setting the Scene object
        root.getChildren().clear();
        root.getChildren().add(imageView);


    }
}