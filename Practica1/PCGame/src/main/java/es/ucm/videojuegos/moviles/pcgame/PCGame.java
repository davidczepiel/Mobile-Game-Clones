package es.ucm.videojuegos.moviles.pcgame;


import es.ucm.videojuegos.moviles.logica.ApplicationManager;
import es.ucm.videojuegos.moviles.pcengine.PCEngine;

/*Clase que representa el lanzador en PC*/
public class PCGame{
    public static void main(String[] args) {
        ApplicationManager app = new ApplicationManager();
        PCEngine pcEngine = new PCEngine(app.getAplication(ApplicationManager.Scene.MainMenu, 7));
        pcEngine.run();
    }
}