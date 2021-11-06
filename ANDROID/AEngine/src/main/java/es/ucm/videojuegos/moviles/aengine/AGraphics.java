package es.ucm.videojuegos.moviles.aengine;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Input;

import android.view.SurfaceView;

public class AGraphics implements Graphics  {


    public AGraphics(){

    }

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
    public void scale(double x, double y) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void drawImage(Image image, int x, int y, float alpha) {

    }

    @Override
    public void drawImage(Image image, int x, int y, int width, int height, float alpha) {

    }

    @Override
    public void fillCircle(int cx, int cy, int radius) {

    }

    @Override
    public void drawCircle(int cx, int cy, int radius, int widthStroke) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeigth() {
        return 0;
    }

    @Override
    public int getWidthNativeCanvas() {
        return 0;
    }

    @Override
    public int getHeightNativeCanvas() {
        return 0;
    }

    @Override
    public int getLogicCanvasWidth() {
        return 0;
    }

    @Override
    public int getLogicCanvasHeight() {
        return 0;
    }
}