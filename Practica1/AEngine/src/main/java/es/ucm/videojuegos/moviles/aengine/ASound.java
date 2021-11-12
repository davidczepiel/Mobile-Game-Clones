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
/*
public class ASound implements Sound {
    public ASound(String file, Context context){

        this._mediaPlayer = new MediaPlayer();
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(file);
            this._mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            this._mediaPlayer.setOnPreparedListener(this);
            this._mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            this._mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void loop() {
        this._mediaPlayer.setLooping(true);
    }

    @Override
    public void play() {
        if(this._mediaPlayer.isPlaying()){
            stop();
            this._mediaPlayer.prepare();
        }
        if(this._isPrepared)
            this._mediaPlayer.start();
    }

    @Override
    public void stop() {
        this._mediaPlayer.stop();
    }

    @Override
    public void release(){
        this._mediaPlayer.release();
        this._mediaPlayer = null;
    }

    MediaPlayer _mediaPlayer;
    boolean _isPrepared = false;
}*/

public class ASound implements Sound {

    public ASound(String file, Context context, SoundPool pool){
        AssetManager assetManager = context.getAssets();
        this._pool = pool;
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(file);
            pool.load(fileDescriptor,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void loop() {
        this._pool.setLoop(this._idSound,-1);
    }

    @Override
    public void play() {
        this._pool.play(this._idSound, 1,1,1,0,1);
    }

    @Override
    public void stop() {
        this._pool.stop(this._idSound);
    }

    int _idSound;
    SoundPool _pool;
}
