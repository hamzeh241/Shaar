package ir.tdaapp.diako.shaar.FragmentPage;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import ir.tdaapp.diako.shaar.Adapter.ImagePagerAdapter;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 8/16/2019.
 */

public class Fragment_Show_Images extends Fragment implements IBase {

    public static final String TAG = "Fragment_Show_Images";

    ViewPager ImagePager;
    ImagePagerAdapter imagePagerAdapter;
    List<String> Images;
    RelativeLayout Close;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_images, container, false);

//        ((MainActivity) getActivity()).frameLayout2.setVisibility(View.GONE);

        FindItem(view);

        GetImagesInServer();

        OnClick();

        return view;
    }

    void FindItem(View view) {
        ImagePager = view.findViewById(R.id.ImagePager);
        Images = new ArrayList<>();
        Close = view.findViewById(R.id.Close);
    }

    void OnClick() {
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });
    }

    //در اینجا لیست عکس ها در سرور دریافت می شوند
    void GetImagesInServer() {

        Bundle bundle = getArguments();

        if (bundle != null) {
            int Id = bundle.getInt("Id");

            String url = Api + "Item/ImagesByIdAsynce?id=" + Id;

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    SetImages(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        GetImagesInServer();
                    } catch (Exception e) {

                    }
                }
            });

            AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley,request));
        }
    }

    //در اینجا عکس های دریافت شده در آداپتر قرار می گیرند
    void SetImages(JSONArray array) {
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    //JSONObject object = array.getJSONObject(i);
                    String Image = array.get(i).toString();
                    Images.add(ApiImage + "ImageSave/" + Image);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Images.add(ApiImage + "ImageSave/NoImage");
        }

        imagePagerAdapter = new ImagePagerAdapter(getContext(), Images);
        ImagePager.setAdapter(imagePagerAdapter);
    }
}
