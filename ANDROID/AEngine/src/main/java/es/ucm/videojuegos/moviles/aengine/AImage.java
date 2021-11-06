package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import es.ucm.videojuegos.moviles.engine.Image;

public class AImage implements Image {

    public AImage(String name, Context context){
        AssetManager assetManager = context.getAssets();
        try(InputStream is = assetManager.open(name)){
            this._image = BitmapFactory.decodeStream(is);
        }
        catch(IOException e){
            android.util.Log.e(null,("Couldn't load image " + name));
        }
    }
    @Override
    public int getWidth() {
        return this._image.getWidth();
    }

    @Override
    public int getHeight() {

        return this._image.getHeight();
    }

    public Bitmap getImage(){ return this._image;}

    Bitmap _image;
}