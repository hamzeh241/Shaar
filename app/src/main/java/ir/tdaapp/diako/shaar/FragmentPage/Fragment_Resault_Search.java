package ir.tdaapp.diako.shaar.FragmentPage;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.ListHomeAdapter;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.Model.List_Home;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.ViewModel.VM_Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Diako on 5/15/2019.
 */

public class Fragment_Resault_Search extends Fragment implements IBase {

    public static final String TAG="Fragment_Resault_Search";

    RecyclerView recyclerView;
    RelativeLayout Filter;
    RelativeLayout Back;
    DBAdapter dbAdapter;
    LinearLayoutManager linearLayoutManager;
    Internet internet;
    TextView txt_CountItem;
    VM_Search vm_search;
    int CountItem = -1;
    ProgressBar progressbar;
    ListHomeAdapter listHomeAdapter;
    boolean loading = true;
    Fragment_Dialog_Filter_Search fragment_dialog_filter_search;
    ImageView search,Save_Search;
    LinearLayout img_not_item;
    JsonArrayRequest jsonObjReq;
    RequestQueue requestQueue;
    FloatingActionButton fab_add_home;
    int userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_resault_search, container, false);
        FindItem(view);
        SetNews();

        OnClick();

        Scroll();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        requestQueue= Volley.newRequestQueue(getContext());

        jsonObjReq.setTag(TAG);

        requestQueue.add(jsonObjReq);

        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    public void SetNews() {

        Bundle bundle = getArguments();
        int Type = 0;

        if (bundle != null)
            Type = bundle.getInt("Type");

        SetFilter(Type);

        if (internet.HaveNetworkConnection()) {

            Cursor GetSearch = dbAdapter.ExecuteQ("select * from TblLastSearch");

            if (GetSearch != null && GetSearch.moveToLast()) {

                //در اینجا آخرین سطر جدول lastSearch را خوانده داده های آن را استخراج می کنیم
                Long MinPrice = Long.valueOf(GetSearch.getString(6));
                Long MaxPrice = Long.valueOf(GetSearch.getString(7));
                Long MinMortgage = Long.valueOf(GetSearch.getString(8));
                Long MaxMortgage = Long.valueOf(GetSearch.getString(9));
                Long MinArea = Long.valueOf(GetSearch.getString(10));
                Long MaxArea = Long.valueOf(GetSearch.getString(11));
                vm_search.setTarget(Integer.parseInt(GetSearch.getString(1)));
                vm_search.setTypeHome(Integer.parseInt(GetSearch.getString(2)));
                vm_search.setLocationId(Integer.parseInt(GetSearch.getString(3)));
                vm_search.setFullRent(Boolean.valueOf(GetSearch.getString(4)));
                vm_search.setFullMortgage(Boolean.valueOf(GetSearch.getString(5)));
                vm_search.setMinPrice(BigInteger.valueOf(MinPrice));
                vm_search.setMaxPrice(BigInteger.valueOf(MaxPrice));
                vm_search.setMinMortgage(BigInteger.valueOf(MinMortgage));
                vm_search.setMaxMortgage(BigInteger.valueOf(MaxMortgage));
                vm_search.setMinArea(BigInteger.valueOf(MinArea));
                vm_search.setMaxArea(BigInteger.valueOf(MaxArea));
                vm_search.setCountRoom(Integer.parseInt(GetSearch.getString(12)));
                vm_search.setPage(0);

                GetHomes();

            }
            GetSearch.close();
            dbAdapter.close();
        } else {
            Toast.makeText(getContext(), "لطفا اینترنت خود را چک نمایید", Toast.LENGTH_SHORT).show();
        }
    }

    void FindItem(View view) {
        recyclerView = view.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_List_Home);
        Back = view.findViewById(ir.tdaapp.diako.shaar.R.id.Back);
        dbAdapter = new DBAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        internet = new Internet(getContext());
        txt_CountItem = view.findViewById(ir.tdaapp.diako.shaar.R.id.CountItem);
        vm_search = new VM_Search();
        progressbar = view.findViewById(ir.tdaapp.diako.shaar.R.id.progressbar);
        Filter = view.findViewById(ir.tdaapp.diako.shaar.R.id.filter);
        fragment_dialog_filter_search = new Fragment_Dialog_Filter_Search();
//        search = view.findViewById(ir.tdaapp.diako.shaar.R.id.search);
        img_not_item = view.findViewById(ir.tdaapp.diako.shaar.R.id.img_not_item);
        Save_Search=view.findViewById(ir.tdaapp.diako.shaar.R.id.Save_Search);
        search = view.findViewById(R.id.img_btn_search_main);
        fab_add_home = view.findViewById(R.id.fab_add_home_main);
        userId = new User(getContext()).GetUserId();
    }

    void OnClick() {

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_dialog_filter_search.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "Fragment_Dialog_Filter_Search");
            }
        });

        Save_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Push("Fragment_Save_Search",getActivity());
                ((MainActivity)getActivity()).onAddFragment(new Fragment_Save_Search(),0,0,true,Fragment_Save_Search.TAG);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Stack_Back.MyStack_Back.Push("Fragment_Search_Home",getActivity());
                Fragment_Search_Home fragment_search_home=new Fragment_Search_Home(() -> {
                    SetNews();
                });
                ((MainActivity)getActivity()).onAddFragment(fragment_search_home,0,0,true,Fragment_Search_Home.TAG);
            }
        });

        fab_add_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userId == 0){
                    Toasty.info(getContext(),R.string.addAccuont,Toast.LENGTH_SHORT,false).show();
                    ((MainActivity)getActivity()).onAddFragment(new Fragment_Login_Home(),R.anim.fadein,R.anim.fadeout,true,Fragment_Login_Home.TAG);
                }else {
//                    Stack_Back.MyStack_Back.Push("Fragment_Add_Home_fab",getActivity());
                    ((MainActivity)getActivity()).onAddFragment(new Fragment_Add_Home(),0,0,true,Fragment_Add_Home.TAG);
                }
            }
        });
    }


    //در این متد لیست منزل در api خوانده می شوند
    void GetHomes() {

        progressbar.setVisibility(View.VISIBLE);

        JSONArray array = new JSONArray();

        JSONObject json = new JSONObject();
        try {
            json.put("EnumTarget", vm_search.getTarget());
            json.put("FkType", vm_search.getTypeHome());
            json.put("FkLocation", vm_search.getLocationId());
            json.put("FullPrice", vm_search.isFullRent());
            json.put("MinPrice", vm_search.getMinPrice());
            json.put("MaxPrice", vm_search.getMaxPrice());
            json.put("FullMortgage", vm_search.isFullMortgage());
            json.put("MinMortgage", vm_search.getMinMortgage());
            json.put("MaxMortgage", vm_search.getMaxMortgage());
            json.put("MinArea", vm_search.getMinArea());
            json.put("MaxArea", vm_search.getMaxArea());
            json.put("Room", vm_search.getCountRoom());
            json.put("Page", vm_search.getPage());
            json.put("Old_New", vm_search.isOld_New());
            json.put("New_old", vm_search.isNew_old());
            json.put("Expensiv_Cheap", vm_search.isExpensiv_Cheap());
            json.put("Cheap_Expensiv", vm_search.isCheap_Expensiv());
            json.put("Room_l_h", vm_search.isRoom_l_h());
            json.put("Room_h_l", vm_search.isRoom_h_l());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        array.put(json);

        if (vm_search.getPage() == 0) {
            //در اینجا تمامی آیتم ها در TblLastSearch حذف می شوند
            Cursor RemoveAllItemInLastSearch = dbAdapter.ExecuteQ("delete from TblLastSearchSave");
            RemoveAllItemInLastSearch.close();
        }

        String url = Api + "Item/Searchitem";

         jsonObjReq = new JsonArrayRequest(Request.Method.POST, url, array
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<List_Home> resault_searches = new ArrayList<>();

                if (response.length() > 0) {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject j = response.getJSONObject(i);
                            List_Home res = new List_Home();

                            res.setAddress(j.getString("Address"));
                            res.setPrice(j.getString("Price"));
                            res.setImage(ApiImage + "ImageSave/" + j.getString("Image"));
                            res.setDiscription(j.getString("Title"));
                            res.setTime(j.getString("DateInsert"));
                            res.setId(Integer.parseInt(j.getString("Id")));
                            res.setArea(j.getString("Area"));
                            res.setCountRoom(j.getString("Room"));
                            res.setCountImage(j.getString("ImageCount"));
                            res.setSpecial(Boolean.valueOf(j.getString("Special")));

                            int Special = 0;

                            if (res.isSpecial())
                                Special = 1;

                            //در اینجا آیتم ها در TblLastSearchSave ذخیره می شوند
                            Cursor AddToLastSearch = dbAdapter.ExecuteQ("insert into TblLastSearchSave (Id,Title,Price,Area,Room,Age,Code,Address,Description,DateInsert,Special,Image,CountImage) values (" + res.getId() + ",'"+res.getDiscription()+"','" + res.getPrice() + "'," + res.getArea() + "," + res.getCountRoom() + ",1398,'Code','" + res.getAddress() + "','" + res.getDiscription() + "','" + res.getTime() + "'," + Special + ",'" + j.getString("Image") + "',"+res.getCountImage()+")");
                            AddToLastSearch.close();

                            if (CountItem == -1) {
                                CountItem = Integer.parseInt(j.getString("Count"));
                            }
                            resault_searches.add(res);


                        } catch (JSONException e) {

                        }
                    }
                }else{
                    img_not_item.setVisibility(View.VISIBLE);
                }

                if (CountItem != -1) {
                    txt_CountItem.setText(CountItem + " آگهی");
                } else {
                    txt_CountItem.setText(0 + " آگهی");
                }

                if (vm_search.getPage() == 0) {

                    listHomeAdapter = new ListHomeAdapter(resault_searches, getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(listHomeAdapter);
                } else {
                    listHomeAdapter.Add(resault_searches);
                    loading = true;
                }

                if (listHomeAdapter.getItemCount() == 0){
                    img_not_item.setVisibility(View.VISIBLE);
                }else {
                    img_not_item.setVisibility(View.GONE);
                }
                progressbar.setVisibility(View.GONE);
                Save_Search.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.setVisibility(View.GONE);
                try {
                    new AlertDialog.Builder(getContext())
                            .setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>")))
                            .setMessage((Html.fromHtml("<font color='#FF7F27'>اینترنت شما بسیار ضعیف است لطفا مجددا امتحان کنید</font>")))
                            .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>بارگیری مجدد</font>")), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("Type", 1);
                                    Fragment_Resault_Search fragment_resault_search = new Fragment_Resault_Search();
                                    fragment_resault_search.setArguments(bundle);

                                    ((AppCompatActivity) getActivity()).getSupportFragmentManager().
                                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, fragment_resault_search).commit();

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception ex) {

                }
            }
        });
        AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley,jsonObjReq));


    }

    void Scroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)&&dy>0){
                    if (loading){
                        if (listHomeAdapter.getItemCount() < CountItem) {
                            loading = false;
                            int page = vm_search.getPage() + 1;
                            vm_search.setPage(page);
                            GetHomes();
                        }
                    }
                }
            }
        });
    }

    //در این متد تمامی فیلترها در vm_search پاک خواهند شد
    void ClearFilters() {
        vm_search.setOld_New(false);
        vm_search.setNew_old(false);
        vm_search.setExpensiv_Cheap(false);
        vm_search.setCheap_Expensiv(false);
        vm_search.setRoom_l_h(false);
        vm_search.setRoom_h_l(false);
    }

    //در اینجا یک عدد خواهد گرفت و بر روی آن عدد فیلتر مورد نظر را در vm_Search برابر true قرار خواهد داد
    void SetFilter(int Type) {
        ClearFilters();

        switch (Type) {
            case 2:
                vm_search.setNew_old(true);
                break;
            case 3:
                vm_search.setOld_New(true);
                break;
            case 4:
                vm_search.setExpensiv_Cheap(true);
                break;
            case 5:
                vm_search.setCheap_Expensiv(true);
                break;
            case 6:
                vm_search.setRoom_l_h(true);
                break;
            case 7:
                vm_search.setRoom_h_l(true);
                break;
        }
    }
}
