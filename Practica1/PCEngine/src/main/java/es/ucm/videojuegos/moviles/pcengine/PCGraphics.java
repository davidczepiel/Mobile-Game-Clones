package es.ucm.videojuegos.moviles.pcengine;

import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JLabel;

import es.ucm.videojuegos.moviles.engine.AbstractGraphics;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Image;
/*Clase que implementa metodos de Graphics haciendo uso de JFrame y Graphics*/
public class PCGraphics extends AbstractGraphics {

    //Esta constante se utiliza para solventar el problema de que (0,0) en la ventana del JFrame no coincide
    //con el borde de la pantalla dibujado en el eje de las X's
    private static final int OFFSET_JFRAME_X = 7;
    /**
     * Constructor.
     *
     * @param p Ventana de la aplicacion.
     */
    public PCGraphics(Window p){
        this._window = p;

        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                this._window.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy

        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        else {
             System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        // Obtenemos el Buffer Strategy que se supone que acaba de crearse.
        this._bufferStrategy = _window.getBufferStrategy();
        /*-------------------------------------------------------------------------+
        /|                    Inicializacion de variables                          |
        /+-------------------------------------------------------------------------+
         */
        this._y = 0; this._x = 0;
        this._originalWidth = this._canvasSizeX = this._window.getWidth();
        this._originalHeight = this._canvasSizeY =this._window.getHeight();
        this._scale = 1;
        //Indicamos a AbstarctGraphics que tenga en cuenta los 7 pixeles que no tenemos en PC a la izquierda de la pantalla
        this._offsetX = OFFSET_JFRAME_X;
    }
    /*Crea una imagen dada la ruta de la misma
    * @params name ruta de la imagen
    * @returns PCImage */
    @Override
    public Image newImage(String name) {
        return new PCImage(name);
    }
    /*Crea una fuente dada la ruta de la misma
    * @params filename ruta de la fuente
    * @params size tamanio de la fuente
    * @params isBold si se quiere que este o no en negrita */
    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return new PCFont(filename,size,isBold);
    }
    /*Rellena el fondo de la pantalla con el color establecido*/
    @Override
    public void clear(int color) {
        // Borramos el fondo.
        this._graphics.setColor(new java.awt.Color(color));
        this._graphics.fillRect(0, 0, this._window.getWidth(), this._window.getHeight());
    }
    /*Dibuja una imagen en el x y sin escalado. El pivote de la imagen se
    * encuentra en la parte superior izquierda*/
    @Override
    public void drawImage(Image image, int x, int y, float alpha) {
        PCImage pcImage = (PCImage)image;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D)this._graphics;
        g2d.setComposite(ac);
        this._graphics.drawImage(pcImage.get_image(), x, y, pcImage.getWidth(),pcImage.getHeight(),null);
    }
    /*Dibuja una imagen en el x y con escalado escalado. El pivote de la imagen se
     * encuentra en la parte superior izquierda*/
    @Override
    public void drawImage(Image image, int x, int y, int width, int height, float alpha){
        PCImage pcImage = (PCImage)image;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D)this._graphics;
        g2d.setComposite(ac);
        this._graphics.drawImage(pcImage.get_image(), x, y, width, height, null);
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g2d.setComposite(ac);
    }
    /*Pone el color de dibujado al que se aplicara en todas las operaciones de dibujado tras llamar
    * al metodo*/
    @Override
    public void setColor(int color) {
        this._graphics.setColor(new java.awt.Color(color));
    }
    /*Pone la fuente que se usara y la quese aplicara en todas las operaciones de dibujado de texto tras llamar
     * al metodo*/
    @Override
    public void setFont(Font font) {
        PCFont pcFont = (PCFont)font;
        this._graphics.setFont(pcFont.get_font());
    }
    /*Dibuja un circulo relleno desde el centro del mismo*/
    @Override
    public void fillCircle(int cx, int cy, int radius) {
        Graphics2D g = (Graphics2D)this._graphics;
        this._graphics.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }
    /*Dibuja una circunferencia relleno desde el centro del mismo. El diametro de la circunferencia
    * será diametro + 1*/
    @Override
    public void drawCircle(int cx, int cy, int radius, int widthStroke) {
        Graphics2D g = (Graphics2D)this._graphics;
        g.setStroke(new BasicStroke(widthStroke));
        this._graphics.drawOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }
    /*
    * Dibuja un texto en la posicion x e y dada. El pivote queda situado en la parte superior
    * central del texto.
    * */
    @Override
    public void drawText(String text, int x, int y) {
        //Recogemos que metricas utiliza y calculamos el tamanio asignado en pixeles para el texto
        FontMetrics metrics = this._graphics.getFontMetrics(this._graphics.getFont());
        int sizeX = metrics.stringWidth(text);
        //Se realiza la resta a la hora de dibujar para que el pivote quede situado en el centro del texto
        //en la x
        this._graphics.drawString(text, x - sizeX/2,  y);

    }
    @Override
    public int getWidth() {
        return this._window.getWidth();
    }

    @Override
    public int getHeigth() { return this._window.getHeight(); }
    //+-----------------------------------------------------------------------------------+
    //|                 METODOS AUXILIARES PARA BUFFER                                    |
    //+-----------------------------------------------------------------------------------+
    @Override
    /*Prepara el frame cogiendo el buffer actualmente no utilizado, limpia la pantalla
    * y translada el canvas*/
    public void prepareFrame(){
        this._graphics = this._bufferStrategy.getDrawGraphics();
        clear(0xffffffff);
        translate(OFFSET_JFRAME_X, 0);
        super.prepareFrame();
    }

    @Override
    /* Deja de utilizar el motor de render de cada plataforma*/
    public void show(){
        this._bufferStrategy.show();
    }
    /*Cierra graphics*/
    public void closeGraphics(){
        this._graphics.dispose();
    }
    /*Devuelve si el buffer de dibujado ha sido restaurado recientemente de un estado perdido y se
    * reinicializo al color de fondo pretedeterminado*/
    public boolean contentsRestored(){
        return this._bufferStrategy.contentsRestored();
    }
    /*Devuelve si el buffer de dibujado se perdio desde la llamada prepareFrame*/
    public boolean contentsLost(){
        return this._bufferStrategy.contentsLost();
    }

    //+-----------------------------------------------------------------------------------+
    //|             METODOS CALCULAR POSICION Y ESCALADO                                  |
    //+-----------------------------------------------------------------------------------+
    @Override
    public void translate(int x, int y) {
        Graphics2D g = (Graphics2D)this._graphics;
        g.translate(x,y);
    }

    @Override
    public void scale(double x, double y) {
        Graphics2D g = (Graphics2D)this._graphics;
        g.scale(x,y);
    }

    @Override
    public void save() {
        Graphics2D g = (Graphics2D)this._graphics;
        this._transformationMatrix = g.getTransform();
    }

    @Override
    public void restore() {
        Graphics2D g = (Graphics2D)this._graphics;
        g.setTransform(this._transformationMatrix);
    }

    private java.awt.image.BufferStrategy _bufferStrategy;          //buffer para el swap de los buffers
    private java.awt.Graphics _graphics;                            //Graphics para pintar
    private java.awt.geom.AffineTransform _transformationMatrix;    //Matriz de transformacion guardada
    private Window _window;                                         //Ventana de JFrame
}
