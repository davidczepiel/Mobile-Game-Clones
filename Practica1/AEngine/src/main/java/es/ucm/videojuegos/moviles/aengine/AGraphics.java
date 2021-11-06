package es.ucm.videojuegos.moviles.aengine;

import es.ucm.videojuegos.moviles.engine.AbstractGraphics;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class AGraphics extends AbstractGraphics {


    public AGraphics(Context c){
        //Creamos la surface view y se la pasamos a la actividad
        SurfaceView surfaceView = new SurfaceView(c);
        ((AppCompatActivity)c).setContentView(surfaceView);
        //Inicializacion de variables de clase
        this._canvas = null;
        this._holder = surfaceView.getHolder();
        this._context = c;
        this._paint = new Paint();
        //Recogemos el display para calcular el tamanio del movil
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity)c).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this._height = displayMetrics.heightPixels;
        this._width = displayMetrics.widthPixels;
        this._originalWidth =  this._canvasSizeX = 400;
        this._originalHeight = this._canvasSizeY = 600;
        this._y = 0; this._x = 0;
        this._scale = 1;
    }
    @Override
    public Image newImage(String name) {
        return new AImage(name, this._context);
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return new AFont(filename,this._context,this._paint,isBold);
    }

    @Override
    public void clear(int color) {
        // Borramos el fondo.
        this._canvas.drawColor(color); // ARGB
    }

    @Override
    public void translate(int x, int y) {
        this._canvas.translate(x,y);
    }

    @Override
    public void scale(double x, double y) {
        this._canvas.scale((float)x,(float)y);
    }

    @Override
    public void save() {
        this._canvas.save();
    }

    @Override
    public void restore() {
        this._canvas.restore();
    }

    @Override
    public void drawImage(Image image, int x, int y, float alpha) {
        AImage aux = (AImage) image;
        this._paint.setAlpha((int)(alpha * 255));
        this._canvas.drawBitmap(aux.getImage(),x,y,this._paint);
        this._paint.setAlpha(255);  //Restrablecemos el alpha
    }

    @Override
    public void drawImage(Image image, int x, int y, int width, int height, float alpha) {
        AImage aux = (AImage) image;
        this._paint.setAlpha((int)(alpha * 255));

        Rect source = new Rect(0,0,aux.getWidth(),aux.getHeight());
        Rect dest = new Rect(x,y,x+width,y+height);

        this._canvas.drawBitmap(aux.getImage(),source,dest, this._paint);
        this._paint.setAlpha(255);  //Restrablecemos el alpha
    }

    @Override
    public void fillCircle(int cx, int cy, int radius) {
        this._paint.setStyle(Paint.Style.FILL);
        this._canvas.drawCircle(cx,cy,radius,this._paint);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius, int widthStroke) {
        this._paint.setStyle(Paint.Style.STROKE);
        this._paint.setStrokeWidth(widthStroke);
        this._canvas.drawCircle(cx,cy,radius,this._paint);
    }

    @Override
    public void drawText(String text, int x, int y) {
        this._paint.setTextAlign(Paint.Align.LEFT);
        this._canvas.drawText(text,x,y,this._paint);
    }

    @Override
    public void setColor(int color) {
        this._paint.setColor(color);
    }

    @Override
    public void setFont(Font font) {
        this._paint.setTypeface(((AFont) font).get_font());
    }

    public SurfaceHolder get_holder(){
        return this._holder;
    }
    @Override
    public int getWidth() {
        return this._width;
    }

    @Override
    public int getHeigth() {
        return this._height;
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

    Canvas _canvas;             //Contiene los metodos de dibujado
    SurfaceHolder _holder;      //Encargado del swap del canvas
    Context _context;           //Aplicacion
    Paint _paint;               //Se encarga de la geomeria, estilo y color de los textos y BitMap

    int _height, _width;


}