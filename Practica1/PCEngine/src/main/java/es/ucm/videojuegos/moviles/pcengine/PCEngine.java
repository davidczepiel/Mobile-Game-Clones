package es.ucm.videojuegos.moviles.pcengine;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Input;

public class PCEngine implements Engine {

    /*Constructora
    * @param application Aplicacion que el motor va a poner en marcha */
    public PCEngine(Application application){
        Window window = new Window(application.getName());
        window.init(400,600);
        this._graphics = new PCGraphics(window);
        this._input = new PCInput(this._graphics);
        this._app = application;
        window.addMouseListener(this._input);
        application.onInit(this);
    }

    /*Metodo sobre el que se sostiene el bucle principal*/
    public void run(){
        //Crear Input
        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;
        // Bucle principal
        while(true) {
            //------------------------Calculo de deltaTime------------------------------
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this._app.onUpdate(elapsedTime);
            // ------------------------Informe de FPS-------------------------------------
            /*if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;*/
            //---------------------------------------------------------------------------
            // Pintamos el frame
            do {
                do {
                    this._graphics.prepareFrame();
                    try {
                        this._app.onDraw();
                    } finally {
                        this._graphics.closeGraphics();
                    }
                } while (this._graphics.contentsRestored());
                this._graphics.show();
            } while (this._graphics.contentsLost());
        }
    }

    /*Metodo que permite obtener el manager de Graphics en PC
    * @return manager de lo grafico en PC*/
    @Override
    public Graphics getGraphics() {
        return this._graphics;
    }

    /*Metodo que permite obtener el manager de Input en PC
    * @return manager del input en PC*/
    @Override
    public Input getInput() {
        return this._input;
    }


    Application _app;           //Aplicacion que va a poner en marcha el engine
    PCGraphics _graphics;       //Manager de lo relacionado con los graficos en PC
    PCInput _input;             //Manager de lo relacionado con el input en PC


}