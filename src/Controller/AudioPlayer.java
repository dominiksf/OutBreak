package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioPlayer {
    /**
     * Music file paths
     */
    private static final String BACKGROUND_MUSIC_FILE = "resources/exciting_background_music.wav";
    private static final String COLLISION_SOUND_FILE = "resources/collision_sound.wav";
    private static final String INFECTION_SOUND_FILE = "resources/horn_sound.wav";
    private static final String GAME_WON_SOUND_FILE = "resources/game_won_sound.wav";
    private static final String GAME_OVER_SOUND_FILE = "resources/game_over_sound.wav";

    /**
     * Attributes
     */
    private boolean isPlayingBackgroundMusic = false;

    private Clip backgroundClip;
    private Clip collisionClip;
    private Clip infectionClip;
    private Clip gameWonClip;
    private Clip gameOverClip;

    /**
     * Constructor
     */
    public AudioPlayer() {
        try {
            AudioInputStream backgroundStream = AudioSystem.getAudioInputStream(new File(BACKGROUND_MUSIC_FILE).getAbsoluteFile());
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(backgroundStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);

            AudioInputStream collisionStream = AudioSystem.getAudioInputStream(new File(COLLISION_SOUND_FILE).getAbsoluteFile());
            collisionClip = AudioSystem.getClip();
            collisionClip.open(collisionStream);

            AudioInputStream infectionStream = AudioSystem.getAudioInputStream(new File(INFECTION_SOUND_FILE).getAbsoluteFile());
            infectionClip = AudioSystem.getClip();
            infectionClip.open(infectionStream);

            AudioInputStream gameWonStream = AudioSystem.getAudioInputStream(new File(GAME_WON_SOUND_FILE).getAbsoluteFile());
            gameWonClip = AudioSystem.getClip();
            gameWonClip.open(gameWonStream);

            AudioInputStream gameOverStream = AudioSystem.getAudioInputStream(new File(GAME_OVER_SOUND_FILE).getAbsoluteFile());
            gameOverClip = AudioSystem.getClip();
            gameOverClip.open(gameOverStream);

        }
        catch (Exception e) {
            System.err.println("Audiplayer stopped working: " + e.getMessage());
        }
    }

    /**
     * Ends the AudioPlayer
     * Should be called before the program terminates
     */
    public void endAudioPlayer() {
        backgroundClip.close();
        collisionClip.close();
        infectionClip.close();
        gameWonClip.close();
        gameOverClip.close();
    }

    /**
     * Background music
     */
    public void playBackgroundMusic() {
        if (!isPlayingBackgroundMusic) backgroundClip.start();
        isPlayingBackgroundMusic = true;
    }

    public void stopBackgroundMusic() {
        backgroundClip.stop();
        isPlayingBackgroundMusic = false;
    }

    public boolean isPlayingBackgroundMusic() {
        return isPlayingBackgroundMusic;
    }

    /**
     * Sound effects
     */
    public void makeCollisionSound() {
        collisionClip.start();
        AudioInputStream collisionStream = null;
        try {
            collisionStream = AudioSystem.getAudioInputStream(new File(COLLISION_SOUND_FILE).getAbsoluteFile());
            collisionClip = AudioSystem.getClip();
            collisionClip.open(collisionStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeInfectionSound() {
        infectionClip.start();
    }

    public void makeGameWonSound() {
        gameWonClip.start();
    }

    public void makeGameOverSound() {
        gameOverClip.start();
    }

}