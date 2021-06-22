package ir.tdaapp.diako.shaar.FragmentPage;

import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.ListHomeAdapter;
import ir.tdaapp.diako.shaar.Model.List_Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/30/2019.
 */

public class Fragment_Child_Search_List extends Fragment {

    public static final String TAG="Fragment_Child_Search_List";

    RelativeLayout backall;
    TextView CountItem;
    RecyclerView recycler;
    LinearLayout img_not_item;
    DBAdapter dbAdapter;
    ListHomeAdapter homeAdapter;
    LinearLayoutManager linearLayoutManager;
    List<List_Home>homes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_child_search_list,container,false);

        Find(view);

        OnClick();

        SetData();

        return view;
    }

    void Find(View view){
        backall=view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
        CountItem=view.findViewById(ir.tdaapp.diako.shaar.R.id.CountItem);
        recycler=view.findViewById(ir.tdaapp.diako.shaar.R.id.recycler);
        img_not_item=view.findViewById(ir.tdaapp.diako.shaar.R.id.img_not_item);
        dbAdapter=new DBAdapter(getContext());
        homes=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
    }

    void OnClick(){
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getContext());
                getActivity().onBackPressed();
            }
        });
    }

    void SetData(){

        Bundle bundle = getArguments();
        int Type = 0;

        if (bundle != null)
            Type = bundle.getInt("Id");

        if (Type!=0){

            Cursor cursor=dbAdapter.ExecuteQ("select * from TblSearch where FkListSearch="+Type);

            CountItem.setText("تعداد آگهی: "+cursor.getCount());

            if (cursor!=null && cursor.moveToFirst()){
                while (!cursor.isAfterLast()){

                    List_Home home=new List_Home();
                    home.setId(Integer.parseInt(cursor.getString(0)));
                    home.setDiscription(cursor.getString(1));
                    home.setPrice(cursor.getString(2));
                    home.setArea(cursor.getString(3));
                    home.setCountRoom(cursor.getString(5));
                    home.setTime(cursor.getString(12));
                    home.setAddress(cursor.getString(8));
                    home.setImage(cursor.getString(13));
                    home.setSpecial(Boolean.valueOf(cursor.getString(14)));
                    homes.add(home);
                    cursor.moveToNext();
                }
                homeAdapter=new ListHomeAdapter(homes,getContext());
                recycler.setLayoutManager(linearLayoutManager);
                recycler.setAdapter(homeAdapter);
            }else{
                img_not_item.setVisibility(View.VISIBLE);
            }

        }else{
            img_not_item.setVisibility(View.VISIBLE);
        }
    }
}
