package es.ucm.videojuegos.moviles.aengine;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

import es.ucm.videojuegos.moviles.engine.Font;

public class AFont implements Font {

    public AFont(String name, Context context, Paint p, boolean isBold){
        this._font = Typeface.createFromAsset(context.getAssets(), "Bangers-Regular.ttf");
        this._paint = p;
        if(isBold)
            this._paint.setFakeBoldText(true);
    }
    @Override
    public void setSize(float size) {
        this._paint.setTextSize(size);
    }

    public Typeface get_font() {
        return _font;
    }

    protected Typeface _font;
    Paint _paint;
}