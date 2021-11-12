package es.ucm.videojuegos.moviles.engine;

public interface SoundManager {
    Sound newSound(String file);
    void releaseSounds();
}
