package ir.tdaapp.diako.shaar.ETC;

import android.content.Context;

import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Diako on 7/28/2019.
 */

public class SizeScreen {

    DisplayMetrics displayMetrics;
    Context context;

    public SizeScreen(Context context){
        this.context=context;
        displayMetrics=new DisplayMetrics();
    }

    public int GetWidth(){
        ((AppCompatActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public int GetHeight(){
        ((AppCompatActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
