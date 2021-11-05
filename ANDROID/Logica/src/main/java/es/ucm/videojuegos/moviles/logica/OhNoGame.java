package es.ucm.videojuegos.moviles.logica;

import java.awt.Color;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.TouchEvent;


public class OhNoGame implements Application {
    /*Crea los recursos que va a necesitar a lo largo de la aplicacion*/
    @Override
    public void onInit(Engine g) {
        //Atributos de la clase
        this._boardSize = 7;
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
     }

    @Override
    public void onUpdate(double deltaTime) {
        //Recogemos input
        List<TouchEvent> list = this._engine.getInput().getTouchEvents();
        //Procesamos el input
        for (TouchEvent e: list) {
            checkCircles(e.get_x(),e.get_y());
            checkUI(e.get_x(),e.get_y());
        }
        //Limpiamos la lista de eventos
        this._engine.getInput().clearEvents(list);

        //Comprobamos si se ha terminado el juego
        checkEndedGame();
    }

    /*Dibuja el estado del juego*/
    @Override
    public void onDraw() {
        drawText(this._engine.getGraphics());
        drawBoard(this._engine.getGraphics());
        drawUI(this._engine.getGraphics());
    }

    /*Devuelve el nombre de la aplicacion*/
    @Override
    public String getName() {
        return "OhNoGame";
    }

    /*Dibuja el tablero del juego*/
    private void drawBoard(Graphics g) {

        //radio de cada circulo
        float diametro = (g.getWidthNativeCanvas() / (this._boardSize + 1));
        float radius = diametro * 0.8f * 0.5f;
        //Dalculamos el offset entre cada circulo
        float offseBetween = diametro * 0.2f;
        //Situamos l x e y para pintar el tablero
        g.restore();
        g.setColor(0xffffffff);
        // La pantalla de JFrame empieza a pintarse tras 7 pixeles
        g.translate((int)offseBetween/2,g.getHeigthNativeCanvas() / 4);


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
                int x = (int)(diametro+offseBetween/2) * j + (int)radius;
                int y = (int)(diametro+offseBetween/2) * i + (int)radius;
                //pintamos
                g.fillCircle(x,y, (int)radius);
                if(!casilla.esModificable()){
                    //Si es azul no modificable ponemos el numero
                    if(casilla.getTipoActual() == Casilla.Tipo.AZUL){
                        g.setColor(0xffffffff);         //Asignamos el color blanco
                        this._font.setSize(radius);     //Cambiamos el tamanio de letra
                        g.setFont(this._font);          //Asignamos la fuente
                        String text = "" + casilla.getNumero();
                        g.drawText(text, x - (int)radius/4, y +(int)radius/4);
                    }
                    //Si la casilla es roja y esta activado el lock pintamos el candado
                    /*else if(this._lo && this._isLocked){
                        g.drawImage(this._blockImage, this._xLock, this._yLock);
                    }*/
                }
            }
        }
    }
    /*Dibuja el texto situado encima del tablero*/
    private void drawText(Graphics g){
        g.restore();
        this._font.setSize(70);     //Asignamos tamanio de fuente
        g.setFont(this._font);      //Asignamos la fuente;
        g.setColor(0xff000000);     //Color a negro
        String text = this._boardSize + "x" + this._boardSize;
        g.drawText(text, g.getWidthNativeCanvas()/3,g.getHeigthNativeCanvas() / 5);
        //TODO: Pintar pistas
    }
    /* Dibuja los iconos de interfaz situados debajo del tablero*/
    private void drawUI(Graphics g){
        //Dibuja iconos Pista/Deshacer/Rendirse
        g.restore();
        g.translate(0,g.getHeigthNativeCanvas()-(g.getHeigthNativeCanvas()/8));
        g.scale(0.8,0.8);

        g.drawImage(this._closeImage,g.getWidthNativeCanvas()/4, 0);
        g.drawImage(this._rewindImage,g.getWidthNativeCanvas()/2, 0);
        g.drawImage(this._helpImage,g.getWidthNativeCanvas()*3/4,0 );
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
        int offsetText = _engine.getGraphics().getHeigthNativeCanvas() / 4;
        //radio de cada circulo
        int radius = (int)(this._engine.getGraphics().getWidthNativeCanvas() / (this._boardSize * 1.5));
        //calculamos el offset entre cada circulo
        float offseBetween = (this._engine.getGraphics().getWidthNativeCanvas() - radius * this._boardSize)/(float)(this._boardSize + 1);

        //Recorremos cada casilla del tablero
        for(int i = 0; i< this._boardSize; i++){
            for(int j = 0; j< this._boardSize; j++){
                Casilla casilla = this._tablero.getTablero()[i][j];
                int cx = (int)offseBetween + radius/(this._boardSize + 1) + (int)(radius+offseBetween) * j;
                int cy = (int)(radius+offseBetween) * i + offsetText;
                //calculamos catetos
                int deltaX = Math.abs(cx-x);
                int deltaY = Math.abs(cy-y);
                //calculamos la hipotenusa
                double distance = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
                //comprobamos si se está clicando
                if(distance <= radius){
                    if(!casilla.esModificable()){
                        if(casilla.getTipoActual() == Casilla.Tipo.ROJO){
                            this._xLock = cx; this._yLock = cy;
                            this._isLocked = true;
                        }
                    }
                    else{
                        _tablero.modificarCasilla(casilla);
                    }
                    return;
                }
            }
        }
    }

    /*Comprueba dado un x,y del input si corresponde a alguno de los iconos*/
    private void checkUI(int x, int y){
        Graphics g = this._engine.getGraphics();

        int posY = g.getHeigthNativeCanvas()-(g.getHeigthNativeCanvas()/7);
        int posX = g.getWidthNativeCanvas()/4 - g.getWidthNativeCanvas()/8;
        if(x > posX && x < posX + this._closeImage.getWidth() &&
                y > posY && y < posY + this._closeImage.getHeight()){
            System.out.println("Close image");
            //TODO:Cerrar la partida
        }
        posX = g.getWidthNativeCanvas()/2 - g.getWidthNativeCanvas()/8;
        if(x > posX && x < posX + this._rewindImage.getWidth() &&
                y > posY && y < posY + this._rewindImage.getHeight()){
            System.out.println("Rewind image");
            //TODO: Hacer paso atras
        }
        posX = g.getWidthNativeCanvas()*3/4 - g.getWidthNativeCanvas()/8;
        if(x > posX && x < posX + this._helpImage.getWidth() &&
                y > posY && y < posY + this._helpImage.getHeight()){
            System.out.println(this._tablero.damePista());
            //TODO: Que escriba esto en la parte de arriba
        }

    }

    private Tablero _tablero;   //tablero del juego
    private Engine _engine;     //engine
    private Image _closeImage, _rewindImage, _helpImage, _blockImage;   //imagenes de iconos
    private Font _font;         //fuente

    private int _boardSize;         //tamanio del tablero de juego
    private int _xLock, _yLock;     //posicion x,y de la casilla con el simbolo de block
    private boolean _isLocked;      //si el usuario ha clicado en una casilla no modificable
}
