package es.ucm.videojuegos.moviles.pcengine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import es.ucm.videojuegos.moviles.engine.Sound;

public class PCSound implements Sound {

    public PCSound(String file) {
        this._audioFile = new File(file);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(this._audioFile);
            this._audio = AudioSystem.getClip();
            this._audio.open(audioStream);

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {

        this._audio.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void play() {
        // Si el audio esta siendo ejecutado se para
        if (this._audio.isRunning()) { this._audio.stop(); }

        // Y se vuelve a lanzar desde la posicion 0
        this._audio.setFramePosition(0);
        this._audio.start();
    }

       @Override
    public void stop() {
        if(this._audio.isRunning())
            this._audio.stop();
    }

    @Override
    public void release() {
        this._audio.close();
    }

    private Clip _audio;
    private File _audioFile;
}
