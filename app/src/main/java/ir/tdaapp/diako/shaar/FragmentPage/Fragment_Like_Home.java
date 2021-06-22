package ir.tdaapp.diako.shaar.FragmentPage;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.ListHomeAdapter;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.Model.List_Home;
import ir.tdaapp.diako.shaar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/29/2019.
 */

public class Fragment_Like_Home extends Fragment {

    public static final String TAG="Fragment_Like_Home";

    RecyclerView recycler;
    DBAdapter dbAdapter;
    ListHomeAdapter listHomeAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView lbl_CountItem;
    LinearLayout img_not_item;
    RelativeLayout BackAll;

    LinearLayout linearLayoutNotLogIn;

    int userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_home, container, false);
        FindItem(view);
        OnClick();
        SetDataRecycle();
        return view;
    }

    void FindItem(View view) {
        recycler=view.findViewById(R.id.recycler);
        dbAdapter=new DBAdapter(getContext());
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lbl_CountItem=view.findViewById(R.id.CountItem);
        img_not_item=view.findViewById(R.id.img_not_item);
        BackAll=view.findViewById(R.id.backall);
        userId = new User(getContext()).GetUserId();
        linearLayoutNotLogIn = view.findViewById(R.id.no_item_to_show_home);

    }

    void OnClick(){
        BackAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });
    }

    void SetDataRecycle(){

        Cursor GetAll=dbAdapter.ExecuteQ("select * from TblSearch where IsFavorit=1");

        lbl_CountItem.setText("تعداد اگهی: "+ GetAll.getCount());

        List<List_Home> homes=new ArrayList<>();

        if (GetAll!=null && GetAll.moveToFirst()){
            while (!GetAll.isAfterLast()){

                List_Home home=new List_Home();

                home.setId(Integer.parseInt(GetAll.getString(0)));
                home.setPrice(GetAll.getString(2));
                home.setArea(GetAll.getString(3));
                home.setCountRoom(GetAll.getString(5));
                home.setAddress(GetAll.getString(8));
                home.setDiscription(GetAll.getString(1));
                home.setTime(GetAll.getString(12));
                home.setSpecial(Boolean.valueOf(GetAll.getString(14)));
                home.setImage(GetAll.getString(13));
                homes.add(home);
                GetAll.moveToNext();
            }
        }else if (userId == 0){
            img_not_item.setVisibility(View.GONE);
            linearLayoutNotLogIn.setVisibility(View.VISIBLE);
        }else {
            img_not_item.setVisibility(View.VISIBLE);
        }
        GetAll.close();
        dbAdapter.close();

        listHomeAdapter=new ListHomeAdapter(homes,getContext());
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(listHomeAdapter);
    }
}
