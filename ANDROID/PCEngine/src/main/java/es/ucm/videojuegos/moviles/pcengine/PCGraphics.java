package es.ucm.videojuegos.moviles.pcengine;

import es.ucm.videojuegos.moviles.engine.AbstractGraphics;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;

public class PCGraphics extends AbstractGraphics {

    /**
     * Constructor.
     *
     * @param p Ventana de la aplicacion.
     */
    public PCGraphics(Window p){
        this._window = p;
        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                this._window.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        else {
             System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        // Obtenemos el Buffer Strategy que se supone que acaba de crearse.
        this._bufferStrategy = _window.getBufferStrategy();
        /*-------------------------------------------------------------------------+
        /|                    Inicializacion de variables                          |
        /+-------------------------------------------------------------------------+
         */
        this._y = 0; this._x = 0;
        this._originalWidth = this._canvasSizeX = this._window.getWidth();
        this._originalHeight = this._canvasSizeY =this._window.getHeight();
        this._scale = 1;
    }
    @Override
    public Image newImage(String name) {
        return new PCImage(name);
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return new PCFont(filename,size,isBold);
    }

    @Override
    public void clear(int color) {
        // Borramos el fondo.
        this._graphics.setColor(new java.awt.Color(color));
        this._graphics.fillRect(0, 0, this._window.getWidth(), this._window.getHeight());
    }
    @Override
    public void drawImage(Image image, int x, int y) {
        PCImage pcImage = (PCImage)image;
        this._graphics.drawImage(pcImage.get_image(),
                (x * this._canvasSizeX / this._originalWidth + this._x),       //posicionX
                (y * this._canvasSizeY / this._originalHeight + this._y),      //posicionY
                (int)(pcImage.getWidth() * this._scale),    //escalaX
                (int)(pcImage.getHeight() * this._scale),   //escalaY
                null);
    }

    @Override
    public void setColor(int color) {
        this._graphics.setColor(new java.awt.Color(color));
    }

    @Override
    public void setFont(Font font) {
        PCFont pcFont = (PCFont)font;
        this._graphics.setFont(pcFont.get_font().deriveFont(pcFont.get_font().getSize() * this._scale));
    }

    @Override
    public void fillCircle(int cx, int cy, int radius) {
        this._graphics.fillOval((cx * this._canvasSizeX / this._originalWidth + this._x),       //posicionX
                                (cy * this._canvasSizeY / this._originalHeight + this._y),      //posicionY
                                (int)(radius * this._scale),(int)(radius * this._scale));
    }

    @Override
    public void drawText(String text, int x, int y) {
        this._graphics.drawString(text,
                                 (x * this._canvasSizeX / this._originalWidth + this._x),       //posicionX
                                 (y * this._canvasSizeY / this._originalHeight + this._y));      //posicionY
    }

    @Override
    public int getWidth() {
        return this._originalWidth;
    }

    @Override
    public int getHeigth() {
        return this._originalHeight;
    }
    //+-----------------------------------------------------------------------------------+
    //|                 METODOS AUXILIARES PARA BUFFER                                    |
    //+-----------------------------------------------------------------------------------+
    @Override
    /*Prepara el frame para cada plataforma*/
    public void prepareFrame(){
        this._graphics = this._bufferStrategy.getDrawGraphics();
        scale();
         translate();
    }
    @Override
    /* Deja de utilizar el motor de render de cada plataforma*/
    public void show(){
        this._bufferStrategy.show();
    }

    public void closeGraphics(){
        this._graphics.dispose();
    }

    public boolean contentsRestored(){
        return this._bufferStrategy.contentsRestored();
    }

    public boolean contentsLost(){
        return this._bufferStrategy.contentsLost();
    }

    //+-----------------------------------------------------------------------------------+
    //|                           METODOS PRIVADOS                                        |
    //+-----------------------------------------------------------------------------------+
    @Override
    public void translate() {
        float auxHeight = this._originalHeight * this._window.getWidth() / this._originalWidth;
        if(auxHeight > this._window.getHeight()){
            //poner bandas laterales
            this._canvasSizeX = this._originalWidth * this._window.getHeight() / this._originalHeight;
            this._canvasSizeY = this._window.getHeight();
            this._x = (this._window.getWidth() - (int)(_canvasSizeX))/2;
            this._y = 0;
        }
        else{
           //poner bandas arriba y abajo
            this._canvasSizeX = this._window.getWidth();
            this._canvasSizeY = (int)auxHeight;
            this._x = 0;
            this._y = (this._window.getHeight() - (int)(this._canvasSizeY))/2;;
        }
    }

    @Override
    public void scale() {
        /*if(this._window.getHeight() < this._window.getWidth())
            this._scale = this._window.getHeight() / (float)this._originalHeight;
        else
            this._scale = this._window.getWidth() / (float)this._originalWidth;*/

        if(this._canvasSizeY < this._canvasSizeX)
            this._scale = this._canvasSizeY / (float)this._originalHeight;
        else
            this._scale = this._canvasSizeX / (float)this._originalWidth;

    }

    @Override
    public void save() {
        //
    }

    @Override
    public void restore() {
        //
    }

    private java.awt.Graphics _graphics;
    private Window _window;
    private java.awt.image.BufferStrategy _bufferStrategy;
    private int _x,  _y;                            // Posicion desde la cual se empieza a pintar el canvas
    private float _scale;                           // Factor de escalado
    private int _canvasSizeX, _canvasSizeY;         //El tamanio de la parte pintable del juego que es relacion a 2/3
    private int _originalWidth, _originalHeight;    //El tamanio original desde el que se inicia la app
}
