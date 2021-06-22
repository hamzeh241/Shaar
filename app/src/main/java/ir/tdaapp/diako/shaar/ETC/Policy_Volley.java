package ir.tdaapp.diako.shaar.ETC;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

/**
 * Created by Diako on 8/20/2019.
 */

public class Policy_Volley {

    public static StringRequest SetTimeOut(int Time,StringRequest request){
        RetryPolicy policy = new DefaultRetryPolicy(Time, DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        return request;
    }

    public static JsonObjectRequest SetTimeOut(int Time, JsonObjectRequest request){
        RetryPolicy policy = new DefaultRetryPolicy(Time, DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        return request;
    }

    public static JsonArrayRequest SetTimeOut(int Time, JsonArrayRequest request){
        RetryPolicy policy = new DefaultRetryPolicy(Time, DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        return request;
    }

    public static JsonObjectRequest SetRetryAndPolicy(int Time, JsonObjectRequest request){
        request.setRetryPolicy(new DefaultRetryPolicy(Time,0,0));
        return request;
    }

    public static StringRequest SetRetryAndPolicy(int Time, StringRequest request){
        request.setRetryPolicy(new DefaultRetryPolicy(Time,0,0));
        return request;
    }

    public static JsonArrayRequest SetRetryAndPolicy(int Time, JsonArrayRequest request){
        request.setRetryPolicy(new DefaultRetryPolicy(Time,0,0));
        return request;
    }

    public static JsonObjectRequest SetPostRetryAndPolicy(JsonObjectRequest request){
        request.setRetryPolicy(new DefaultRetryPolicy(0,-1,0));
        return request;
    }

    public static StringRequest SetPostRetryAndPolicy(StringRequest request){
        request.setRetryPolicy(new DefaultRetryPolicy(0,-1,0));
        return request;
    }

    public static JsonArrayRequest SetPostRetryAndPolicy(JsonArrayRequest request){
        request.setRetryPolicy(new DefaultRetryPolicy(0,-1,0));
        return request;
    }
}
