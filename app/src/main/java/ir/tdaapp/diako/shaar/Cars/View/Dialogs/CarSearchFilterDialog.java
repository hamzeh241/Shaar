package ir.tdaapp.diako.shaar.Cars.View.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import ir.tdaapp.diako.shaar.R;

public class CarSearchFilterDialog extends DialogFragment {

    public static final String TAG = "CarSearchFilterDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_car_filter_search,container,false);


    }
}
