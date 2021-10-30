package es.ucm.videojuegos.moviles.engine;

import java.util.List;

public interface Input {
    List<TouchEvent> getTouchEvents();
    void addEvent(TouchEvent e);
}
