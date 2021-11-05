package es.ucm.videojuegos.moviles.logica;

import java.util.List;
import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.TouchEvent;

/*Clase que implementa el juego*/
public class OhNoGame implements Application {
    /*Crea los recursos que va a necesitar a lo largo de la aplicacion*/
    @Override
    public void onInit(Engine g) {
        //Atributos de la clase
        this._boardSize = 5;
        this._engine = g;
        this._isLocked = false;
        //Creamos el tablero
        this._tablero = new Tablero(this._boardSize);
        //Guardamos las imagenes
        this._closeImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/close.png");
        this._rewindImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/history.png");
        this._helpImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/eye.png");
        this._blockImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/lock.png");
        //Guardamos las fuentes
        this._font = g.getGraphics().newFont("PCGame/src/main/assets/fonts/JosefinSans-Bold.ttf", 70, false);

        //Creamos la cola de casillas pre-modificadas
        this._restoreManager = new RestoreManager();

        //DebugClicks
        this.debugClick = true;
     }

     /*Recoge los eventos y los procesa*/
    @Override
    public void onUpdate(double deltaTime) {
        //Recogemos input
        List<TouchEvent> list = this._engine.getInput().getTouchEvents();
        //Procesamos el input
        for (TouchEvent e: list) {
            //Solo comprobamos eventos cuando sean pulsados
            if(e.get_type() == TouchEvent.TouchEventType.pulsar){
                checkCircles(e.get_x(),e.get_y());
                checkUI(e.get_x(),e.get_y());
            }
            //Debug clicks
            this.debugClickX = e.get_x();
            this.debugClickY = e.get_y();
        }

        //Comprobamos si se ha terminado el juego
        checkEndedGame();
    }

    /*Dibuja el estado del juego*/
    @Override
    public void onDraw() {
        drawText(this._engine.getGraphics());
        drawBoard(this._engine.getGraphics());
        drawUI(this._engine.getGraphics());

        if(debugClick) {
            this._engine.getGraphics().restore();
            this._engine.getGraphics().setColor(0xff00ff00);
            this._engine.getGraphics().fillCircle(this.debugClickX, this.debugClickY, 5);
        }
    }

    /*Devuelve el nombre de la aplicacion*/
    @Override
    public String getName() {
        return "OhNoGame";
    }

    /*Dibuja el tablero del juego*/
    private void drawBoard(Graphics g) {
        /*+-----------------------------------------------------------------+--------
        * |                             _________                           |
        * |                          *             *                        |
        * |                        *                 *                      |
        * |                      **                   **                    |  
        * |<--offsetBetween-->  **         .<-radius-> **<--offsetBetween-->|
        * |                      **                   **                    |
        * |                        *                 *                      |
        * |                          *             *                        |
        * |                             _________                           |
         */

        //radio de cada circulo
        int diametro = (int)Math.floor((g.getWidthNativeCanvas()) / this._boardSize);
        int radius = (int)Math.ceil((diametro * 0.5f) * 0.75f);
        //Dalculamos el offset entre cada circulo
        int offseBetween = (int)Math.floor((diametro * 0.5f) * 0.25f);

        //Situamos l x e y para pintar el tablero
        g.restore();
        g.setColor(0xffffffff);
        g.translate(0,g.getHeightNativeCanvas() / 4);

        //Recorremos cada casilla del tablero
        for(int i = 0; i< this._boardSize; i++){
            for(int j = 0; j< this._boardSize; j++){
                Casilla casilla = this._tablero.getTablero()[i][j];
                //Asignamos el color actual dependiendo del tipo
                switch (casilla.getTipoActual()){
                    case AZUL:  g.setColor(0xff33c7ff);     break;
                    case ROJO:  g.setColor(0xfffa4848);     break;
                    case VACIO: g.setColor(0xffdfdfdf);     break;
                }
                int x = diametro * j + radius + offseBetween/2;
                int y = diametro * i + radius + offseBetween/2;
                //pintamos
                g.fillCircle(x, y, (int)radius);
                if(!casilla.esModificable()){
                    //Si es azul no modificable ponemos el numero
                    if(casilla.getTipoActual() == Casilla.Tipo.AZUL){
                        g.setColor(0xffffffff);         //Asignamos el color blanco
                        this._font.setSize(radius);     //Cambiamos el tamanio de letra
                        g.setFont(this._font);          //Asignamos la fuente
                        String text = "" + casilla.getNumero();
                        g.drawText(text, x , y +(int)radius/4);
                    }
                    //Si la casilla es roja y esta activado el lock pintamos el candado
                    else if(this._isLocked && casilla.getTipoActual() == Casilla.Tipo.ROJO){
                        int lockX = x - (int)(radius * 0.5f);
                        int lockY = y - (int)(radius * 0.5f);
                        g.drawImage(this._blockImage, lockX, lockY, (int)(radius * 1.0f), (int)(radius * 1.0f));
                    }
                }
            }
        }
    }

    /*Dibuja el texto situado encima del tablero*/
    private void drawText(Graphics g){
        g.restore();
        String text = "";
        g.setColor(0xff000000);     //Color a negro
        if(!this._isAnyHelp){
            this._font.setSize(70);     //Asignamos tamanio de fuente
            g.setFont(this._font);      //Asignamos la fuente;
            text = this._boardSize + "X" + this._boardSize;
            g.drawText(text, g.getWidthNativeCanvas()/2 ,g.getHeightNativeCanvas() / 5);
        }else{
            this._font.setSize(20);     //Asignamos tamanio de fuente
            g.setFont(this._font);      //Asignamos la fuente;

            String[] paragraph = this._helpString.split("-");   //Separamos las cadenas
            for (int i = 0; i < paragraph.length ;++i) {
                g.drawText(paragraph[i], g.getWidthNativeCanvas() /2 ,g.getHeightNativeCanvas() / 10 + 20*i);
            }
        }
    }

    /* Dibuja los iconos de interfaz situados debajo del tablero*/
    private void drawUI(Graphics g){
        //Dibuja iconos Pista/Deshacer/Rendirse
        g.restore();
        g.translate(0,(int)(g.getHeightNativeCanvas() * 0.90));

        float scale = 0.6f;
        float inverseScale = 1/scale;

        g.scale(scale,scale);

        int size = this._closeImage.getWidth()/2;
        g.drawImage(this._closeImage,(int)(g.getWidthNativeCanvas() * 0.33 * inverseScale) - size, 0);
        g.drawImage(this._rewindImage,(int)(g.getWidthNativeCanvas() * 0.50 * inverseScale) - size, 0);
        g.drawImage(this._helpImage,(int)(g.getWidthNativeCanvas() * 0.66 * inverseScale) - size,0 );
    }
    /*Comprueba si el numero de casillas del tablero es 0
     *En caso de ser 0 comprueba si es correcto o no y notifica al jugador.*/
    private void checkEndedGame(){
        if(this._tablero.getNumVacias() == 0){

            if(this._tablero.esCorrecto()){ //TODO: poner variables para hacer los textos
                System.out.println("Has ganado");
            }
            else
                System.out.println("Ereh un pringao y lo has hecho mal");
        }
    }

    /*Comprueba dado un x,y del input si corresponde a alguno de los circulos del tablero*/
    private void checkCircles(int x, int y){
        //distancia con el texto de arriba
        int offsetText = _engine.getGraphics().getHeightNativeCanvas() / 4;
        //Diametro logico de los circulos
        int diametro = (int)Math.floor((_engine.getGraphics().getWidthNativeCanvas()) / this._boardSize);
        //radio de cada circulo
        int radius = (int)Math.ceil((diametro * 0.5f) * 0.75f);
        //calculamos el offset entre cada circulo
        int offseBetween = (int)Math.floor((diametro * 0.5f) * 0.25f);

        //Recorremos cada casilla del tablero
        for(int i = 0; i< this._boardSize; i++){
            for(int j = 0; j< this._boardSize; j++){
                Casilla casilla = this._tablero.getTablero()[i][j];
                int cx = diametro * j + radius + offseBetween/2;
                int cy = offsetText + diametro * i + radius + offseBetween/2;
                //calculamos catetos
                int deltaX = Math.abs(cx-x);
                int deltaY = Math.abs(cy-y);
                //calculamos la hipotenusa
                double distance = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
                //comprobamos si se está clicando
                if(distance <= radius){
                    if(!casilla.esModificable()){
                        if(casilla.getTipoActual() == Casilla.Tipo.ROJO){  //Ponemos un lock en la casilla actual
                            this._isLocked = !this._isLocked;
                        }
                    }
                    else{
                        //aniadimos la casilla antes de modificarla
                        this._restoreManager.addCasilla(casilla);
                        //modifiamos el tipo de la casilla actual
                        this._tablero.modificarCasilla(casilla);
                    }
                    return; //ya hemos comprobado que el click ha sido en una de las casillas
                }
            }
        }
    }

    /*Comprueba dado un x,y del input si corresponde a alguno de los iconos*/
    private void checkUI(int x, int y){
        Graphics g = this._engine.getGraphics();

        int size = this._closeImage.getWidth()/2;
        int posY = (int)(g.getHeightNativeCanvas() * 0.90);
        int posX = (int)(g.getWidthNativeCanvas() * 0.33) - size;
        if(x > posX && x < posX + this._closeImage.getWidth() &&
                y > posY && y < posY + this._closeImage.getHeight()){
            System.out.println("Close image");
            //TODO:Cerrar la partida
        }
        posX = g.getWidthNativeCanvas()/2 - g.getWidthNativeCanvas()/8;
        if(x > posX && x < posX + this._rewindImage.getWidth() &&
                y > posY && y < posY + this._rewindImage.getHeight()){
            System.out.println("Rewind image");
            RestoreCasilla aux = this._restoreManager.getLastCasilla();
               this._tablero.getTablero()[aux.get_position().getX()][aux.get_position().getY()].setTipo(aux.get_currentType());
        }
        posX = g.getWidthNativeCanvas()*3/4 - g.getWidthNativeCanvas()/8;
        if(x > posX && x < posX + this._helpImage.getWidth() &&
                y > posY && y < posY + this._helpImage.getHeight()){
            _helpString = this._tablero.damePista();
            this._isAnyHelp = true;
        }

    }

    private Tablero _tablero;   //tablero del juego
    private Engine _engine;     //engine
    private Image _closeImage, _rewindImage, _helpImage, _blockImage;   //imagenes de iconos
    private Font _font;         //fuente
    private RestoreManager _restoreManager;     //guarda la cola de casillas pre-modificadas

    private int _boardSize;         //tamanio del tablero de juego

    private boolean _isLocked;      //si el usuario ha clicado en una casilla no modificable

    private int debugClickX, debugClickY;
    private boolean debugClick;

    private boolean _isAnyHelp;     //si el usuario ha pedido una pista
    private String _helpString;     //cadena de texto donde se guarda la pista

}
