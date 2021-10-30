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
    public void translate() {

    }

    @Override
    public void scale() {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void drawImage(Image image, int x, int y) {

    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public void fillCircle(int cx, int cy, int radius) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeigth() {
        return 0;
    }

    /*Prepara el frame para cada plataforma*/
    public void prepareFrame(){

    }
    /* Deja de utilizar el motor de render de cada plataforma*/
    public void show(){

    }
}
