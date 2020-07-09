package ir.tdaapp.diako.shaar.ETC;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Diako on 8/17/2019.
 */

public class Font {

    Context context;

    public Font(Context context){
        this.context=context;
    }

    public void SetFont(String Path, TextView lbl){
        Typeface face = Typeface.createFromAsset(context.getAssets(),Path);
        lbl.setTypeface(face);
    }

    public void SetFont(String Path, EditText txt){
        Typeface face = Typeface.createFromAsset(context.getAssets(),Path);
        txt.setTypeface(face);
    }
}
