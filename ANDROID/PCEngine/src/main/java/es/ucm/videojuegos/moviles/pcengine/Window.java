package es.ucm.videojuegos.moviles.pcengine;

import javax.swing.JFrame;

public class Window extends JFrame {

    /**
     * Constructor.
     *
     * @param title Texto que se utilizará como título de la ventana
     *              que se creará.
     */
    public Window(String title) {
        super(title);
    }


    /**
     * Realiza la inicialización del objeto (inicialización en dos pasos).
     * Se configura el tamaño de la ventana, se habilita el cierre de la
     * aplicación al cerrar la ventana, y se carga la fuente que se usará
     * en la ventana.
     */
    public void init(int width, int height) {

        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Vamos a usar renderizado activo. No queremos que Swing llame al
        // método repaint() porque el repintado es continuo en cualquier caso.
        setIgnoreRepaint(true);

        // Hacemos visible la ventana.
        setVisible(true);
    } //  init
}