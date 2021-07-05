package ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.RateDialogService;
import ir.tdaapp.diako.shaar.R;

public class RateDialog extends DialogFragment implements View.OnClickListener {

  public static final String TAG = "RateDialog";

  RatingBar ratingBar;
  Button button;
  RateDialogService service;

  public RateDialog(RateDialogService service) {
    this.service = service;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_add_rating, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    ratingBar = view.findViewById(R.id.ratingBarDialog);
    button = view.findViewById(R.id.btnRatingDialog);
  }

  private void implement() {
    button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnRatingDialog:
        service.onRate((int) ratingBar.getRating());
        Log.i("LOG", "" + ratingBar.getRating());
        dismiss();
        break;
    }
  }
}
