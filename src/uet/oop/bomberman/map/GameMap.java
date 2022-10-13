package uet.oop.bomberman.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.maptexture.Brick;
import uet.oop.bomberman.entities.maptexture.Grass;
import uet.oop.bomberman.entities.maptexture.Portal;
import uet.oop.bomberman.entities.maptexture.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.item.BombItem;
import uet.oop.item.FlameItem;
import uet.oop.item.Item;
import uet.oop.item.SpeedItem;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean toNextLevel = false;
    public static String[] mapLv1 = new String[100];


    public static List<Brick> bricks = new ArrayList<>();
    public static List<Portal> portals = new ArrayList<>();
    public static List<Wall> walls = new ArrayList<>();
    public static List<Grass> grasses = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static Bomber bomberMan;//= new Bomber();

    public static void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        try {
            //clear broken items
            bricks.removeIf(g -> !g.isExisting());
            items.removeIf(g -> !g.isExisting());
            enemies.removeIf(g -> !g.isExisting());
            for (Bomb bomb : bombs) {
                if (!bomb.isExisting()) continue;
                bomb.getFlames().removeIf(g -> !g.isExisting());
            }

            //updating
            portals.forEach(Portal::update);
            bomberMan.update();
            for (Bomb bomb : bombs) {
                if (!bomb.isExisting()) continue;
                bomb.getFlames().forEach(Flame::update);
            }
            enemies.forEach(Enemy::update);
            items.forEach(Item::update);
            bombs.forEach(Bomb::update);


            //rendering
            walls.forEach(g -> g.render(gc));
            grasses.forEach(g -> g.render(gc));
            portals.forEach(g -> g.render(gc));
            for (Bomb bomb : bombs) {
                bomb.getFlames().forEach(g -> g.render(gc));
            }
            items.forEach(g -> g.render(gc));
            bricks.forEach(g -> g.render(gc));
            bombs.forEach(g -> g.render(gc));
            enemies.forEach(g -> g.render(gc));
            bomberMan.render(gc);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
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
                    GameMap.enemies.add(oneal);
                } else if (mapLv1[i].charAt(j) == '3') {
                    object = new Doll(j, i, Sprite.doll_dead.getFxImage());
                    GameMap.enemies.add((Doll) object);
                } else if (mapLv1[i].charAt(j) == '4') {
                    object = new Minvo(j, i, Sprite.minvo_dead.getFxImage());
                    GameMap.enemies.add((Minvo) object);
                } else if (mapLv1[i].charAt(j) == 's') {
                    object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                    GameMap.items.add((SpeedItem) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (mapLv1[i].charAt(j) == 'b') {
                    object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                    GameMap.items.add((BombItem) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (mapLv1[i].charAt(j) == 'f') {
                    object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                    GameMap.items.add((FlameItem) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                }
                if (mapLv1[i].charAt(j) != '#') {
                    GameMap.grasses.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
    }
}
