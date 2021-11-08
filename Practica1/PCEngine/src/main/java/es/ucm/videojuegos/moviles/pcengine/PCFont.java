package es.ucm.videojuegos.moviles.pcengine;

import es.ucm.videojuegos.moviles.engine.Font;
import java.io.InputStream;
import java.io.FileInputStream;

/*Clase que encapsula las fuentes en PC
 * Ofrece funcionalidades basicas como el uso de la negrita o el cambio del
 * tamaño de la fuente*/
public class PCFont implements Font {
    PCFont(String file,  float size, boolean isBold){
        // Cargamos la fuente del fichero .ttf
        try (InputStream is = new FileInputStream(file)) {
            this._font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
            //Asignamos tamanio
            this._font = this._font.deriveFont(size);
            //Comprobamos si es negrita
            if(isBold)
                this._font = this._font.deriveFont(java.awt.Font.BOLD);  //Asignamos negrita
        }
        catch (Exception e) {
            System.err.println("Error cargando la fuente: " + e);
        }
    }

    /*Metodo que permite obtener la fuente
    * @return fuente almacenada*/
    public java.awt.Font get_font() {
        return _font;
    }

    /*Metodo que permite establecerle un tamanio concreto a la fuente*/
    @Override
    public void setSize(float size) {
        //Asignamos tamanio
        this._font = this._font.deriveFont(size);
    }

    private java.awt.Font _font; //Fuente de Java/PC
}
