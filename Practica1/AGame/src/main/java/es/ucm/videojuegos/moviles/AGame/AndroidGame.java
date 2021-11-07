package es.ucm.videojuegos.moviles.AGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.videojuegos.moviles.aengine.AEngine;
import es.ucm.videojuegos.moviles.logica.OhNoGame;

public class AndroidGame extends AppCompatActivity {


    public AndroidGame(){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._engine = new AEngine(new OhNoGame(), this);
        this._engine.run();
    }

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad va a pasar a primer plano,
     * estando en la cima de la pila de actividades y completamente
     * visible.
     *
     * Es llamado durante la puesta en marcha de la actividad (algo después
     * de onCreate()) y también después de un periodo de pausa (notificado
     * a través de onPause()).
     */
    @Override
    protected void onResume() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        this._engine.resume();
    } // onResume


    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad ha dejado de ser la de
     * primer plano. Es un indicador de que el usuario está, de alguna
     * forma, abandonando la actividad.
     */
    @Override
    protected void onPause() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        this._engine.pause();
    } // onPause


    private AEngine _engine;

}