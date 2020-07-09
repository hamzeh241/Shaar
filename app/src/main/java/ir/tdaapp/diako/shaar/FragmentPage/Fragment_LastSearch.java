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
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.Model.List_Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/22/2019.
 */

public class Fragment_LastSearch extends Fragment implements IBase {

    RecyclerView recycler;
    DBAdapter dbAdapter;
    ListHomeAdapter listHomeAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView lbl_CountItem;
    LinearLayout img_not_item;
    RelativeLayout backall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_lastsearch,container,false);

        FindItem(view);

        SetDataRecycle();

        OnClick();

        return view;
    }

    void FindItem(View view){
        recycler=view.findViewById(ir.tdaapp.diako.shaar.R.id.recycler);
        dbAdapter=new DBAdapter(getContext());
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lbl_CountItem=view.findViewById(ir.tdaapp.diako.shaar.R.id.CountItem);
        img_not_item=view.findViewById(ir.tdaapp.diako.shaar.R.id.img_not_item);
        backall=view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
    }

    void SetDataRecycle(){

        Cursor GetAll=dbAdapter.ExecuteQ("select * from TblLastSearchSave");

        lbl_CountItem.setText("تعداد اگهی: "+String.valueOf(GetAll.getCount()));

        List<List_Home> homes=new ArrayList<>();

        if (GetAll!=null && GetAll.moveToFirst()){
            while (!GetAll.isAfterLast()){

                List_Home home=new List_Home();

                home.setId(Integer.parseInt(GetAll.getString(0)));
                home.setPrice(GetAll.getString(2));
                home.setArea(GetAll.getString(3));
                home.setCountRoom(GetAll.getString(4));
                home.setAddress(GetAll.getString(7));
                home.setDiscription(GetAll.getString(8));
                home.setTime(GetAll.getString(9));
                home.setSpecial(Boolean.valueOf(GetAll.getString(10)));
                home.setImage(ApiImage+"ImageSave/"+GetAll.getString(11));
                home.setCountImage(GetAll.getString(13));
                homes.add(home);
                GetAll.moveToNext();
            }
        }else{
            img_not_item.setVisibility(View.VISIBLE);
        }
        GetAll.close();
        dbAdapter.close();

        listHomeAdapter=new ListHomeAdapter(homes,getContext());
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(listHomeAdapter);
    }

    void OnClick(){
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stack_Back.MyStack_Back.Pop(getActivity());
            }
        });
    }
}
