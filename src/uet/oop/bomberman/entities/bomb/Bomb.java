package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private boolean explored = false;
    private int flameLength = 1;
    private List<Flame> flames = new ArrayList<Flame>();


    public int animatingVariable = 1;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Bomb(int xUnit, int yUnit) {
        super(xUnit,yUnit);
    }
    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }


    //        changeX = {0, 0, 1, -1};     D-U-R-L
    //        changeY = {1, -1, 0, 0};     0-1-2-3
    public void addFlameFourDirection() {
        Image imageHorizontal = Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1,Sprite.explosion_horizontal2,60,60).getFxImage();
        Image imageVertical = Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2,60,60).getFxImage();
        flames.add(new Flame(x/Sprite.SCALED_SIZE,y/Sprite.SCALED_SIZE,Sprite.explosion_vertical1.getFxImage()));
        addFlameDirection(0,imageVertical);
        addFlameDirection(1,imageVertical);
        addFlameDirection(2,imageHorizontal);
        addFlameDirection(3,imageHorizontal);

    }

    public void addFlameDirection(int direction,Image image) {
        for (int i = 1; i <= flameLength; i++) {
            Flame flame  = new Flame(x/Sprite.SCALED_SIZE+changeX[direction]*i,y/Sprite.SCALED_SIZE+changeY[direction]*i,image);
            if (flame.checkCollideWall()) {
                break;
            }
            if (flame.checkCollideBrick()) {
                flames.add(flame);
                break;
            }
            flames.add(flame);
        }
    }

    @Override
    public void update() {
        if (!existing) return;
        if (animatingVariable == 60) {
            animatingVariable = 1;
        } else {
            animatingVariable++;
        }

        if (explored) {
            setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animatingVariable, 60).getFxImage());
        } else {
            setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animatingVariable, 60).getFxImage());
        }
    }

    public List<Flame> getFlames() {
        return flames;
    }
}
