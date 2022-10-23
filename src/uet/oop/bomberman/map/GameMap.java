package uet.oop.bomberman.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.BrokenEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.maptexture.Brick;
import uet.oop.bomberman.entities.maptexture.Grass;
import uet.oop.bomberman.entities.maptexture.Portal;
import uet.oop.bomberman.entities.maptexture.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.SpeedItem;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean toNextLevel = false;
    public static String[][] map = new String[3][100];


    public static List<Brick> bricks = new ArrayList<>();
    public static List<Portal> portals = new ArrayList<>();
    public static List<Wall> walls = new ArrayList<>();
    public static List<Grass> grasses = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<BrokenEntity> brokenEntities = new ArrayList<uet.oop.bomberman.entities.BrokenEntity>();

    public static Bomber bomberMan;//= new Bomber();
    public static int gameLvl = 1;

    public static void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, WIDTH * 32, HEIGHT * 32);

        try {
            //clear broken items
            bricks.removeIf(g -> !g.isExisting());
            items.removeIf(g -> !g.isExisting());
            enemies.removeIf(g -> !g.isExisting());
            brokenEntities.removeIf(g -> !g.isExisting());
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
            bombs.forEach(g -> g.render(gc));
            for (Bomb bomb : bombs) {
                bomb.getFlames().forEach(g -> g.render(gc));
            }
            portals.forEach(g -> g.render(gc));
            items.forEach(g -> g.render(gc));
            brokenEntities.forEach(g -> g.render(gc));

            bricks.forEach(g -> g.render(gc));
            enemies.forEach(g -> g.render(gc));
            bomberMan.render(gc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createMap() {
        String[] tmpMap = GameMap.map[gameLvl];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (tmpMap[i].charAt(j) == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    GameMap.walls.add((Wall) object);
                } else if (tmpMap[i].charAt(j) == 'x') {
                    object = new Portal(j, i, Sprite.portal.getFxImage());
                    GameMap.portals.add((Portal) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (tmpMap[i].charAt(j) == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (tmpMap[i].charAt(j) == '2') {
                    object = new Balloon(j, i, Sprite.balloom_dead.getFxImage());
                    GameMap.enemies.add((Balloon) object);
                } else if (tmpMap[i].charAt(j) == '1') {
                    Oneal oneal = new Oneal(j, i, Sprite.oneal_dead.getFxImage());
                    GameMap.enemies.add(oneal);
                } else if (tmpMap[i].charAt(j) == '3') {
                    object = new Doll(j, i, Sprite.doll_left1.getFxImage());
                    GameMap.enemies.add((Doll) object);
                } else if (tmpMap[i].charAt(j) == '4') {
                    object = new Minvo(j, i, Sprite.minvo_left1.getFxImage());
                    GameMap.enemies.add((Minvo) object);
                } else if (tmpMap[i].charAt(j) == '5') {
                    object = new Kondoria(j, i, Sprite.kondoria_left1.getFxImage());
                    GameMap.enemies.add((Kondoria) object);
                } else if (tmpMap[i].charAt(j) == '6') {
                    object = new Ovapi(j, i, Sprite.ovapi_left1.getFxImage());
                    GameMap.enemies.add((Ovapi) object);
                } else if (tmpMap[i].charAt(j) == 's') {
                    object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                    GameMap.items.add((SpeedItem) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (tmpMap[i].charAt(j) == 'b') {
                    object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                    GameMap.items.add((BombItem) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (tmpMap[i].charAt(j) == 'f') {
                    object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                    GameMap.items.add((FlameItem) object);
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    GameMap.bricks.add((Brick) object);
                } else if (tmpMap[i].charAt(j) == 'p') {
                    object = new Bomber(j, i, Sprite.player_right.getFxImage());
                    GameMap.bomberMan = (Bomber) object;
                }
                if (tmpMap[i].charAt(j) != '#') {
                    GameMap.grasses.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
    }

    public static void clearAll() {
        bricks = new ArrayList<>();
        portals = new ArrayList<>();
        walls = new ArrayList<>();
        grasses = new ArrayList<>();
        enemies = new ArrayList<>();
        bombs = new ArrayList<>();
        items = new ArrayList<>();
        bomberMan = new Bomber();
    }
}
