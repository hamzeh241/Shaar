package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.ETC.Validation;
import ir.tdaapp.diako.shaar.Model.List_Home;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Diako on 7/29/2019.
 */

public class Fragment_Save_Search extends Fragment {

    public static final String TAG="Fragment_Save_Search";

    Button btn_Done;
    Validation validation;

    EditText txt_TitleSave;
    RelativeLayout backall;
    DBAdapter dbAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_save_search, container, false);

//        ((MainActivity) getActivity()).frameLayout1.setVisibility(View.GONE);
        FindItem(view);

        OnClick();

        return view;
    }

    void FindItem(View view) {
        btn_Done = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Done);
        txt_TitleSave = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_TitleSave);
        backall = view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
        dbAdapter = new DBAdapter(getContext());
        validation = new Validation();
    }

    void OnClick() {
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_Done.setEnabled(false);

                if (!validation.Required(txt_TitleSave, getResources().getString(ir.tdaapp.diako.shaar.R.string.EmptyTitle))) {

                    final ProgressDialog progress = new ProgressDialog(getActivity());
                    progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
                    progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();

                    //در اینجا بزرگترین id رو به دست می آوریم
                    Cursor GetIdItem = dbAdapter.ExecuteQ("select MAX(Id) from TblListSearch");
                    int Id;
                    if (GetIdItem.getString(0) != null)
                        Id = Integer.parseInt(GetIdItem.getString(0)) + 1;
                    else
                        Id = 1;
                    GetIdItem.close();

                    //در اینجا سردسته در دیتابیس ذخیره می شود
                    Date currentTime = Calendar.getInstance().getTime();
                    Cursor cursor = dbAdapter.ExecuteQ("insert into TblListSearch (Id,Title,DateCreate) values (" + Id + ",'" + txt_TitleSave.getText().toString() + "','" + currentTime.toString() + "')");

                    Cursor GetListSearch = dbAdapter.ExecuteQ("select * from TblLastSearchSave");

                    List<List_Home> homes = new ArrayList<>();
                    if (GetListSearch != null && GetListSearch.moveToFirst()) {
                        while (!GetListSearch.isAfterLast()) {

                            List_Home home = new List_Home();

                            home.setId(Integer.parseInt(GetListSearch.getString(0)));
                            home.setDiscription(GetListSearch.getString(1));
                            home.setImage(GetListSearch.getString(11));
                            home.setTime(GetListSearch.getString(9));
                            home.setAddress(GetListSearch.getString(7));
                            home.setCountRoom(GetListSearch.getString(4));
                            home.setArea(GetListSearch.getString(3));
                            home.setPrice(GetListSearch.getString(2));
                            home.setSpecial(Boolean.valueOf(GetListSearch.getString(10)));
                            home.setCountImage("13");
                            home.setFavorit(Boolean.valueOf(GetListSearch.getString(12)));
                            homes.add(home);
                            GetListSearch.moveToNext();
                        }
                    }

                    for (int i = 0; i < homes.size(); i++) {
                        int Special = 0;
                        if (Boolean.valueOf(homes.get(i).isSpecial()))
                            Special = 1;
                        Cursor AddToTblSave = dbAdapter.ExecuteQ("insert into TblSearch (Id,Title,Price,Area,Room,Age,Address,IsFavorit,FkLocation,DateInsert,Image,Special,FkListSearch) values (" + homes.get(i).getId() + ",'" + homes.get(i).getDiscription() + "','" + homes.get(i).getPrice() + "'," + Integer.parseInt(homes.get(i).getArea()) + "," + Integer.parseInt(homes.get(i).getCountRoom()) + ",0,'" + homes.get(i).getAddress() + "',0,0,'" + homes.get(i).getTime() + "','" + homes.get(i).getImage() + "'," + Special + "," + Id + ")");
                        AddToTblSave.close();
                    }

                    cursor.close();
                    dbAdapter.close();

                    progress.dismiss();

                    Toast.makeText(getActivity(), "عملیات با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }
                btn_Done.setEnabled(true);
            }
        });
    }
}
