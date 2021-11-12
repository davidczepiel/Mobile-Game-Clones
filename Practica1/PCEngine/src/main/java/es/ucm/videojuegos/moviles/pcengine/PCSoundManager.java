package es.ucm.videojuegos.moviles.pcengine;

import java.util.ArrayList;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Sound;
import es.ucm.videojuegos.moviles.engine.SoundManager;

public class PCSoundManager implements SoundManager {
    public  PCSoundManager(){

    }
    private static final String PATH = "assets/";
    @Override
    public Sound newSound(String file) {
        PCSound p = new PCSound(PATH + file);
        return p;
    }

    @Override
    public void releaseSounds() {
        //
    }
}
