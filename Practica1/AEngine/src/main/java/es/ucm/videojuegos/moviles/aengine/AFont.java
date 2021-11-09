package es.ucm.videojuegos.moviles.aengine;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

import es.ucm.videojuegos.moviles.engine.Font;

/*Clase que encapsula las fuentes en Android
* Ofrece funcionalidades basicas como el cambio del uso de la negrita o del
* tamaño de la fuente*/
public class AFont implements Font {

    /*Contructora
     * @param name nombre del fichero que contiene la fuente
     * @context contecto que va a permitir acceder al fichero de la fuente
     * @param p paint al que se le va a comunicar cambios en el tamaño y en el uso de la negrita para esta fuente
     * @param isBold boolean que si la fuente se va a dibujar en negrita*/
    public AFont(String name, Context context, Paint p, boolean isBold, int size){
        this._font = Typeface.createFromAsset(context.getAssets(), name);
        this._paint = p;
        if(isBold)
            this._paint.setFakeBoldText(true);
        this.setSize(size);
    }

    /*Metodo que permite establecer el tamaño con el que queremos que se use la fuente
    * @param size tamaño a tener en cuenta para dibujar la fuente*/
    @Override
    public void setSize(float size) {
        this._paint.setTextSize(size);
    }

    /*Metodo que devuelve la fuente
    * @return fuente que se almacena*/
    public Typeface get_font() {
        return _font;
    }

    protected Typeface _font;
    Paint _paint;
}