import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound 
{
    private URL url;
    private Clip clip;
/**
 * @param requestedSound The requested type of sound
 */
    public Sound(String requestedSound)
    {
        url = this.getClass().getResource(requestedSound);
        if (url != null)
        {
            try
            {
                // Open an audio input stream.
                // Get a sound clip resource.
                // Open audio clip and load samples from the audio input stream.
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public void play()
    {
        clip.setFramePosition(0);
        clip.start();
    }
}
