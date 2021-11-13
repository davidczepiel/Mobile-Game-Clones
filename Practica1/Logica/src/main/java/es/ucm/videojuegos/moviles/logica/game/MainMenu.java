package es.ucm.videojuegos.moviles.logica.game;

import java.util.List;

import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Sound;
import es.ucm.videojuegos.moviles.engine.TouchEvent;

/*Clase que implementa el juego*/
public class MainMenu implements Scene {

    private final float FADE_VELOCITY= 2.5f;

    public MainMenu(SceneManager app){
        this._sceneManager = app;
    }
    /*Crea los recursos que va a necesitar a lo largo de la aplicacion
    * @param g Motor que va a sostener el juego*/
    @Override
    public void onInit(Engine g) {
        this._sceneAlpha = 0;
        this.fade = 1;
        this._engine = g;
        this._font = this._sceneManager.getFont(SceneManager.Fonts.JosefinSans);
        this._titleFont = this._sceneManager.getFont(SceneManager.Fonts.MollerRegular);
        this._gotaImage = this._sceneManager.getImage(SceneManager.Images.q42);
        this._clickSound = this._sceneManager.getSound(SceneManager.Sounds.Click);
        this._changeScene = false;
    }

     /*Recoge los eventos y los procesa
     * @param deltaTime Tiempo transcurrido desde la ultima iteracion*/
    @Override
    public void onUpdate(double deltaTime) {
        this._sceneAlpha = Math.min(this._sceneAlpha + fade * FADE_VELOCITY *(float) deltaTime , 1);

        if(this._sceneAlpha < 0)
            this._sceneManager.swapScene(SceneManager.SceneName.SelectMenu,0);

        if(this._changeScene) return;
        //Recogemos input
        List<TouchEvent> list = this._engine.getInput().getTouchEvents();
        //Procesamos el input
        for (TouchEvent e: list) {
            //Solo comprobamos eventos cuando sean pulsados
            if(e.get_type() == TouchEvent.TouchEventType.touch){
                checkBotonJugar(e.get_x(),e.get_y());
            }
        }
    }


    /*Dibuja el estado del juego*/
    @Override
    public void onDraw() {
        drawText(this._engine.getGraphics());
        drawImage(this._engine.getGraphics());
    }

    /*Dibuja el texto situado encima del tablero
     * @param g Encargado de graficos*/
    private void drawText(Graphics g){
        g.restore();
        g.save();

        String text = "";
        g.setColor(0xff000000);     //Color a negro

        //Dibujo el titulo del juego
        this._titleFont.setSize(70);
        g.setFont(this._titleFont);
        text = "Oh No";
        g.drawText(text, g.getWidthNativeCanvas()/2 ,g.getHeightNativeCanvas() / 5, this._sceneAlpha);

        //Dibujo el texto de JUGAR
        this._font.setSize(50);
        g.setFont(this._font);
        text = "Jugar";
        g.drawText(text, g.getWidthNativeCanvas()/2 ,g.getHeightNativeCanvas() *4 / 9, this._sceneAlpha);

        //Dibujo el texto de quien es el juego
        this._font.setSize(20);
        g.setColor(0xff808080);
        g.setFont(this._font);
        text =  "Un juego copiado a Q42";
        g.drawText(text, g.getWidthNativeCanvas()/2 ,g.getHeightNativeCanvas() *3/ 5, this._sceneAlpha);
        text = "Creado por Martin Kool";
        g.drawText(text, g.getWidthNativeCanvas()/2 ,20+g.getHeightNativeCanvas() *3/ 5, this._sceneAlpha);

    }

    /* Dibuja los iconos de interfaz situados debajo del tablero
     * @param g Manager de lo relacionado con graficos*/
    private void drawImage(Graphics g){
        //Dibuja iconos Hint/Deshacer/Rendirse
        g.restore();
        g.save();
        g.translate(0,(int)(g.getHeightNativeCanvas() * 0.80));

        float scale = 0.6f;
        float inverseScale = 1/scale;

        g.scale(scale,scale);
        int size = this._gotaImage.getWidth()/2;
        int sizeImageX = (int)(g.getWidthNativeCanvas()/5);
        int sizeImageY = sizeImageX*3/2;
        g.drawImage(this._gotaImage,
                (int)(g.getWidthNativeCanvas() * 0.5 * inverseScale)-sizeImageX/2 ,
                0,sizeImageX,sizeImageY,
                this._sceneAlpha * 0.6f);

    }

    /*Comprueba dado un x,y del input si corresponde a alguno de los iconos
     * @param x Posicion en el eje X donde se ha producido el input
     * @param y Posicion en el eje Y donde se ha producido el input*/
    private void checkBotonJugar(int x, int y){

        Graphics g = this._engine.getGraphics();
        int size = g.getWidthNativeCanvas()/3;

        //Me pongo donde el texto
        int posY = (int)(g.getHeightNativeCanvas() * 4/9); //Parte superior del texto
        int posX = (int)(g.getWidthNativeCanvas() * 0.5); //Centro de pantalla
        int despVert = (int)(this._engine.getGraphics().getHeightNativeCanvas()*0.08);
        posY -=(despVert*3/4);

        if(x > posX - (size/2) && x < posX + (size/2)  &&
                y > posY  && y < posY + despVert){
            this.fade = -1;
            this._sceneAlpha = 1;
            this._changeScene = true;
            this._clickSound.play();    //lanzamos el sonido
        }

    }

    private Engine _engine;     //engine
    private Image _gotaImage;   //imagenes de iconos
    private Font _font;         //fuente
    private Font _titleFont;    //fuente
    private Sound _clickSound;  //sonido de click
    private SceneManager _sceneManager;

    float _sceneAlpha;
    int fade = 1;
    boolean _changeScene;

}
