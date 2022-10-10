package uet.oop.bomberman.soundplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundPlayer {
    public static void playLayBomb() {
        Media sound = new Media(new File("res/soundtrack/lay_bomb.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void playBumb() {
        Media sound = new Media(new File("res/soundtrack/bumb.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}

