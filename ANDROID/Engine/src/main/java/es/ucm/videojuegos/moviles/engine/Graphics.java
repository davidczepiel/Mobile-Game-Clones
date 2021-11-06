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
    void scale(double x, double y);
    // Captura de imagen
    void save();
    // Vuelve el formato nativo del sistema de coordenadas y escalado
    void restore();
    // Pinta una imagen en la poscicion x y de la pantalla
    void drawImage(Image image, int x, int y, float alpha);
    //Pinta una imagen en la posicion x y escalandola para el ancho y alto dado
    void drawImage(Image image, int x, int y, int width, int height, float alpha);
    /* Dibuja un círculo relleno del color activo
    ** @param cx posicion en X del centro del circulo
    ** @param cy posicion en Y del centro del circulo
    ** @param radius radio de la circunferencia */
    void fillCircle(int cx, int cy, int radius);
    /* Escribe el texto con la fuente y color activos */
    void drawText(String text, int x, int y);
    // Establece el color a utilizar en las operaciones de
    //dibujado posteriores.
    void setColor(int color);
    // Establece la fuente activa
    void setFont(Font font);

    // tamanio de la ventana
    int getWidth();
    int getHeigth();
    /***********************************************************************************/
    // tamanio del canvas nativo
    int getWidthNativeCanvas();
    int getHeightNativeCanvas();

    //tamanio del canvas logico
    int getLogicCanvasWidth();
    int getLogicCanvasHeight();

}
