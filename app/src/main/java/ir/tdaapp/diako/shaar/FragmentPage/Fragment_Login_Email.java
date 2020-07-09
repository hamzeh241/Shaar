package ir.tdaapp.diako.shaar.FragmentPage;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 5/5/2019.
 */

public class Fragment_Login_Email extends Fragment_Login_Home implements IBase {

    WebView webView;
    ProgressBar ProgressBar;
    TextView textView;
    String TourId = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_login_email, container, false);

        webView = view.findViewById(R.id.webView);
        ProgressBar = view.findViewById(R.id.ProgressBar);
        ProgressBar.setVisibility(View.VISIBLE);
        textView = view.findViewById(R.id.txt_wait);

        try {

            TourId = getArguments().getString("Id");

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(TourId);


            webView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    ProgressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {

        }

        return view;
    }
}
