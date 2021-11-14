package es.ucm.videojuegos.moviles.engine;
/*Inferfaz que abstrae la gestion de los sonidos*/
public interface SoundManager {
    /*Devuelve un sonido dado el archivo. Estas rutas han de ser
    * a partir de assets*/
    Sound newSound(String file);
    /*libera memoria de los sonidos*/
    void releaseSounds();
}
