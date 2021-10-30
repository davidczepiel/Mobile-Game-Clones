package es.ucm.videojuegos.moviles.pcengine;

import java.io.IOException;

import es.ucm.videojuegos.moviles.engine.Image;

public class PCImage implements Image {

    PCImage(String file){
        try {
            this._image = javax.imageio.ImageIO.read(new java.io.File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getWidth() {
        return this._image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return this._image.getHeight(null);
    }
    private java.awt.Image _image;
}
