package es.ucm.videojuegos.moviles.engine;

public interface Graphics {
    // Crea una nueva imagen
    Image newImage(String name);
    // Crea una nueva fuente
    Font newFont(String filename, int size, boolean isBold);
    // Pinta toda la pantalla del color dado
    void clear(int color);
    //Translada el canvas
    void translate(int x, int y);
    // Escala el canvas
    void scale(float x, float y);
    // Captura de imagen
    void save();
    // Vuelve el formato nativo del sistema de coordenadas y escalado
    void restore();
}
