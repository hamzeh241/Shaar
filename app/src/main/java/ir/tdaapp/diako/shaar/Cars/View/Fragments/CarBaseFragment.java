package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import ir.tdaapp.diako.shaar.ErrorHandeling.ErrorDialog;

public class CarBaseFragment extends Fragment {

    void showErrorDialog(String title, String subtitle, ErrorDialog.onRetryClick clickListener) {
        ErrorDialog dialog = new ErrorDialog(title, subtitle, clickListener);
        dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
    }

    void showErrorDialog(String title, String subtitle, @DrawableRes int imageRes, ErrorDialog.onRetryClick clickListener) {
        ErrorDialog dialog = new ErrorDialog(title, subtitle, imageRes, clickListener);
        dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
    }
}
