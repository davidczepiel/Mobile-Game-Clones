package es.ucm.videojuegos.moviles.pcgame;


import es.ucm.videojuegos.moviles.logica.OhNoGame;
import es.ucm.videojuegos.moviles.pcengine.PCEngine;

/*Clase que representa el lanzador en PC*/
public class PCGame{
    public static void main(String[] args) {
        PCEngine pcEngine = new PCEngine(new OhNoGame());
        pcEngine.run();
    }
}