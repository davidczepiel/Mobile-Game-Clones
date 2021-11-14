package es.ucm.videojuegos.moviles.engine;
/*Clase que realiza los calculos de posicionaminto y escalado del canvas para ambas
* plataformas*/
public class AbstractGraphics implements Graphics{
    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {  }

    @Override
    public void translate(int x, int y) {    }

    @Override
    public void scale(double x, double y) {    }

    @Override
    public void save() {    }

    @Override
    public void restore() {    }

    @Override
    public void drawImage(Image image, int x, int y, float alpha) {    }

    @Override
    public void drawImage(Image image, int x, int y, int width, int height, float alpha) {    }

    @Override
    public void setColor(int color) {    }

    @Override
    public void setFont(Font font) {    }

    @Override
    public void fillCircle(int cx, int cy, int radius, float alpha) {    }

    @Override
    public void drawCircle(int cx, int cy, int radius, int widthStroke, float alpha) {

    }

    @Override
    public void fillRect(int x, int y, int w, int h, int color){}

    @Override
    public void drawText(String text, int x, int y, float alpha) {    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeigth() {
        return 0;
    }

    /*Devuelve el ancho del canvas nativo(400x600)*/
    @Override
    public int getWidthNativeCanvas() {
        return this._originalWidth;
    }

    /*Devuelve el alto del canvas nativo(400x600)*/
    @Override
    public int getHeightNativeCanvas() {
        return this._originalHeight;
    }

    /*Devuelve el ancho del canvas actualmente pintado*/
    @Override
    public int getLogicCanvasWidth() {
        return this._canvasSizeX;
    }
    /*Devuelve el alto del canvas actualmente pintado*/
    @Override
    public int getLogicCanvasHeight() {
        return this._canvasSizeY;
    }

    //+-----------------------------------------------------------------------------------+
    //|                 METODOS PREPARAR EL CANVAS                                        |
    //+-----------------------------------------------------------------------------------+


    /*Prepara el frame para cada plataforma*/
    public void prepareFrame(){
        float auxHeight = this._originalHeight * this.getWidth() / this._originalWidth;
        //Miramos la posicion
        if(auxHeight > this.getHeigth()){
            //poner bandas laterales
            this._canvasSizeX = this._originalWidth * this.getHeigth() / this._originalHeight;
            this._canvasSizeY = this.getHeigth();
            this._x = (this.getWidth() - (int)(_canvasSizeX))/2;
            this._y = 0;
        }
        else{
            //poner bandas arriba y abajo
            this._canvasSizeX = this.getWidth();
            this._canvasSizeY = (int)auxHeight;
            this._x = 0;
            this._y = (this.getHeigth() - (int)(this._canvasSizeY))/2;
        }
        //Miramos la escala
        if(this._canvasSizeY < this._canvasSizeX)
            this._scale = this._canvasSizeY / (float)this._originalHeight;
        else
            this._scale = (this._canvasSizeX ) / (float)this._originalWidth;

        //translate(this._canvasSizeX / this._originalWidth + this._x, this._canvasSizeY / this._originalHeight + this._y);
        translate(this._x, this._y);
        scale(this._scale, this._scale);
        save();
    }
    /* Muestra el contenido pintado en el buffer*/
    public void show(){

    }

    protected int _x,  _y;                            // Posicion desde la cual se empieza a pintar el canvas
    protected double _scale;                          // Factor de escalado
    protected int _canvasSizeX, _canvasSizeY;         //El tamanio de la parte pintable del juego que es relacion a 2/3
    protected int _originalWidth, _originalHeight;    //El tamanio original desde el que se inicia la app
}
