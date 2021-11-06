package es.ucm.videojuegos.moviles.pcgame;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.logica.OhNoGame;
import es.ucm.videojuegos.moviles.pcengine.PCEngine;

public class PCGame{
    public static void main(String[] args) {
        Application app = new OhNoGame();
        PCEngine pcEngine = new PCEngine(app);
        pcEngine.run();
    }
}