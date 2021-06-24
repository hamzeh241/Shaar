package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import androidx.fragment.app.Fragment;

import ir.tdaapp.diako.shaar.ErrorHandeling.ErrorDialog;

public class CityGuideBaseFragment extends Fragment {
    void showErrorDialog(String title, String subtitle, ErrorDialog.onRetryClick clickListener) {
        ErrorDialog dialog = new ErrorDialog(title, subtitle, clickListener);
        dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
    }
}
