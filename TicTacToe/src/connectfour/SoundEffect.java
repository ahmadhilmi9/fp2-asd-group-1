/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221016 - Arjuna Ahmad Dewangga Aljabbar
 * 2 - 5026221126 -  Nur Ghulam Musthafa Al Kautsar
 * 3 - 5026221147 -  Ahmad Hilmi Dwi Setiawan
 */

package connectfour;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.initGame() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can the static variable SoundEffect.volume to SoundEffect.Volume.MUTE
 *    to mute the sound.
 *
 * For Eclipse, place the audio file under "src", which will be copied into "bin".
 */
public enum SoundEffect {
    PLANT("plant.wav"),
    ZOMBIE("groan6.wav"),
    DRAWSOUND("finalfanfare.wav"),
    ZOMBIEWIN("losemusic.wav"),
    PLANTWIN("winmusic.wav"),
    BGSOUND("day_stage.wav");

    /** Nested enumeration for specifying volume */
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    /** Each sound effect has its own clip, loaded with its own sound file. */
    private Clip clip;

    /** Private Constructor to construct each element of the enum with its own sound file. */
    private SoundEffect(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /** Play or Re-play the sound effect from the beginning, by rewinding. */
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
    }

    /** Loop the sound effect continuously. */
    public void loop() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop(); // Stop the player if it is still running
            clip.setFramePosition(0); // Rewind to the beginning
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Play in a continuous loop
        }
    }


    /** Stop the sound effect if it is playing. */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /** Optional static method to pre-load all the sound files. */
    static void initGame() {
        values(); // calls the constructor for all the elements
    }
}
