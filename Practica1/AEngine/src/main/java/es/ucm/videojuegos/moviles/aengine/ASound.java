package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import es.ucm.videojuegos.moviles.engine.Sound;
/*Clase que implementa los sonidos para Android.
recoge los sonidos de una pool de sonidos(SoundPool) que ha de
ser inicializada antes de hacer el new del objeto
 */
public class ASound implements Sound {

    /*Crea una sonido de Android dada la pool de sonidos
    * @param file ruta del fichero
    * @param context aplicacion
    * @param pool SoundPool de android ya inicializada*/
    public ASound(String file, Context context, SoundPool pool){
        AssetManager assetManager = context.getAssets();
        this._pool = pool;
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(file);
            this._idSound = pool.load(fileDescriptor,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*Pone el sonido en loop*/
    @Override
    public void loop() {
        this._pool.setLoop(this._idSound,-1);
    }
    /*Ejecuta el sonido*/
    @Override
    public void play() {
        this._pool.play(this._idSound, 1,1,1,0,1);
    }

    /*Para el sonido si esta sonando*/
    @Override
    public void stop() {
        this._pool.stop(this._idSound);
    }

    int _idSound;
    SoundPool _pool;
}
