package es.ucm.videojuegos.moviles.pcengine;

import java.io.IOException;

import es.ucm.videojuegos.moviles.engine.Image;

/*Clase que encapsula las imagenes en PC
 * Ofrece funcionalidades basicas como la consulta del tamanio de la imagen*/
public class PCImage implements Image {

    /*Constructra
    * @param file Nombre del fichero que contiene nuestra imagen*/
    PCImage(String file){
        try {
            this._image = javax.imageio.ImageIO.read(new java.io.File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Metodo que devuelve el ancho que ocupa la imagen
    * @return Ancho de la imagen almacenada*/
    @Override
    public int getWidth() {
        return this._image.getWidth(null);
    }

    /*Metodo que devuelve el alto de la imagen
    * @return Alto de la imagena almacenada*/
    @Override
    public int getHeight() {
        return this._image.getHeight(null);
    }

    /*Metodo que devuelve la imagen que se encuentra almacenada1
    * @return Imagen almacenada*/
    public java.awt.Image get_image() {
        return _image;
    }

    private java.awt.Image _image;
}
