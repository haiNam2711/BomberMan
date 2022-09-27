package uet.oop.bomberman.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Grass;
import uet.oop.bomberman.entities.unmovableobject.Portal;
import uet.oop.bomberman.entities.unmovableobject.Wall;

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
        bombs.forEach(Bomb::update);
        for (Bomb bomb : bombs) {
            bomb.getFlames().forEach(Flame::update);
        }
    }
}
