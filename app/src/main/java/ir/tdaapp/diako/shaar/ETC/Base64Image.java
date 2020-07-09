package ir.tdaapp.diako.shaar.ETC;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Diako on 6/26/2019.
 */

public class Base64Image {

    public static String BitmapToBase64(final Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        String encodeImage = Base64.encodeToString(b, Base64.NO_WRAP);
        return encodeImage;
    }
}
