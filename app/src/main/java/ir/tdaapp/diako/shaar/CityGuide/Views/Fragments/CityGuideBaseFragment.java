package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import androidx.fragment.app.Fragment;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;

public class CityGuideBaseFragment extends Fragment {

    void showErrorDialog(ErrorDialog.Builder builder) {
        ErrorDialog dialog = builder.build();
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
    }
}
