package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.PublishedAdapter;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.Model.Published_Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/7/2019.
 */

public class Tab_Published_Home extends Fragment implements IBase {

    public static final String TAG = "Tab_Published_Home";
    User user;
    List<Published_Home> homes;
    PublishedAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Internet internet;
    LinearLayout img_not_item;

    RecyclerView recycle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.tab_published_home, container, false);

        FindItem(view);

        GetData();

        return view;
    }

    void FindItem(View view) {
        recycle = view.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler);
        user = new User(getActivity());
        homes = new ArrayList<>();
        internet = new Internet(getActivity());
        img_not_item = view.findViewById(ir.tdaapp.diako.shaar.R.id.img_not_item);
    }

    void GetData() {
        if (internet.HaveNetworkConnection()) {
            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال بارگیری</font>")));
            progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

            String url = Api + "Item/getUseritem?Id=" + user.GetUserId();

            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url, null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject j = response.getJSONObject(i);

                                String Title = j.getString("Title");
                                String Price = j.getString("Price");
                                String Address = j.getString("Address");
                                String Image = ApiImage + "ImageSave/" + j.getString("Image");
                                int Room = Integer.parseInt(j.getString("Room"));
                                int Id = Integer.parseInt(j.getString("Id"));
                                homes.add(new Published_Home(Title, Price, Address, Image, Id, Room));

                            } catch (JSONException e) {
                                int a = 1;
                                a++;
                            }
                        }
                        adapter = new PublishedAdapter(getActivity(), homes);
                        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recycle.setLayoutManager(linearLayoutManager);
                        recycle.setAdapter(adapter);
                    }else{
                        img_not_item.setVisibility(View.VISIBLE);
                    }
                    progress.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();

                    try {

                        new AlertDialog.Builder(getContext())
                                .setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>")))
                                .setMessage((Html.fromHtml("<font color='#FF7F27'>اینترنت شما بسیار ضعیف است لطفا مجددا امتحان کنید</font>")))
                                .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>بارگیری مجدد</font>")), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((AppCompatActivity) getActivity()).getSupportFragmentManager().
                                                beginTransaction().
                                                replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_MyHome()).commit();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } catch (Exception e) {

                    }
                }
            });
            AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley,jsonObjReq));
        } else {
            Toast.makeText(getActivity(), "لطفا اتصال خود را به اینترنت چک نمایید", Toast.LENGTH_LONG).show();
        }
    }
}
