package es.ucm.videojuegos.moviles.pcengine;

import es.ucm.videojuegos.moviles.engine.Font;
import java.io.InputStream;
import java.io.FileInputStream;

public class PCFont implements Font {
    PCFont(String file,  int size, boolean isBold){
        // Cargamos la fuente del fichero .ttf
        try (InputStream is = new FileInputStream("Bangers-Regular.ttf")) {
            this._font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
            //Asignamos tamanio
            this._font.deriveFont(size);
            //Comprovanis su es negrita
            if(isBold)
                this._font.deriveFont(java.awt.Font.BOLD);  //Asignamos negrita
        }
        catch (Exception e) {
            System.err.println("Error cargando la fuente: " + e);
        }
    }
    private java.awt.Font _font;
}
