package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import es.ucm.videojuegos.moviles.engine.Image;

/*Clase que encapsula las imagenes en Android
 * Ofrece funcionalidades basicas como la consulta y
 * el cambio del tamanio de la imagen*/
public class AImage implements Image {

    /*Contructora
    * @param name nombre del fichero que contiene la imagen
    * @context contecto que va a permitir acceder al fichero de la imagen*/
    public AImage(String name, Context context){
        AssetManager assetManager = context.getAssets();
        try(InputStream is = assetManager.open(name)){
            this._image = BitmapFactory.decodeStream(is);
        }
        catch(IOException e){
            android.util.Log.e(null,("Couldn't load image " + name));
        }
    }

    /*Metodo que devuelve el ancho que ocupa la imagen
    * @return ancho de la imagen almacenada*/
    @Override
    public int getWidth() {
        return this._image.getWidth();
    }

    /*Metodo que devuelve el alto que ocupa la imagen
    * @return alto de la imagen almacenada*/
    @Override
    public int getHeight() {

        return this._image.getHeight();
    }

    /*Metodo que devuelve la imagen de Android
    * @return imagen almacenada*/
    public Bitmap getImage(){
        return this._image;
    }

    Bitmap _image;
}