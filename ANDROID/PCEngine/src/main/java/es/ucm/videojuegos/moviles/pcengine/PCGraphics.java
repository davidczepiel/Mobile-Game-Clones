package es.ucm.videojuegos.moviles.pcengine;

import java.awt.Color;

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
        this._canvas.setColor(new java.awt.Color(color));
        this._canvas.fillRect(0, 0, this._window.getWidth(), this._window.getHeight());
    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public void scale(float x, float y) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }
    @Override
    /*Prepara el frame para cada plataforma*/
    public void prepareFrame(){

    }
    @Override
    /* Deja de utilizar el motor de render de cada plataforma*/
    public void submitRender(){

    }

    private java.awt.Graphics _canvas;
    private Window _window;
}
