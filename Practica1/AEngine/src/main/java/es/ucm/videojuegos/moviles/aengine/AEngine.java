package es.ucm.videojuegos.moviles.aengine;

import android.content.Context;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Input;


public class AEngine implements Engine , Runnable {

    public AEngine(Application app, Context context ) {
        this._graphics = new AGraphics(context);
        this._application = app;
        this._input = new AInput(this._graphics);
        this._application.onInit(this);

        this._graphics.getSurfaceView().setOnTouchListener(this._input);
    }

    /*MEtodo que permite obtener el manager de Graphics de android
    * @return manager de lo grafico en android*/
    @Override
    public Graphics getGraphics() {
        return this._graphics;
    }

    /* Metodo que permite obtener el manager de Input de android
    * @return manager del input en android*/
    @Override
    public Input getInput() {
        return this._input;
    }

    @Override
    public void setAplication(Application a) {
        this._application = a;
        a.onInit(this);
    }

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {

        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _renderThread = new Thread(this);
            _renderThread.start();
        } // if (!_running)

    } // resume

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause

    @Override
    public void run() {

        long lastFrameTime =0;
        // Bucle principal.
        while(_running) {

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this._application.onUpdate(elapsedTime);
            // Informe de FPS
            //if (currentTime - informePrevio > 1000000000l) {
            //    long fps = frames * 1000000000l / (currentTime - informePrevio);
            //    System.out.println("" + fps + " fps");
            //    frames = 0;
            //    informePrevio = currentTime;
            //}
            //++frames;


            // Pintamos el frame
            while (!this._graphics.isValid());

            this._graphics.prepareFrame();
            this._application.onDraw();
            this._graphics.unlockCanvas();
                /*
                // Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
                try {
                    Thread.sleep(1);
                }
                catch(Exception e) {}
    			*/

        } // while
    }

    /**
     * Objeto Thread que está ejecutando el método run() en una hebra
     * diferente. Cuando se pide a la vista que se detenga el active
     * rendering, se espera a que la hebra termine.
     */
    private Thread _renderThread;


    /**
     * Bandera que indica si está o no en marcha la hebra de
     * active rendering, y que se utiliza para sincronización.
     * Es importante que el campo sea volatile.
     *
     * Java proporciona un mecanismo integrado para solicitar la
     * detencción de una hebra, aunque por simplicidad nosotros
     * motamos el nuestro propio.
     */
    private volatile boolean _running = false;

    private Application _application;
    private AGraphics _graphics;
    private AInput _input;

}