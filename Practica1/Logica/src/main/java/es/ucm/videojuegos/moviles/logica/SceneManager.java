package es.ucm.videojuegos.moviles.logica;

import java.util.ArrayList;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Sound;

/*Clase utilizada para guardar y crear las instancias de las aplicaciones con el fin de
 *reutilizar las aplicaciones de los menus y no crear nuevas instancias.
 */
public class SceneManager {

    public enum SceneName{ MainMenu, SelectMenu, OhNo}
    public enum Images{close,history,eye,lock,q42}
    public enum Fonts{JosefinSans, MollerRegular}
    public enum Sounds{Click}

    public SceneManager(Engine engine){
        _scenes = new ArrayList<>();
        _scenes.add(new MainMenu(this));
        _scenes.add(new SelectMenu(this));
        this.engine = engine;
        this._currentScene = _scenes.get(SceneName.MainMenu.ordinal());
    }
    /*Devuelve la aplicacion a la que se quiere cambiar.
    * @param scene posicion en donde se encuentra almacenada la escena
    * @param sizeBoard tamanio del tablero a instanciar en caso de querer una Aplicacion con OhNo.
    * Si el valor es inferior a 4 no se cambia de escena
    * En caso de no querer este tipo de aplicacion el valor puesto no afecta en nada*/
    public void swapScene(SceneName scene, int sizeBoard){
        if(scene == SceneName.OhNo){
            if(sizeBoard > 3){
                this._currentScene = new Game(this, sizeBoard);
                this._currentScene.onInit(this.engine);
            }
            return;
        }
        this._currentScene = this._scenes.get(scene.ordinal());
        this._currentScene.onInit(this.engine);

    }

    /*Devuelve la escena que está actualmente usandose*/
    public Scene getCurrentScene(){
        return  this._currentScene;
    }
    /*Crea y guarda todos los recursos del juego*/
    public void chargeResources(){
        if(_images == null){
            this._images = new ArrayList<>();
            this._fonts = new ArrayList<>();
            this._sounds = new ArrayList<>();
            //Guardamos las imagenes
            this._images.add(engine.getGraphics().newImage("sprites/close.png"));
            this._images.add(engine.getGraphics().newImage("sprites/history.png"));
            this._images.add(engine.getGraphics().newImage("sprites/eye.png"));
            this._images.add(engine.getGraphics().newImage("sprites/lock.png"));
            this._images.add(engine.getGraphics().newImage("sprites/q42.png"));
            //Guardamos las fuentes
            this._fonts.add(engine.getGraphics().newFont("fonts/JosefinSans-Bold.ttf", 70, true));
            this._fonts.add(engine.getGraphics().newFont("fonts/Molle-Regular.ttf", 50, false));

            this._sounds.add(engine.getSoundManager().newSound("sounds/click.wav"));
        }
    }

    public Image getImage(Images id){
        return this._images.get(id.ordinal());
    }
    public Font getFont(Fonts id){
        return this._fonts.get(id.ordinal());
    }
    public Sound getSound(Sounds id){
        return this._sounds.get(id.ordinal());
    }

    private Scene _currentScene;
    private List<Scene> _scenes;    //array donde se guardan las escenas
    private List<Image> _images;    //array donde se guardan las imagenes
    private List<Font> _fonts;      //array donde se guardan las fuentes
    private List<Sound> _sounds;      //array donde se guardan las fuentes

    private Engine engine;
}
