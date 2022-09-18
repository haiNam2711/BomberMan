package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {

    private final int moveLength = Sprite.SCALED_SIZE/6;
    private final int[] changeX = {0,0,1,-1};
    private final int[] changeY = {1,-1,0,0};

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }
    public void move(int direction) {
        for (int i=1;i<=moveLength;i++) {
            x+=changeX[direction];
            y+=changeY[direction];
        }
        switch (direction) {
            case 0: {
                setImg(Sprite.movingSprite(Sprite.player_down,Sprite.player_down_1,Sprite.player_down_2,this.getY(),60).getFxImage());
                break;
            }
            case 1: {
                setImg(Sprite.movingSprite(Sprite.player_up,Sprite.player_up_1,Sprite.player_up_2,this.getY(),60).getFxImage());
                break;
            }
            case 2: {
                setImg(Sprite.movingSprite(Sprite.player_right,Sprite.player_right_1,Sprite.player_right_2,this.getX(),60).getFxImage());
                break;
            }
            case 3: {
                setImg(Sprite.movingSprite(Sprite.player_left,Sprite.player_left_1,Sprite.player_left_2,this.getX(),60).getFxImage());
                break;
            }
        }
    }
}
