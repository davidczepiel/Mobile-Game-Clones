package es.ucm.videojuegos.moviles.engine;

import java.util.List;
/*Interfaz que gestiona los eventos de input*/
public interface Input {
    /*Recoge la lista de eventos*/
    List<TouchEvent> getTouchEvents();
    /*Aniade un evento a la lista*/
    void addEvent(TouchEvent e);
}
