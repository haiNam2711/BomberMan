package uet.oop.bomberman.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MapReader {
    public static void reader(int lvl) throws IOException {
        File file = new File("res/levels/Level" + lvl + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String[] res = new String[100];
        int dem = 0;
        while ((st = br.readLine()) != null) {
            if (dem == 0) {
                dem++;
                String st2 = new String();
                for (int i = 0; i < st.length(); i++) {
                    char c = st.charAt(i);
                    if (c != ' ') {
                        st2 += c;
                        //System.out.println(st2);
                    } else {
                        GameMap.HEIGHT = Integer.valueOf(st2);
                        st2 = "";
                    }
                }
                GameMap.WIDTH = Integer.valueOf(st2);
            } else {
                res[dem - 1] = st;
                dem++;
            }
        }
        GameMap.map[lvl] = res;
    }
}
