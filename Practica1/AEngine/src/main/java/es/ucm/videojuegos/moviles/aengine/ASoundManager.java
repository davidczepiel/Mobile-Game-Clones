package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Sound;
import es.ucm.videojuegos.moviles.engine.SoundManager;

public class ASoundManager implements SoundManager {

    public ASoundManager(Context context){
        this._context = context;
        this._sounds = new ArrayList<>();
    }
    @Override
    public Sound newSound(String file) {
        ASound a = new ASound(file, _context);
        this._sounds.add(a);
        return a;
    }

    @Override
    public void releaseSounds() {
        for(ASound sound: this._sounds){
            sound.release();
        }
    }


    private List<ASound> _sounds;
    private Context _context;
}
