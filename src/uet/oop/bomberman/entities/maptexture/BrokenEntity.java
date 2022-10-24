package uet.oop.bomberman.entities.maptexture;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class BrokenEntity extends MapTexture {

    public BrokenEntity(int x, int y, int type) {
        super();
        this.x = x;
        this.y = y;
        final BrokenEntity brokenEntity = this;
        switch (type) {
            case 0: {
                this.setImg(Sprite.brick_exploded.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        setImg(Sprite.brick_exploded1.getFxImage());
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);

                TimerTask timerTask2 = new TimerTask() {
                    @Override
                    public void run() {
                        setImg(Sprite.brick_exploded2.getFxImage());
                    }
                };
                Timer timer2 = new Timer();
                timer2.schedule(timerTask2, 800L);
                TimerTask timerTask3 = new TimerTask() {
                    @Override
                    public void run() {
                        setExisting(false);
                    }
                };
                Timer timer3 = new Timer();
                timer3.schedule(timerTask3, 1000L);
                break;
            }
            case 1: {
                this.setImg(Sprite.oneal_dead.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        setExisting(false);
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);
                break;
            }
            case 2: {
                this.setImg(Sprite.balloom_dead.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        setExisting(false);
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);
                break;
            }
            case 3: {
                this.setImg(Sprite.doll_dead.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        setExisting(false);
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);
                break;
            }
            case 4: {
                this.setImg(Sprite.minvo_dead.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        setExisting(false);
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);
                break;
            }
            case 5: {
                this.setImg(Sprite.kondoria_dead.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {

                        setExisting(false);
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);
                break;
            }
            case 6: {
                this.setImg(Sprite.ovapi_dead.getFxImage());
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        setExisting(false);
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(timerTask1, 400L);
                break;
            }


        }

    }
}
