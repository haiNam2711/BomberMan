package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.algorithm.Bfs;
import uet.oop.bomberman.algorithm.BfsNode;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Oneal extends Enemy {
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spritesSet1 = new Sprite[]{Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3};
        this.spritesSet2 = new Sprite[]{Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3};
    }

    public Oneal(int scaleX, int scaleY) {
        super(scaleX, scaleY);
    }

    @Override
    public void update() {
        if(checkCollideBomber(this,GameMap.bomberMan)){
            GameMap.bomberMan.setExisting(false);
        }
        if (!existing){
            new BombermanGame().setGamePoint(BombermanGame.getGamePoint()+100);
            return;
        }
        //randomMove();
        Bomber bomber = GameMap.bomberMan;
        int bomberX = bomber.getX();
        int bomberY = bomber.getY();
        Bfs bfs = new Bfs();
        BfsNode bfsNode = bfs.loang(bomberX, bomberY, x, y);
        if (bfsNode.getX() != -1) {
            for (int i = 0; i < 4; i++) {
                if (bfsNode.getX() - x != changeX[i]) continue;
                if (bfsNode.getY() - y != changeY[i]) continue;
                switch (i) {
                    //        changeX = {0, 0, 1, -1};     D-U-R-L
                    //        changeY = {1, -1, 0, 0};     0-1-2-3
                    case 0:
                    case 2: {
                        setImg(Sprite.movingSprite(spritesSet1[0], spritesSet1[1], spritesSet1[2], this.getX(), 60).getFxImage());
                        break;
                    }
                    case 1:
                    case 3: {
                        setImg(Sprite.movingSprite(spritesSet2[0], spritesSet2[1], spritesSet2[2], this.getX(), 60).getFxImage());
                        break;
                    }
                }
            }
            x = bfsNode.getX();
            y = bfsNode.getY();
            direction = -1;
        } else {
            this.randomMove();
        }
    }
}
