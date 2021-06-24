package ir.tdaapp.diako.shaar.ErrorHandeling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import ir.tdaapp.diako.shaar.R;

public class ErrorDialog extends DialogFragment {

  public interface onRetryClick {
    void onRetry();
  }

  public static final String TAG = "ErrorDialog";


  String title, subtitle;
  int image;
  onRetryClick clickListener;

  TextView txtErrorTitle,txtSubtitle,btnRetry;
  ImageView imgError;
  ImageButton btnDismis;

  public ErrorDialog(String title, String subtitle, int image, onRetryClick clickListener) {
    this.title = title;
    this.subtitle = subtitle;
    this.image = image;
    this.clickListener = clickListener;
  }

  public ErrorDialog(String title, String subtitle, onRetryClick clickListener) {
    this.title = title;
    this.subtitle = subtitle;
    this.clickListener = clickListener;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.dialog_error,container,false);

    findViews(view);
    implement();
    setCancelable(false);

    return view;
  }

  @Override
  public int getTheme() {
    return R.style.RoundedCornersDialog;
  }

  private void findViews(View view){
    txtErrorTitle = view.findViewById(R.id.txtErrorTitle);
    txtSubtitle = view.findViewById(R.id.txtSubtitle);
    imgError = view.findViewById(R.id.img_error);
    btnRetry = view.findViewById(R.id.btnRetry);
    btnDismis = view.findViewById(R.id.imgDismiss);
  }

  private void implement() {
    if (!title.isEmpty())
      txtErrorTitle.setText(title);
    if (!subtitle.isEmpty())
     txtSubtitle.setText(subtitle);
    if (image != 0)
      Glide.with(getContext())
        .load(image)
        .into(imgError);

  btnRetry.setOnClickListener(v -> {
      clickListener.onRetry();
      dismiss();
    });

    btnDismis.setOnClickListener(v -> dismiss());
  }
}
