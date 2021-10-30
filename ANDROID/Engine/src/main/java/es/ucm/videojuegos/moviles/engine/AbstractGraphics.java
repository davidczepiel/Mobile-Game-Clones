package es.ucm.videojuegos.moviles.engine;

public class AbstractGraphics implements Graphics{
    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {

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
    /*Prepara el frame para cada plataforma*/
    public void prepareFrame(){

    }
    /* Deja de utilizar el motor de render de cada plataforma*/
    public void submitRender(){

    }
}
