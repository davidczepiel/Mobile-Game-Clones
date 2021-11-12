package es.ucm.videojuegos.moviles.logica;

import java.util.List;

import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Sound;
import es.ucm.videojuegos.moviles.engine.TouchEvent;

public class SelectMenu implements Scene {

    private final float FADE_VELOCITY= 2.5f;

    public SelectMenu(SceneManager sceneManager){
        this._sceneManager = sceneManager;
    }
    @Override
    public void onInit(Engine g) {
        this._sceneAlpha = 0;
        this.fade = 1;
        this._engine = g;
        this._closeIcon =  this._sceneManager.getImage(SceneManager.Images.close);
        //Guardamos las fuentes
        this._fontInformation = this._sceneManager.getFont(SceneManager.Fonts.JosefinSans);
        this._fontTitle = this._sceneManager.getFont(SceneManager.Fonts.MollerRegular);
        this._clickSound = this._sceneManager.getSound(SceneManager.Sounds.Click);

        int[] number = {4,5,6,7,8,9};
        this._number = number;
        this._changeScene = false;
    }

    @Override
    public void onUpdate(double deltaTime) {
        this._sceneAlpha = Math.min(this._sceneAlpha + fade * FADE_VELOCITY *(float) deltaTime , 1);
        if(this._sceneAlpha < 0){
            if(this._board == -1)
                this._sceneManager.swapScene(SceneManager.SceneName.MainMenu, 0);
            else
                this._sceneManager.swapScene(SceneManager.SceneName.OhNo, this._number[this._board]);

        }
        if(this._changeScene) return;
        //Recogemos input
        List<TouchEvent> list = this._engine.getInput().getTouchEvents();
        //Procesamos el input
        for (TouchEvent e: list) {
            //Solo comprobamos eventos cuando sean pulsados
            if(e.get_type() == TouchEvent.TouchEventType.touch){
                checkCircles(e.get_x(),e.get_y());
                checkUI(e.get_x(),e.get_y());
            }
        }
    }

    @Override
    public void onDraw() {
        Graphics g = this._engine.getGraphics();
        drawText(g);
        drawCircles(g);
        drawUI(g);
    }

    /* Dibuja los iconos de interfaz situados debajo del tablero
     * @param g Manager de lo relacionado con graficos*/
    private void drawUI(Graphics g){
        //Dibuja iconos Hint/Deshacer/Rendirse
        g.restore();
        g.save();
        g.translate(0,(int)(g.getHeightNativeCanvas() * 0.80));

        float scale = 0.6f;
        float inverseScale = 1/scale;

        g.scale(scale,scale);

        int size = this._closeIcon.getWidth()/2;
        g.drawImage(this._closeIcon,(int)(g.getWidthNativeCanvas() * 0.5 * inverseScale) - size, 0, 0.6f * this._sceneAlpha);

    }
    private void drawText(Graphics g){
        g.restore();
        g.save();
        g.setColor(0xff000000);
        this._fontTitle.setSize(60);
        g.setFont(this._fontTitle);
        g.drawText("Oh No", g.getWidthNativeCanvas()/2, g.getHeightNativeCanvas()/5,  this._sceneAlpha);
        this._fontInformation.setSize(20);
        g.setFont(this._fontInformation);
        g.drawText("Elije el tamaño a jugar", g.getWidthNativeCanvas()/2, g.getHeightNativeCanvas() * 2/7,  this._sceneAlpha);
    }
    private void drawCircles(Graphics g){
        //radio de cada circulo
        int diametro = (int)Math.floor((g.getWidthNativeCanvas() - 7) / 5);
        int radius = (int)Math.floor((diametro * 0.5f) * 0.75f);

        //Volvemos al estado de inicio y guardamos el valor (para la pila)
        g.restore();
        g.save();
        //Situamos l x e y para pintar el tablero
        g.translate(g.getWidthNativeCanvas()/5, g.getHeightNativeCanvas() / 3);

        for(int i = 0; i< this._number.length; i++){
            if(i % 2 == 0)
                g.setColor(0xff33c7ff);
            else
                g.setColor(0xfffa4848);

            int x = diametro * (i % 3) + diametro/2;
            int j = (i/3 >= 1) ? 1: 0;
            int y = diametro * j + diametro/2;

            g.fillCircle(x,y,radius,  this._sceneAlpha);
            g.setColor(0xffffffff);
            this._fontInformation.setSize(30);
            g.drawText("" + _number[i],x,y +(int)radius/4,  this._sceneAlpha);
        }
    }

    /*Comprueba dado un x,y del input si corresponde a alguno de los circulos del tablero
     * @param x Posicion en el eje X donde se ha producido el input
     * @param y Posicion en el eje Y donde se ha producido el input*/
    private void checkCircles(int x, int y){

        //distancia con el texto de arriba
        int offsetTextY = this._engine.getGraphics().getHeightNativeCanvas() / 3;
        int offsetTextX =     this._engine.getGraphics().getWidthNativeCanvas()/5;
        //Diametro logico de los circulos
        //radio de cada circulo
        int diametro = (int)Math.floor((this._engine.getGraphics().getWidthNativeCanvas() - 7) / 5);
        int radius = (int)Math.floor((diametro * 0.5f) * 0.75f);

        for(int i = 0; i< this._number.length; i++){

            int cx = diametro * (i % 3) + diametro/2 + offsetTextX;
            int j = (i/3 >= 1) ? 1: 0;
            int cy = diametro * j + diametro/2 + offsetTextY;

            //calculamos catetos
            int deltaX = Math.abs(cx-x);
            int deltaY = Math.abs(cy-y);

            //calculamos la hipotenusa
            double distance = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
            //comprobamos si se está clicando
            if(distance <= radius) {
                this._clickSound.play();    //lanzamos el sonido
                changeScene(i);
            }
        }
    }

    /*Comprueba dado un x,y del input si corresponde a alguno de los iconos
     * @param x Posicion en el eje X donde se ha producido el input
     * @param y Posicion en el eje Y donde se ha producido el input*/
    private void checkUI(int x, int y) {
        Graphics g = this._engine.getGraphics();

        int size = this._closeIcon.getWidth() / 2;
        int posY = (int) (g.getHeightNativeCanvas() * 0.80);
        int posX = (int) (g.getWidthNativeCanvas() * 0.5) - size;
        if (x > posX && x < posX + this._closeIcon.getWidth() &&
                y > posY && y < posY + this._closeIcon.getHeight()) {
            this._clickSound.play();    //lanzamos el sonido
            changeScene(-1);
        }
    }

    private void changeScene(int size){
        this._board = size;
        this.fade = -1;
        this._changeScene = true;
    }

    private SceneManager _sceneManager;
    private Engine _engine;

    private Font _fontTitle, _fontInformation;
    private Image _closeIcon;
    private Sound _clickSound;                          //sonido de click

    private float _sceneAlpha;
    private int fade;
    private boolean _changeScene;

    private int[] _number;
    private int _board;
}
