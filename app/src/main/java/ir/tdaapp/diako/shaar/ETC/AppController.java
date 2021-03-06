package ir.tdaapp.diako.shaar.ETC;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import androidx.multidex.MultiDexApplication;

import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 6/22/2019.
 */

public class AppController extends MultiDexApplication {

  public static final String TAG = AppController.class.getSimpleName();

  private RequestQueue mRequestQueue;

  private static AppController mInstance;

  private static Context context;

  private static Handler handler;

  public static Context getContext() {
    return context;
  }

  public static Handler handler() {
    return handler;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    context = getApplicationContext();
    mInstance = this;
    handler = new Handler();
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
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
