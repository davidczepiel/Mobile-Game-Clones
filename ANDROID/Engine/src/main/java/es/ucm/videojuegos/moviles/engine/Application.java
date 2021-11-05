package es.ucm.videojuegos.moviles.engine;

public interface Application {
    /*Realiza la inicialización del objeto (inicialización en dos pasos).*/
    void onInit(Engine g);
    /*Realiza actualizaciones de la aplicación y comprueba estados*/
    void onUpdate(double deltaTime);
    /*Realiza el dibujado de la aplicacion*/
    void onDraw();
    /*Devuelve el nombre de la aplicacion*/
    String getName();
}
