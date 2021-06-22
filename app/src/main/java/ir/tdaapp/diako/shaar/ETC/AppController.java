package ir.tdaapp.diako.shaar.ETC;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import androidx.multidex.MultiDexApplication;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 6/22/2019.
 */

public class AppController extends MultiDexApplication {

  public static final String TAG = AppController.class.getSimpleName();

  private RequestQueue mRequestQueue;

  private static AppController mInstance;

  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;
    ViewPump.init(ViewPump.builder()
      .addInterceptor(new CalligraphyInterceptor(
        new CalligraphyConfig.Builder()
          .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
          .setFontAttrId(R.attr.fontPath)
          .build()))
      .build());
  }

  public static synchronized AppController getInstance() {
    return mInstance;
  }


  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> req, String tag) {
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(req);
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }
}
