package ir.tdaapp.diako.shaar.ETC;

import android.util.Patterns;
import android.widget.EditText;

import java.math.BigInteger;

/**
 * Created by Diako on 7/30/2019.
 */

public class Validation {

    public boolean Required(EditText txt, String Message) {
        if (txt.getText().toString().equalsIgnoreCase("")) {
            txt.setError(Message);
            return true;
        }
        return false;
    }

    public boolean StringLength(EditText txt, int MinChar, int MaxChar, String Error) {
        String Text = txt.getText().toString();

        if (Text.length() < MinChar || Text.length() > MaxChar) {
            txt.setError(Error);
            return true;
        }
        return false;
    }

    public boolean MaxLength(EditText txt, String Message, int MaxChar) {
        String Text = txt.getText().toString();

        if (Text.length() > MaxChar) {
            txt.setError(Message);
            return true;
        }
        return false;
    }

    public boolean MinLength(EditText txt, String Message, int MinChar) {
        String Text = txt.getText().toString();

        if (Text.length() < MinChar) {
            txt.setError(Message);
            return true;
        }
        return false;
    }

    public boolean EmailValid(EditText txt, String Message) {
        String Text = txt.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(Text).matches()) {
            txt.setError(Message);
            return true;
        }
        return false;
    }

    public boolean PriceValid(EditText txt, BigInteger MinPrice, BigInteger MaxPrice, String Message) {
        String Text = txt.getText().toString();

        if (Text.length() == 0 || Text.equalsIgnoreCase("")) {
            txt.setError(Message);
            return true;
        }

        BigInteger price = new BigInteger(Text);

        if (price.doubleValue() < MinPrice.doubleValue() || price.doubleValue() > MaxPrice.doubleValue()) {
            txt.setError(Message);
            return true;
        }

        return false;
    }

    public boolean ValidMobile(EditText txt, String Message) {
        String Text = txt.getText().toString();

        if (Text.length() > 11 || Text.length() < 10) {
            txt.setError(Message);
            return true;
        }
        return false;
    }

    public boolean ValidPhone(EditText txt, String Message) {
        String Text = txt.getText().toString();

        if (Text.length() != 8) {
            txt.setError(Message);
            return true;
        }
        return false;
    }

    public boolean MinValue(EditText txt, String Message, int MinVal) {
        String Text = txt.getText().toString();

        if (!Text.equalsIgnoreCase("")) {
            if (Integer.parseInt(Text) < MinVal) {
                txt.setError(Message);
                return true;
            }
        }
        return false;
    }

}
