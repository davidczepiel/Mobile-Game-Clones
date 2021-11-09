package es.ucm.videojuegos.moviles.aengine;

import es.ucm.videojuegos.moviles.engine.AbstractGraphics;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class AGraphics extends AbstractGraphics {


    public AGraphics(Context c){
        //Creamos la surface view y se la pasamos a la actividad
        this._view = new SurfaceView(c);
        ((AppCompatActivity)c).setContentView(this._view);
        //Inicializacion de variables de clase
        this._canvas = null;
        this._holder = this._view.getHolder();
        this._context = c;
        this._paint = new Paint();

        //Recogemos el display para calcular el tamanio del movil
        this._displayMetrics = new DisplayMetrics();

        //tamanio nativo
        this._originalWidth =  this._canvasSizeX = 400;
        this._originalHeight = this._canvasSizeY = 600;

        this._y = 0; this._x = 0;
        this._scale = 1;
    }

    /*Metodo que permite crear una nueva imagen de android
    * @param name nombre del fichero que contiene la imagen que queremos crear
    * @return nueva imagen de android*/
    @Override
    public Image newImage(String name) {
        return new AImage(name, this._context);
    }

    /*Metodo que permite crear una nueva fuente de android
     * @param name nombre del fichero que contiene la fuente que queremos crear
     * @return nueva fuente de android*/
    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return new AFont(filename,this._context,this._paint,isBold, size);
    }

    /*Rellena el fondo de pantalla con el color establecido
    * @param color Color que se quiere usar para rellenar la pantalla*/
    @Override
    public void clear(int color) {
        // Borramos el fondo.
        this._canvas.drawColor(color); // ARGB
    }

    /*Metodo que permite trasladar el eje de coordenadas desde el que se dibujan los elementos en pantalla
    * @param x Posicion en x del nuevo origen de coordenadas
    * @param y Posicion en y del nueco origen de coordenadas*/
    @Override
    public void translate(int x, int y) {
        this._canvas.translate(x,y);
    }

    /*Metodo que permite cambiar la escala del eje de coordenadas desde el que se dibujan los elementos en pantalla
     * @param x Escala en x del nuevo origen de coordenadas
     * @param y Escala en y del nuevo origen de coordenadas*/
    @Override
    public void scale(double x, double y) {
        this._canvas.scale((float)x,(float)y);
    }

    /*Metodo que permite guardar la configuracion actual del canvas(posicion y escala) para poder volver a esta
    * tras realizar alguna modificacion*/
    @Override
    public void save() {
        this._canvas.save();
    }
    /*Metodo que permite volver a una configuracion anterior del canvas(posicion y escala)*/
    @Override
    public void restore() {
        this._canvas.restore();
    }

    /*Metodo que permite dibujar una imagen en panatalla
    * @param image imagen que queremos renderizar en pantalla
    * @param x posicion en x en la que queremos que se renderice la imagen
    * @param y posicion en y en la que queremos que se renderice la imagen
    * @param alpha Coeficiente alpha con el que queremos qu ese renderice la imagen*/
    @Override
    public void drawImage(Image image, int x, int y, float alpha) {
        AImage aux = (AImage) image;
        this._paint.setAlpha((int)(alpha * 255));
        this._canvas.drawBitmap(aux.getImage(),x,y,this._paint);
        this._paint.setAlpha(255);  //Restrablecemos el alpha
    }

    /*Metodo que permite dibujar una imagen en panatalla
     * @param image imagen que queremos renderizar en pantalla
     * @param x posicion en x en la que queremos que se renderice la imagen
     * @param y posicion en y en la que queremos que se renderice la imagen
     * @param width Ancho que se quiere que tenga la imagen
     * @param height Alto que se quiere que tenga la imagen
     * @param alpha Coeficiente alpha con el que queremos qu ese renderice la imagen*/
    @Override
    public void drawImage(Image image, int x, int y, int width, int height, float alpha) {
        AImage aux = (AImage) image;
        this._paint.setAlpha((int)(alpha * 255));

        Rect source = new Rect(0,0,aux.getWidth(),aux.getHeight());
        Rect dest = new Rect(x,y,x+width,y+height);

        this._canvas.drawBitmap(aux.getImage(),source,dest, this._paint);
        this._paint.setAlpha(255);  //Restrablecemos el alpha
    }

    /*Metodo que permite rellenat un circulo en la pantalla
    * @param cx Posicion en x del centro del circulo
    * @param cy Posicion en y del centro del circulo
    * @param radius Radio del circulo a rellenar*/
    @Override
    public void fillCircle(int cx, int cy, int radius) {
        this._paint.setStyle(Paint.Style.FILL);
        this._canvas.drawCircle(cx,cy,radius,this._paint);
    }

    /*Metodo que permite rellenat un circulo en la pantalla
     * @param cx Posicion en x del centro del circulo
     * @param cy Posicion en y del centro del circulo
     * @param radius Radio del circulo que queremos dibujar
     * @param widthStroke ancho que queremos que tenga el borde del circulo*/
    @Override
    public void drawCircle(int cx, int cy, int radius, int widthStroke) {
        this._paint.setStyle(Paint.Style.STROKE);
        this._paint.setStrokeWidth(widthStroke);
        this._canvas.drawCircle(cx,cy,radius,this._paint);
    }

    /*Metodo que permite dibujar un texto en pantalla, este dibujado tiene como origen la esquina superior izquierda del texto
    * @param text Texto que queremos que se muestre en pantalla
    * @param x Posicion en x en la que queremos que se muestre el texto
    * @param y Posicion en y en la que queremos que se muestre el texto*/
    @Override
    public void drawText(String text, int x, int y) {
        int sizeX = this.getTextWidth(text);
        this._canvas.drawText(text,x-sizeX/2,y,this._paint);
    }

    /*Metodo que permite establecer el color con el que deseamos pintar en pantalla
    * @param color Color que queremos que se use para pintar*/
    @Override
    public void setColor(int color) {
        this._paint.setColor(color);
    }

    /*Metodo que permite establecer la fuente con la que deseamos escribir en pantalla
     * @param font Fuente que queremos que se use para escribir*/
    @Override
    public void setFont(Font font) {
        this._paint.setTypeface(((AFont) font).get_font());
    }

    /*Metodo que permite obtener el surfaceholder
    * @return surfaceholder de android*/
    public SurfaceHolder get_holder(){
        return this._holder;
    }

    /*Metodo que permite obtener el ancho de la pantalla
    * @return ancho de la pantalla*/
    @Override
    public int getWidth() {
        return this._view.getWidth();
    }

    /*Metodo que permite obtener el alto de la pantalla
    * @return alto de la pantalla*/
    @Override
    public int getHeigth() {
        return this._view.getHeight();
    }

    /*Metodo que permite obtener la surfaceview
    * @return SurfaceView de android*/
    public SurfaceView getSurfaceView() {
        return _view;
    }

    //+-----------------------------------------------------------------------------------+
    //|                 METODOS AUXILIARES PARA BUFFER                                    |
    //+-----------------------------------------------------------------------------------+
    @Override
    /*Prepara el frame cogiendo el canvas actualmente no utilizado, limpia la pantalla
     * y translada el canvas*/
    public void prepareFrame(){
        this._canvas = this._holder.lockCanvas();
        clear(0xffffffff);
        super.prepareFrame();
    }
    /*Desbloquea el canvas y pinta*/
    public void unlockCanvas(){
        this._holder.unlockCanvasAndPost(this._canvas);
    }
    public  boolean isValid(){
        return this._holder.getSurface().isValid();
    }
    //+-----------------------------------------------------------------------------------+
    //|                           METODOS PRIVADOS                                        |
    //+-----------------------------------------------------------------------------------+
    /*Calcula el tamanio del texto
    * @param str String del que se quiere comprobar el ancho
    * @return ancho del texto*/
    private int getTextWidth(String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            this._paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /*Devuelve el tamanio de la barra de navegacion*/
    private int getNavigationBarHeight(AppCompatActivity app) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            app.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            app.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    Canvas _canvas;                 //Contiene los metodos de dibujado
    SurfaceHolder _holder;          //Encargado del swap del canvas
    Context _context;               //Aplicacion
    Paint _paint;                   //Se encarga de la geomeria, estilo y color de los textos y BitMap
    DisplayMetrics _displayMetrics; //Encargado de saber las metricas de la pantalla
    SurfaceView _view;

}