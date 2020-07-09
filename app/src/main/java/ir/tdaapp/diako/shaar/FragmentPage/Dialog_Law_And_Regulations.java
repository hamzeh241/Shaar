package ir.tdaapp.diako.shaar.FragmentPage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 8/17/2019.
 */

public class Dialog_Law_And_Regulations extends DialogFragment {

    public static final String TAG="Dialog_Law_And_Regulations";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_law_and_regulations,container,false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        return view;
    }
}
