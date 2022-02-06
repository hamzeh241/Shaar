package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.List;

import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.Fruits.View.Activities.FruitsActivity;
import ir.tdaapp.diako.shaar.MainActivity;

/**
 * Created by Diako on 6/27/2019.
 */

public class Succefull_Register extends Fragment {
    public static final String TAG = "Succefull_Register";


    Button btn_Down;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.succefull_register, container, false);

        btn_Down = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Done);

        btn_Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.getInstance().HiddenLogin();

                switch (getActivity().getClass().getSimpleName()) {
                    case "MainActivity":
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        List<Fragment> fragmentList = manager.getFragments();
                        for(Fragment fragment : fragmentList){
                            if (fragment instanceof Fragment_Login_Home || fragment instanceof Fragment_Add_Account || fragment instanceof Fragment_SMS_Panel ||
                                    fragment instanceof Succefull_Register){
                                manager.popBackStack();
                            }
                        }

                        break;
                    case "GuideActivity":
                        ((GuideActivity) getActivity()).removeStack();
                        break;

                    case "CarActivity":
                        ((CarActivity) getActivity()).removeStack();
                        break;

                    case "FruitsActivity":
                        ((FruitsActivity) getActivity()).removeStack();
                        break;
                }

            }
        });
        hideKeyboard(getActivity());
        return view;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
