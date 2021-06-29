package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import androidx.fragment.app.Fragment;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;

public class CarBaseFragment extends Fragment {

    void showErrorDialog(ErrorDialog.Builder builder) {
        ErrorDialog dialog = builder.build();
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
    }
}
