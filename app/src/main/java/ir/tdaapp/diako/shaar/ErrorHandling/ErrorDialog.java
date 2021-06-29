package ir.tdaapp.diako.shaar.ErrorHandling;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.diako.shaar.R;

public class ErrorDialog extends DialogFragment {

  public static final String TAG = "ErrorDialog";

  public interface onRetryClick {
    void onRetry();
  }

  public static class Builder {
    Context context;
    String errorTitle,
      errorSubtitle,
      buttonText;

    @DrawableRes
    int imageUrl;

    @ColorRes
    int buttonBackgroundColor;

    onRetryClick clickListener;

    public Builder(Context context) {
      this.context = context;
    }

    public Builder setErrorTitle(@NonNull String errorTitle) {
      this.errorTitle = errorTitle;
      return this;
    }

    public Builder setErrorTitle(@NonNull @StringRes int errorTitle) {
      this.errorTitle = context.getString(errorTitle);
      return this;
    }

    public Builder setErrorSubtitle(@NonNull String errorSubtitle) {
      this.errorSubtitle = errorSubtitle;
      return this;
    }

    public Builder setErrorSubtitle(@NonNull @StringRes int errorSubtitle) {
      this.errorSubtitle = context.getString(errorSubtitle);
      return this;
    }

    public Builder setButtonText(@NonNull String buttonText) {
      this.buttonText = buttonText;
      return this;
    }

    public Builder setButtonText(@NonNull @StringRes int buttonText) {
      this.buttonText = context.getString(buttonText);
      return this;
    }

    public Builder setImageUrl(int imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public Builder setClickListener(@NonNull onRetryClick clickListener) {
      this.clickListener = clickListener;
      return this;
    }

    public Builder setButtonBackgroundColor(@ColorRes int buttonBackgroundColor) {
      this.buttonBackgroundColor = buttonBackgroundColor;
      return this;
    }

    public ErrorDialog build() {
      return new ErrorDialog(this);
    }

  }

  String errorTitle,
    errorSubtitle,
    buttonText;

  TextView title,
    subtitle, button;

  ImageView image;

  ImageButton dismiss;

  CardView btnRetry;

  @DrawableRes
  int imageUrl;

  @ColorRes
  int buttonBackgroundColor;

  onRetryClick clickListener;

  private ErrorDialog(Builder builder) {
    if (builder.errorTitle.isEmpty())
      throw new IllegalStateException("ErrorTitle should be filled");
    else
      this.errorTitle = builder.errorTitle;
    if (builder.errorSubtitle.isEmpty())
      throw new IllegalStateException("ErrorSubtitle should be filled");
    else
      this.errorSubtitle = builder.errorSubtitle;
    if (builder.buttonText.isEmpty())
      throw new IllegalStateException("Button text should be filled");
    else
      this.buttonText = builder.buttonText;
    if (builder.clickListener == null)
      throw new NullPointerException("ClickListener should not be null");
    else
      this.clickListener = builder.clickListener;

    this.imageUrl = builder.imageUrl;
    this.buttonBackgroundColor = builder.buttonBackgroundColor;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_error, container, false);

    findView(view);
    implement();

    return view;
  }

  @Override
  public int getTheme() {
    return R.style.RoundedCornersDialog;
  }

  private void findView(View view) {
    title = view.findViewById(R.id.txtErrorTitle);
    subtitle = view.findViewById(R.id.txtSubtitle);
    button = view.findViewById(R.id.txtRetry);
    btnRetry = view.findViewById(R.id.btnRetry);
    image = view.findViewById(R.id.imageView3);
    dismiss = view.findViewById(R.id.imgDismiss);
  }

  private void implement() {
    title.setText(errorTitle);
    subtitle.setText(errorSubtitle);
    button.setText(buttonText);

    if (imageUrl != 0)
      Glide.with(getContext())
        .load(imageUrl)
        .into(image);

    if (buttonBackgroundColor != 0)
      btnRetry.setCardBackgroundColor(getResources().getColor(buttonBackgroundColor));

    btnRetry.setOnClickListener(v -> {
      clickListener.onRetry();
      dismiss();
    });

    dismiss.setOnClickListener(v -> dismiss());
  }
}
