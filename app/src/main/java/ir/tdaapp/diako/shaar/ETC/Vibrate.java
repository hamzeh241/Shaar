package ir.tdaapp.diako.shaar.ETC;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Diako on 7/24/2019.
 */

public class Vibrate {
    static Vibrator vibrator;

    public static void ButtonClick(Context context) {
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        // 0 : Start without a delay
        // 400 : Vibrate for 400 milliseconds
        // 200 : Pause for 200 milliseconds
        // 400 : Vibrate for 400 milliseconds
        long[] mVibratePattern = new long[]{0, 50, 50, 0};

        // -1 : Do not repeat this pattern
        // pass 0 if you want to repeat this pattern from 0th index
        vibrator.vibrate(mVibratePattern, -1);
    }
}
