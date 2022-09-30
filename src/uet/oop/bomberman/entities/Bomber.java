package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Bomber extends Entity {

    private final int moveLength = Sprite.SCALED_SIZE / 4;
    private final Image[] directionImages = {Sprite.player_down.getFxImage(), Sprite.player_up.getFxImage(), Sprite.player_right.getFxImage(), Sprite.player_left.getFxImage()};
    private int direction = -1;
    private int bombsNumLimit = 2;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomber() {
    }

    public int getMoveLength() {
        return moveLength;
    }

    @Override
    public void update() {
        if (direction == 4) {
            layBomb();
            direction = -1;
            return;
        }
        if (direction == -1) {
            return;
        }
        x += changeX[direction] * moveLength;
        y += changeY[direction] * moveLength;

        boolean flag = checkValidMove();
        if (flag == false) {
            x -= changeX[direction] * moveLength;
            y -= changeY[direction] * moveLength;
            roundAxisMove(direction);
        } else {
            switch (direction) {
                //        changeX = {0, 0, 1, -1};     D-U-R-L
                //        changeY = {1, -1, 0, 0};     0-1-2-3
                case 0: {
                    setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, this.getY(), 60).getFxImage());
                    break;
                }
                case 1: {
                    setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, this.getY(), 60).getFxImage());
                    break;
                }
                case 2: {
                    setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, this.getX(), 60).getFxImage());
                    break;
                }
                case 3: {
                    setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, this.getX(), 60).getFxImage());
                    break;
                }
            }
        }
        direction = -1;
    }

    public void layBomb() {
        int nowX = x;
        int nowY = y;
        nowX = (nowX % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 2) ? (nowX / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE : (nowX / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE;
        nowY = (nowY % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 2) ? (nowY / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE : (nowY / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE;
        Bomb newBomb = new Bomb(nowX / Sprite.SCALED_SIZE, nowY / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        boolean isAlreadyExist = false;
        for (Bomb bomb : GameMap.bombs) {
            if (newBomb.checkCollide(bomb)) {
                isAlreadyExist = true;
                break;
            }
        }
        if (isAlreadyExist == false && GameMap.bombs.size() < bombsNumLimit) {
            GameMap.bombs.add(newBomb);

            //Activate Exploring the Bomb
            TimerTask timerTask1 = new TimerTask() {
                @Override
                public void run() {
                    newBomb.setExplored(true);
                    newBomb.addFlameFourDiretion();
                }
            };
            Timer timer1 = new Timer();
            timer1.schedule(timerTask1, 3000L);

            //Deleting the Broken Obtacles
            TimerTask timerTask2 = new TimerTask() {
                @Override
                public void run() {
                    newBomb.isExisting = false;
                    GameMap.bricks.removeIf(g -> !g.isExisting);
                    GameMap.enemies.removeIf(g -> !g.isExisting);
                    GameMap.bombs.removeIf(g -> !g.isExisting);
                }
            };
            Timer timer2 = new Timer();
            timer2.schedule(timerTask2, 3500L);
        }
    }

    public boolean checkMoveBomb() {
        switch (direction) {
            //        changeX = {0, 0, 1, -1};     D-U-R-L
            //        changeY = {1, -1, 0, 0};     0-1-2-3
            case 0: {
                for (Bomb bomb : GameMap.bombs) {
                    if (bomb.getY() - this.y == Sprite.SCALED_SIZE - moveLength) {
                        return false;
                    }
                }
                break;
            }
            case 1: {
                for (Bomb bomb : GameMap.bombs) {
                    if (this.y - bomb.getY() == Sprite.SCALED_SIZE - moveLength) {
                        return false;
                    }
                }
                break;
            }
            case 2: {
                for (Bomb bomb : GameMap.bombs) {
                    if (bomb.getX() - this.x == Sprite.SCALED_SIZE - moveLength) {
                        return false;
                    }
                }
                break;
            }
            default: {
                for (Bomb bomb : GameMap.bombs) {
                    if (this.x - bomb.getX() == Sprite.SCALED_SIZE - moveLength) {
                        return false;
                    }
                }
                break;
            }
        }
        return true;
    }

    public boolean checkValidMove() {
        if (checkCollideBrick()) return false;
        if (checkCollideWall()) return false;
        return checkMoveBomb();
    }

    public void roundAxisMove(int direction) {

        //        changeX = {0, 0, 1, -1};     D-U-R-L
        //        changeY = {1, -1, 0, 0};     0-1-2-3
        Bomber newBomber1 = new Bomber();
        Bomber newBomber2 = new Bomber();
        if (Math.abs(changeX[direction]) == 0) {
            newBomber1.setX(this.x + moveLength);
            newBomber1.setY(this.y + moveLength * changeY[direction]);
            newBomber2.setX(this.x - moveLength);
            newBomber2.setY(this.y + moveLength * changeY[direction]);
        } else {
            newBomber1.setY(this.y + moveLength);
            newBomber1.setX(this.x + moveLength * changeX[direction]);
            newBomber2.setY(this.y - moveLength);
            newBomber2.setX(this.x + moveLength * changeX[direction]);
        }
        if (newBomber1.checkValidMove()) {
            this.x = newBomber1.x;
            this.y = newBomber1.y;
            setImg(directionImages[direction]);
        } else if (newBomber2.checkValidMove()) {
            this.x = newBomber2.x;
            this.y = newBomber2.y;
            setImg(directionImages[direction]);
        }
    }
}
