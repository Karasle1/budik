import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Zvuk implements Runnable {

    private Thread t;
    private String threadName;
    boolean wakeTime = true;

    Zvuk(String name) {

    }

    public void run() {
        InputStream zStream = null;
        try {
            zStream = new FileInputStream("sound/budik.wav");
            AudioStream audioStream = new AudioStream(zStream);
            AudioPlayer.player.start(audioStream);
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }

    }
    public boolean getWaketime(){
        return wakeTime;

    }
    public boolean getState() {
        return t.isAlive();
            }
}
