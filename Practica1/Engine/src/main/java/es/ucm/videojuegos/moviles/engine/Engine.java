package es.ucm.videojuegos.moviles.engine;

/*Interfaz que abstrae los metodos del motor*/
public interface Engine {
    Graphics getGraphics();
    Input getInput();
    SoundManager getSoundManager();

}