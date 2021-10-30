package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.TouchEvent;

public class OhNoGame implements Application {
    @Override
    public void onInit(Engine g) {
        this._size = 4;
        this._tablero = new Tablero(this._size);
        this._closeImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/close.png");
        this._rewindImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/history.png");
        this._helpImage = g.getGraphics().newImage("PCGame/src/main/assets/sprites/eye.png");
        this._font = g.getGraphics().newFont("PCGame/src/main/assets/fonts/JosefinSans-Bold.ttf", 70, false);
     }

    @Override
    public void onUpdate(double deltaTime) {
        //Recoger input
    }

    @Override
    public void onDraw(Graphics g) {
        drawText(g);
        drawBoard(g);
        drawUI(g);

    }


    @Override
    public String getName() {
        return "OhNoGame";
    }

    private void drawBoard(Graphics g) {
        //distancia con el texto de arriba
        int offsetText = 50 + 70;
        //radio de cada circulo
        int radius = (int)(g.getWidth() / (this._size * 4/3));
        //calculamos el offset entre cada circulo
        float offseBetween = (g.getWidth() - radius * this._size)/(float)(this._size + 1);
        //Recorremos cada casilla del tablero
        for(int i = 0; i< this._size; i++){
            for(int j = 0; j< this._size; j++){
                Casilla casilla = this._tablero.getTablero()[i][j];
                //Asignamos el color actual dependiendo del tipo
                switch (casilla.getTipoActual()){
                    case AZUL:  g.setColor(0xff33c7ff);     break;
                    case ROJO:  g.setColor(0xfffa4848);     break;
                    case VACIO: g.setColor(0xffdfdfdf);     break;
                }
                int x = radius/(this._size + 1) + (int)(radius+offseBetween) * j;
                int y = radius/3 + offsetText +(int)(radius+offseBetween) * i;
                //pintamos
                g.fillCircle(x, y, radius);
                if(!casilla.esModificable()){
                    //TODO Poner candado
                    //Si es azul no modificable ponemos el numero
                    if(casilla.getTipoActual() == Casilla.Tipo.AZUL){
                        g.setColor(0xffffffff);
                        String text = "" + casilla.getNumero();
                        g.drawText(text, x + radius /4, y + radius - radius/5);
                    }
                }
            }
        }
    }
    private void drawText(Graphics g){
        g.setFont(this._font);
        String text = this._size + "x" + this._size;
        g.setColor(0xff000000);
        g.drawText(text, g.getWidth()/3,g.getHeigth() / 5);
    }

    private void drawUI(Graphics g){
        //Dibuja iconos Pista/Deshacer/Rendirse
        g.drawImage(this._closeImage,g.getWidth()/4 - g.getWidth()/8, g.getHeigth()-(g.getHeigth()/7));
        g.drawImage(this._rewindImage,g.getWidth()/2 - g.getWidth()/8,g.getHeigth()-(g.getHeigth()/7));
        g.drawImage(this._helpImage,g.getWidth()*3/4 - g.getWidth()/8,g.getHeigth()-(g.getHeigth()/7));
    }
    Tablero _tablero;
    Image _closeImage, _rewindImage, _helpImage;
    Font _font;

    int _size;

}
