package es.ucm.videojuegos.moviles.engine;

public interface Application {
    void onInit();
    void onUpdate(float deltaTime);
    void onDraw(Graphics g);
    void onEvent(TouchEvent e);
}
