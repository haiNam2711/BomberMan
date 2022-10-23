package uet.oop.bomberman.algorithm;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.Queue;

public class Bfs {
    private int[][] mark = new int[GameMap.WIDTH * Sprite.SCALED_SIZE][GameMap.HEIGHT * Sprite.SCALED_SIZE];
    private static int[] changeX = {0, 0, 1, -1};
    private static int[] changeY = {1, -1, 0, 0};


    public BfsNode loang(int startX, int startY, int endX, int endY) {
        if (BombermanGame.isInRandomMapMode)
            if (Math.abs(endX - startX) + Math.abs(endY - startY) > 32 * 8) {
                return new BfsNode(-1, -1);
            }
        try {
            for (int i = 0; i < GameMap.WIDTH * Sprite.SCALED_SIZE; i++) {
                for (int j = 0; j < GameMap.HEIGHT * Sprite.SCALED_SIZE; j++) {
                    mark[i][j] = 0;
                }
            }
            BfsNode res = new BfsNode();
            BfsQueue bfsQueue = new BfsQueue(GameMap.WIDTH * GameMap.HEIGHT * Sprite.SCALED_SIZE * Sprite.SCALED_SIZE + 99);
            bfsQueue.enqueue(new BfsNode(startX, startY));
            mark[startX][startY] = 1;
            while (!bfsQueue.isEmpty() && mark[endX][endY] == 0) {
                BfsNode bn = bfsQueue.dequeue();
                if (BombermanGame.isInRandomMapMode)
                    if (Math.abs(bn.getX() - startX) + Math.abs(bn.getY() - startY) > 32 * 6) {
                        continue;
                    }
                //        changeX = {0, 0, 1, -1};     D-U-R-L
                //        changeY = {1, -1, 0, 0};     0-1-2-3
                for (int dir = 0; dir < 4; dir++) {
                    Oneal oneal = new Oneal(bn.getX() + changeX[dir], bn.getY() + changeY[dir]);

                    if (oneal.checkValidMove() && oneal.getX() == endX && oneal.getY() == endY) {
                        res.setX(bn.getX());
                        res.setY(bn.getY());
                        return res;
                    }
                    if (!oneal.checkValidMove() || (mark[oneal.getX()][oneal.getY()] != 0)) continue;
                    bfsQueue.enqueue(new BfsNode(oneal.getX(), oneal.getY()));
                    mark[oneal.getX()][oneal.getY()] = 1;
                }
            }
            return new BfsNode(-1, -1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new BfsNode(-1, -1);
    }
}
