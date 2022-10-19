package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.maptexture.Brick;
import uet.oop.bomberman.entities.maptexture.Wall;
import uet.oop.bomberman.graphics.Sprite;


import java.awt.*;
import java.util.Objects;

import static java.lang.Math.abs;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;
    protected boolean existing = true;

    protected final int[] changeX = {0, 0, 1, -1};
    protected final int[] changeY = {1, -1, 0, 0};

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }
    public Entity(int scaleX, int scaleY) {
        this.x = scaleX;
        this.y = scaleY;
    }

    public Entity() {
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public boolean checkCollide(Entity another) {
        Rectangle surrounding1 = new Rectangle(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        Rectangle surrounding2 = new Rectangle(another.x, another.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        if (surrounding2.intersects(surrounding1)) return true;
        return false;
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public boolean checkCollideWall() {
        for (Wall wall : GameMap.walls) {
            if (this.checkCollide(wall)) return true;
        }
        return false;
    }
    public boolean checkCollideBrick() {
        for (Brick brick : GameMap.bricks) {
            if (!brick.existing) continue;
            if (this.checkCollide(brick)) return true;
        }
        return false;
    }
    public boolean checkCollideEnemy() {
        for (Enemy enemy : GameMap.enemies) {
            if (this.checkCollide(enemy)) return true;
        }
        return false;
    }

    public boolean checkCollideBomb() {
        for (Bomb bomb : GameMap.bombs) {
            if (Objects.isNull(bomb)) continue;
            if (!bomb.isExisting()) return false;
            Bomb bbomb = new Bomb(bomb.getX(),bomb.getY());
            if (this.checkCollide(bbomb)) return true;
        }
        return false;
    }

}
