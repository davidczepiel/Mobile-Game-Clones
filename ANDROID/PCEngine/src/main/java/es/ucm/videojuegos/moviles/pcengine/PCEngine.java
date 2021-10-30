package es.ucm.videojuegos.moviles.pcengine;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Input;

public class PCEngine implements Engine {

    public PCEngine(Application application){
        Window window = new Window(application.getName());
        window.init(400,600);
        this._graphics = new PCGraphics(window);
        this._app = application;
        application.onInit(this);
    }

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
                        this._graphics.clear(0xffffffff);
                        this._app.onDraw(this._graphics);
                    } finally {
                        this._graphics.closeGraphics();
                    }
                } while (this._graphics.contentsRestored());
                this._graphics.show();
            } while (this._graphics.contentsLost());
        }
    }

    @Override
    public Graphics getGraphics() {
        return this._graphics;
    }

    @Override
    public Input getInput() {
        return null;
    }

    Application _app;
    PCGraphics _graphics;
    // _input;


}