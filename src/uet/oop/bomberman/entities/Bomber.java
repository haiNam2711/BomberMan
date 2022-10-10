package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import jdk.nashorn.internal.ir.SplitReturn;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.soundplayer.SoundPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Bomber extends Entity {

    private int moveLength = 6;
    private boolean bombCollideFlag = false;
    private final Image[] directionImages = {Sprite.player_down.getFxImage(), Sprite.player_up.getFxImage(), Sprite.player_right.getFxImage(), Sprite.player_left.getFxImage()};
    private int direction = -1;
    private int bombsNumLimit = 1;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomber(int x, int y) {
        super(x, y);
    }

    public Bomber() {
    }

    public int getMoveLength() {
        return moveLength;
    }

    public void setBombsNumLimit(int bombsNumLimit) {
        this.bombsNumLimit = bombsNumLimit;
    }

    public int getBombsNumLimit() {
        return bombsNumLimit;
    }

    public void setMoveLength(int moveLength) {
        this.moveLength = moveLength;
    }

    @Override
    public void update() {
        //System.out.println(x);
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

        if (!checkValidMove()) {
            x -= changeX[direction] * moveLength;
            y -= changeY[direction] * moveLength;
            if (!bombCollideFlag) {
                roundAxisMove(direction);
            } else {
                if (x % Sprite.SCALED_SIZE != 0 || y % Sprite.SCALED_SIZE != 0) roundBombMove(direction);
            }
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
        if (isAlreadyExist == false && GameMap.bombs.size() < bombsNumLimit && !newBomb.checkCollideEnemy()) {

            SoundPlayer.playLayBomb();
            GameMap.bombs.add(newBomb);

            //Activate Exploring the Bomb
            TimerTask timerTask1 = new TimerTask() {
                @Override
                public void run() {
                    SoundPlayer.playBumb();
                    newBomb.setExplored(true);
                    newBomb.addFlameFourDirection();
                }
            };
            Timer timer1 = new Timer();
            timer1.schedule(timerTask1, 3000L);

            //Deleting the Broken Bombs
            TimerTask timerTask2 = new TimerTask() {
                @Override
                public void run() {
                    newBomb.existing = false;
                    GameMap.bombs.removeIf(g -> !g.existing);
                }
            };
            Timer timer2 = new Timer();
            timer2.schedule(timerTask2, 3500L);
        }
    }

    public boolean checkMoveBomb() {
        bombCollideFlag = true;
        switch (direction) {
            //        changeX = {0, 0, 1, -1};     D-U-R-L
            //        changeY = {1, -1, 0, 0};     0-1-2-3
            case 0: {
                for (Bomb bomb : GameMap.bombs) {
                    if (bomb.getY() - this.y >= Sprite.SCALED_SIZE - moveLength && bomb.getY() - this.y <= Sprite.SCALED_SIZE) {
                        return false;
                    }
                }
                break;
            }
            case 1: {
                for (Bomb bomb : GameMap.bombs) {
                    if (this.y - bomb.getY() >= Sprite.SCALED_SIZE - moveLength && this.y - bomb.getY() <= Sprite.SCALED_SIZE) {
                        return false;
                    }
                }
                break;
            }
            case 2: {
                for (Bomb bomb : GameMap.bombs) {
                    if (bomb.getX() - this.x >= Sprite.SCALED_SIZE - moveLength && bomb.getX() - this.x <= Sprite.SCALED_SIZE) {
                        return false;
                    }
                }
                break;
            }
            case 3: {
                for (Bomb bomb : GameMap.bombs) {
                    if (this.x - bomb.getX() >= Sprite.SCALED_SIZE - moveLength && this.x - bomb.getX() <= Sprite.SCALED_SIZE) {
                        return false;
                    }
                }
                break;
            }
        }
        bombCollideFlag = false;
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
//        Bomber newBomber1 = new Bomber();
//        Bomber newBomber2 = new Bomber();
//        if (Math.abs(changeX[direction]) == 0) {   //  U D
//            int modX = x % Sprite.SCALED_SIZE;
//            newBomber1.setX(this.x + moveLength);
//            newBomber1.setY(this.y + moveLength * changeY[direction]);
//            newBomber2.setX(this.x - moveLength);
//            newBomber2.setY(this.y + moveLength * changeY[direction]);
//        } else {                                   //  R L
//            newBomber1.setY(this.y + moveLength);
//            newBomber1.setX(this.x + moveLength * changeX[direction]);
//            newBomber2.setY(this.y - moveLength);
//            newBomber2.setX(this.x + moveLength * changeX[direction]);
//        }
//        if (newBomber1.checkValidMove()) {
//            this.x = newBomber1.x;
//            this.y = newBomber1.y;
//            setImg(directionImages[direction]);
//        } else if (newBomber2.checkValidMove()) {
//            this.x = newBomber2.x;
//            this.y = newBomber2.y;
//            setImg(directionImages[direction]);
//        }
        //        changeX = {0, 0, 1, -1};     D-U-R-L
        //        changeY = {1, -1, 0, 0};     0-1-2-3
        Bomber newBomber1 = new Bomber(x, y);
        switch (direction) {
            case 2:
            case 3: {
                int modY = newBomber1.y % Sprite.SCALED_SIZE;
                if (modY <= Sprite.SCALED_SIZE / 3) {
                    newBomber1.y -= modY;
                } else if (modY >= Sprite.SCALED_SIZE * 2 / 3) {
                    newBomber1.y += Sprite.SCALED_SIZE - modY;
                }
                newBomber1.x += changeX[direction] * moveLength;
                break;
            }
            case 1:
            case 0: {
                int modX = newBomber1.x % Sprite.SCALED_SIZE;
                if (modX <= Sprite.SCALED_SIZE / 3) {
                    newBomber1.x -= modX;
                } else if (modX >= Sprite.SCALED_SIZE * 2 / 3) {
                    newBomber1.x += Sprite.SCALED_SIZE - modX;
                }
                newBomber1.y += changeY[direction] * moveLength;
                break;
            }
        }
        if (newBomber1.checkValidMove()) {
            x = newBomber1.getX();
            y = newBomber1.getY();
            setImg(directionImages[direction]);
        }
    }

    public void roundBombMove(int direction) {
        //        changeX = {0, 0, 1, -1};     D-U-R-L
        //        changeY = {1, -1, 0, 0};     0-1-2-3
        Bomber newBomber1 = new Bomber(x, y);
        switch (direction) {
            case 0: {
                int modY = newBomber1.y % Sprite.SCALED_SIZE;
                newBomber1.y += Sprite.SCALED_SIZE - modY;
                break;
            }
            case 1: {
                int modY = newBomber1.y % Sprite.SCALED_SIZE;
                newBomber1.y -= modY;
                break;
            }
            case 2: {
                int modX = newBomber1.x % Sprite.SCALED_SIZE;
                newBomber1.x += Sprite.SCALED_SIZE - modX;
                break;
            }
            case 3: {
                int modX = newBomber1.x % Sprite.SCALED_SIZE;
                newBomber1.x -= modX;
                break;
            }
        }
        if (newBomber1.checkValidMove()) {
            x = newBomber1.getX();
            y = newBomber1.getY();
        }
    }
}
