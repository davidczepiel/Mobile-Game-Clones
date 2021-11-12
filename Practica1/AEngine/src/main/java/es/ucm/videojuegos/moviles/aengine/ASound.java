package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import es.ucm.videojuegos.moviles.engine.Sound;

public class ASound implements Sound,MediaPlayer.OnPreparedListener {
    public ASound(String file, Context context){

        /*this._mediaPlayer = new MediaPlayer();
        try {
            this._mediaPlayer.setDataSource(file);
            this._mediaPlayer.setOnPreparedListener(this);
            //this._mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            this._mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    @Override
    public void loop() {
        this._mediaPlayer.setLooping(true);
    }

    @Override
    public void play() {
        /*if(this._mediaPlayer.isPlaying()){
            stop();
            try {
                this._mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(this._isPrepared)
            this._mediaPlayer.start();*/
    }

    @Override
    public void stop() {
        //this._mediaPlayer.stop();
    }

    @Override
    public void release(){
        //this._mediaPlayer.release();
        //this._mediaPlayer = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //this._isPrepared = true;
    }


    MediaPlayer _mediaPlayer;
    boolean _isPrepared = false;
}
