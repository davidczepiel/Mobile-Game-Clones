package es.ucm.videojuegos.moviles.engine;
/*Interfaz encargada de abstraer los sonidos en cada plataforma*/
public interface Sound {
    void loop();
    void play();
    void stop();
}
