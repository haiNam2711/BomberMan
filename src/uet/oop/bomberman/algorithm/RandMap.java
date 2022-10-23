package uet.oop.bomberman.algorithm;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Enemy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

public class RandMap {
    private char[][] randomMap = new char[11][29];
    private int enemyNumber;
    private int autoMoveEnemyNumber;
    private int wallNumber;
    private int brickNumber;
    public static List<Coordinate> entities = new ArrayList<>();
    public static List<Coordinate> bricks = new ArrayList<>();
    public static List<Coordinate> enemies = new ArrayList<>();


    public void randMap() throws FileNotFoundException {
        for (int level = 1; level <= 3; ++level) {
            createMap(level);
            StringBuilder res = new StringBuilder();
            res.append('1');
            res.append('3');
            res.append(' ');
            res.append('3');
            res.append('1');
            res.append('\n');
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    if (i == 0 || j == 0 || i == 12 || j == 30) {
                        res.append('#');
                    } else {
                        res.append(randomMap[i - 1][j - 1]);
                    }
                }
                res.append('\n');
            }
            fileExport(level, res.toString());
        }
    }

    public void createMap(int level) {
        initEntitiesNum(level);
        while (!randWall()) {
        }

        while (!randEntities()) {
        }


        randPlayerAndEnemies();
        randomBrick();
        randomItem();
    }

    private void randomItem() {
        int n = bricks.size();
        while (true) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);
            if (randomMap[coorX][coorY] != '*') continue;
            randomMap[coorX][coorY] = 'x';
            break;
        }

        while (true) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);
            if (randomMap[coorX][coorY] != '*') continue;
            randomMap[coorX][coorY] = 'b';
            break;
        }

        while (true) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);
            if (randomMap[coorX][coorY] != '*') continue;
            randomMap[coorX][coorY] = 'f';
            break;
        }

        while (true) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);
            if (randomMap[coorX][coorY] != '*') continue;
            randomMap[coorX][coorY] = 's';
            break;
        }
    }

    private void randomBrick() {
        int brickCounter = 0;
        while (brickCounter < brickNumber) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);
            if (randomMap[coorX][coorY] != ' ') continue;
            boolean flag = true;
            for (Coordinate e : entities) {
                if (Math.abs(e.getX() - coorX) + Math.abs(e.getY() - coorY) < 2) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                continue;
            }
            bricks.add(new Coordinate(coorX,coorY));
            randomMap[coorX][coorY] = '*';
            brickCounter++;
        }
    }

    private void randPlayerAndEnemies() {
        randomMap[entities.get(0).getX()][entities.get(0).getY()] = 'p';
        for (int i = 1; i <= autoMoveEnemyNumber; i++) {
            randomMap[entities.get(i).getX()][entities.get(i).getY()] = '1';
        }
        for (int i = autoMoveEnemyNumber + 1; i < entities.size(); i++) {
            Random random = new Random();
            randomMap[entities.get(i).getX()][entities.get(i).getY()] = (char) (random.nextInt((6 - 2) + 1) + 2 + '0');
        }
    }

    private boolean randEntities() {
        int entityNumber = enemyNumber + autoMoveEnemyNumber + 1;
        entities.clear();
        int entityCounter = 0;
        while (entityCounter < entityNumber) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);

            if (randomMap[coorX][coorY] != ' ') continue;
            entities.add(new Coordinate(coorX, coorY));
            randomMap[coorX][coorY] = 'e';
            entityCounter++;
        }

        for (int i = 0; i < entities.size(); i++) {
            Coordinate coor1 = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Coordinate coor2 = entities.get(j);
                if (Math.abs(coor1.getX() - coor2.getX()) + Math.abs(coor1.getY() - coor2.getY()) < 5) {
                    for (int k = 0; k < entities.size(); k++) {
                        randomMap[entities.get(k).getX()][entities.get(k).getY()] = ' ';
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean randWall() {
        int wallCounter = 0;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 29; j++) {
                randomMap[i][j] = ' ';
            }
        }

        while (wallCounter < wallNumber) {
            Random generator = new Random();
            int coorX = generator.nextInt((10 - 0) + 1);
            int coorY = generator.nextInt((28 - 0) + 1);
            if (randomMap[coorX][coorY] == ' ') {
                wallCounter++;
                randomMap[coorX][coorY] = '#';
            }
        }

        DisjointSet disjointSet = new DisjointSet();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 29; j++) {
                if (randomMap[i][j] != ' ') continue;
                int thisNode = toOneDimension(i, j);
                if (i > 0) {
                    int nearbyNode = toOneDimension(i - 1, j);
                    if (randomMap[i - 1][j] == ' ' && disjointSet.anc(thisNode) != disjointSet.anc(nearbyNode))
                        disjointSet.join(thisNode, nearbyNode);
                }
                if (i < 10) {
                    int nearbyNode = toOneDimension(i + 1, j);
                    if (randomMap[i + 1][j] == ' ' && disjointSet.anc(thisNode) != disjointSet.anc(nearbyNode))
                        disjointSet.join(thisNode, nearbyNode);
                }
                if (j > 0) {
                    int nearbyNode = toOneDimension(i, j - 1);
                    if (randomMap[i][j - 1] == ' ' && disjointSet.anc(thisNode) != disjointSet.anc(nearbyNode))
                        disjointSet.join(thisNode, nearbyNode);
                }
                if (j < 28) {
                    int nearbyNode = toOneDimension(i, j + 1);
                    if (randomMap[i][j + 1] == ' ' && disjointSet.anc(thisNode) != disjointSet.anc(nearbyNode))
                        disjointSet.join(thisNode, nearbyNode);
                }
            }
        }

        int grassComponents = disjointSet.getNodeCount() - wallNumber;
        if (grassComponents > 1) {
            return false;
        }
        return true;
    }

    private int toOneDimension(int i, int j) {
        return (i * 29) + j;
    }

    private void initEntitiesNum(int level) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 29; j++) {
                randomMap[i][j] = ' ';
            }
        }
        Random generator = new Random();
//        Random rand;
//        int randomNum = rand.nextInt((max - min) + 1) + min;
        enemyNumber = generator.nextInt((3 - 1) + 1) + 1;
        if (level == 1) {
            autoMoveEnemyNumber = 1;
        } else {
            autoMoveEnemyNumber = 2;
        }
        wallNumber = generator.nextInt((5 - 0) + 1) + 65;
        brickNumber = generator.nextInt((5 - 0) + 1) + 60;
    }

    public void fileExport(int level, String map) throws FileNotFoundException {
        String text = "res/levels/RandomMapLevel" + Integer.toString(level) + ".txt";
        Formatter f = new Formatter(text);
        f.format(map);
        f.close();
    }
}
