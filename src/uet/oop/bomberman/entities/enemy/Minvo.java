package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Minvo extends Enemy {
    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3};
        this.spritesSet2 = new Sprite[]{Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3};
    }


    @Override
    public void update() {
        if (!isExisting()) {
            TimerTask timerTask1 = new TimerTask() {
                @Override
                public void run() {
                    Balloon b1 = new Balloon(x/Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE, Sprite.balloom_dead.getFxImage());
                    GameMap.enemies.add(b1);

                    Balloon b2 = new Balloon(x/Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE, Sprite.balloom_dead.getFxImage());
                    GameMap.enemies.add(b2);
                }
            };
            Timer timer1 = new Timer();
            timer1.schedule(timerTask1,600L);
            new BombermanGame().setGamePoint(BombermanGame.getGamePoint()+100);

        }
        randomMove();
        if (checkCollideBomber(this, GameMap.bomberMan)) {
            GameMap.bomberMan.setExisting(false);
        }
    }

}