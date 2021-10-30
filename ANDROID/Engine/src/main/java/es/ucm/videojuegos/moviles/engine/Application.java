package es.ucm.videojuegos.moviles.engine;

public interface Application {
    void onInit(Engine g);
    void onUpdate(double deltaTime);
    void onDraw(Graphics g);
    String getName();
}
