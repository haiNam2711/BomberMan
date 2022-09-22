package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.unmovableobject.Brick;
import uet.oop.bomberman.entities.unmovableobject.Grass;
import uet.oop.bomberman.entities.unmovableobject.Portal;
import uet.oop.bomberman.entities.unmovableobject.Wall;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static final String[] mapLv1 = {
            "###############################",
            "#p     ** *  1 * 2 *  * * *   #",
            "# # # #*# # #*#*# # # #*#*#*# #",
            "#  x*     ***  *  1   * 2 * * #",
            "# # # # # #*# # #*#*# # # # #*#",
            "#f         x **  *  *   1     #",
            "# # # # # # # # # #*# #*# # # #",
            "#*  *      *  *      *        #",
            "# # # # #*# # # #*#*# # # # # #",
            "#*    **  *       *           #",
            "# #*# # # # # # #*# # # # # # #",
            "#           *   *  *          #",
            "###############################"
    };

    public static List<Brick> bricks = new ArrayList<>();
    public static List<Portal> portals = new ArrayList<>();
    public static List<Wall> walls = new ArrayList<>();
    public static List<Grass> grasses = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static Bomber bomberMan ;//= new Bomber();

    public static void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        walls.forEach(g -> g.render(gc));
        grasses.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bomberMan.render(gc);
    }
}
