package ir.tdaapp.diako.shaar.Fruits.View.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.Validation;
import ir.tdaapp.diako.shaar.R;

public class FruitCartDetailsDialog extends DialogFragment {

  public static final String TAG = "FruitCartDetailsDialog";

  EditText address, phone, description;
  Button submit;

  FruitCartResult result;

  public interface FruitCartResult {
    void onResult(String address, String phone, String description);
  }

  public FruitCartDetailsDialog(FruitCartResult result) {
    this.result = result;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.dialog_fruit_cart_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    address = view.findViewById(R.id.edtAddress);
    phone = view.findViewById(R.id.edtPhone);
    description = view.findViewById(R.id.edtDescription);
    submit = view.findViewById(R.id.btnSubmitBuy);

    implement();
  }

  private void implement() {
    submit.setOnClickListener(v -> {
      if (checkValidation()) {
        result.onResult(address.getText().toString(), phone.getText().toString(), description.getText().toString());
        dismiss();
      }
    });
  }

  private boolean checkValidation() {
    boolean a = Validation.Required(address, "آدرس را وارد کنید");
    boolean b = Validation.Required(phone, "شماره گیرنده را وارد کنید");
    boolean c = Validation.ValidPhoneNumber(phone, "شماره خود را درست وارد کنید");

    return a && b && c;
  }
}
