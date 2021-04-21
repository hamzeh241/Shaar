package ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.diako.shaar.R;

public class MessageDialog extends DialogFragment implements View.OnClickListener {

  public static final String TAG = "MessageDialog";

  TextView txtMessage;
  Button button;
  boolean showButton;
  String message;

  public MessageDialog(String message, boolean showButton) {
    this.showButton = showButton;
    this.message = message;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_message, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    txtMessage = view.findViewById(R.id.txtMessageDialog);
    button = view.findViewById(R.id.btnMessageDialog);
  }

  private void implement() {
    button.setVisibility(showButton ? View.VISIBLE : View.GONE);
    button.setOnClickListener(this);
    txtMessage.setText(message);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnMessageDialog:
        dismiss();
        break;
    }
  }
}
