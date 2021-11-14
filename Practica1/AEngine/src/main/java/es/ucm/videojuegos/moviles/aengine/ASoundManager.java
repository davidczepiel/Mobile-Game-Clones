package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Sound;
import es.ucm.videojuegos.moviles.engine.SoundManager;

/*Clase que implementa la gestion de Adnroid para movil
* Consta de una pool de sonidos para la creacion de los mismos.*/
public class ASoundManager implements SoundManager {
    /*Constructora que inicializa la pool*/
    public ASoundManager(Context context){
        this._context = context;
        this._pool = new SoundPool(
                10,
                AudioManager.STREAM_MUSIC,
                0);
    }
    /*Crea y devuelve un sonido dado el archivo*/
    @Override
    public Sound newSound(String file) {
        return new ASound(file, _context, this._pool);
    }
    /*Elimina la pool de sonidos*/
    @Override
    public void releaseSounds() {
        this._pool.release();
    }

    private Context _context;
    private SoundPool _pool;
}
