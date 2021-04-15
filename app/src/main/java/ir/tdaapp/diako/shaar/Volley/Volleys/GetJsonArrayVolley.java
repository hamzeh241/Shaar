package ir.tdaapp.diako.shaar.Volley.Volleys;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.Map;

import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.Utility.CancelVolley;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;

public class GetJsonArrayVolley {
    //آدرس ای پی آی
    String Url = "";

    //زمان انتظار برای دریافت جواب
    int TimeOut = 0;

    //تعداد دفعات تکرار
    int Retries = -1;

    float Multiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

    IGetJsonArray iGetJsonArray;

    JsonArrayRequest request;

    Map<String, String> header;

    public GetJsonArrayVolley(String url, IGetJsonArray iGetJsonArray) {
        Url = url;
        this.iGetJsonArray = iGetJsonArray;
        Get();
    }

    public GetJsonArrayVolley(String url, int timeOut, IGetJsonArray iGetJsonArray) {
        Url = url;
        TimeOut = timeOut;
        this.iGetJsonArray = iGetJsonArray;
        Get();
    }

    public GetJsonArrayVolley(String url, int timeOut, int retries, IGetJsonArray iGetJsonArray) {
        Url = url;
        TimeOut = timeOut;
        Retries = retries;
        this.iGetJsonArray = iGetJsonArray;
        Get();
    }

    public GetJsonArrayVolley(String url, int timeOut, int retries, float multiplier, IGetJsonArray iGetJsonArray) {
        Url = url;
        TimeOut = timeOut;
        Retries = retries;
        Multiplier = multiplier;
        this.iGetJsonArray = iGetJsonArray;
        Get();
    }

    void Get() {
        ResaultGetJsonArrayVolley resault = new ResaultGetJsonArrayVolley();

        try {

            request = new JsonArrayRequest(Request.Method.GET, Url, null,
                    response -> {
                        resault.setJsonArray(response);
                        resault.setResault(ResaultCode.Success);
                        iGetJsonArray.Get(resault);
                    },
                    error -> {
                        if (error instanceof TimeoutError) {
                            resault.setResault(ResaultCode.TimeoutError);
                        } else if (error instanceof ServerError) {
                            resault.setResault(ResaultCode.ServerError);
                        } else if (error instanceof NetworkError) {
                            resault.setResault(ResaultCode.NetworkError);
                        } else if (error instanceof ParseError) {
                            resault.setResault(ResaultCode.ParseError);
                        } else {
                            resault.setResault(ResaultCode.Error);
                        }
                        resault.setJsonArray(null);
                        resault.setMessage(error.toString());
                        iGetJsonArray.Get(resault);
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (getHeader() != null)
                        return getHeader();
                    return super.getHeaders();
                }
            };

            RetryPolicy policy = new DefaultRetryPolicy(TimeOut, Retries, Multiplier);
            request.setRetryPolicy(policy);

            AppController.getInstance().addToRequestQueue(request);

        } catch (Exception e) {
            resault.setJsonArray(null);
            resault.setResault(ResaultCode.Error);
            resault.setMessage(e.toString());
            iGetJsonArray.Get(resault);
        }
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    //برای لغو کردن عملیات
    public void Cancel(String TAG, Context context) {
        new CancelVolley(TAG, request, context);
    }
}
