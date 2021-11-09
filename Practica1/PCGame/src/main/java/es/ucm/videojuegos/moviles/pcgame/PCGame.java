package es.ucm.videojuegos.moviles.pcgame;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.logica.MainMenu;
import es.ucm.videojuegos.moviles.logica.OhNoGame;
import es.ucm.videojuegos.moviles.pcengine.PCEngine;

/*Clase que representa el lanzador en PC*/
public class PCGame{
    public static void main(String[] args) {
        Application app = new OhNoGame();
        app = new MainMenu();
        PCEngine pcEngine = new PCEngine(app);
        pcEngine.run();
    }
}