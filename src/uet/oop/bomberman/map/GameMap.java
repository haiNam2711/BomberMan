package uet.oop.bomberman.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Balloon;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Grass;
import uet.oop.bomberman.entities.unmovableobject.Portal;
import uet.oop.bomberman.entities.unmovableobject.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static int WIDTH;
    public static int HEIGHT;
    public static String[] mapLv1 = new String[100];


    public static List<Brick> bricks = new ArrayList<>();
    public static List<Portal> portals = new ArrayList<>();
    public static List<Wall> walls = new ArrayList<>();
    public static List<Grass> grasses = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static Bomber bomberMan;//= new Bomber();

    public static void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //rendering
        walls.forEach(g -> g.render(gc));
        grasses.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        for (Bomb bomb : bombs) {
            bomb.getFlames().forEach(g -> g.render(gc));
        }
        bricks.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));

        bomberMan.render(gc);

        //updating
        bomberMan.update();
        enemies.forEach(Enemy::update);
        bombs.forEach(Bomb::update);
        for (Bomb bomb : bombs) {
            bomb.getFlames().forEach(Flame::update);
        }
    }

    public static void createMap() {

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
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (mapLv1[i].charAt(j) == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (mapLv1[i].charAt(j) == '1') {
                    object = new Balloon(j, i, Sprite.balloom_dead.getFxImage());
                    GameMap.enemies.add((Balloon) object);
                } else if (mapLv1[i].charAt(j) == '2') {
                    Oneal oneal = new Oneal(j, i, Sprite.oneal_dead.getFxImage());
                    GameMap.enemies.add((Oneal) oneal);
                }
                if (mapLv1[i].charAt(j) != '#') {
                    GameMap.grasses.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
    }
}
