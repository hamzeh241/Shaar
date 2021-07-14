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
import ir.tdaapp.diako.shaar.Adapter.List_SearchAdapter;
import ir.tdaapp.diako.shaar.ViewModel.VM_List_Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/30/2019.
 */

public class Fragment_List_Search extends Fragment {
    public static final String TAG="Fragment_List_Search";

    RelativeLayout backall;
    DBAdapter dbAdapter;
    List<VM_List_Search> searches;
    List_SearchAdapter searchAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recycler;
    TextView CountItem;
    LinearLayout img_not_item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_list_search,container,false);

        Find(view);
        OnClick();
        SetData();

        return view;
    }

    void Find(View view){
        backall=view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
        dbAdapter=new DBAdapter(getContext());
        searches=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycler=view.findViewById(ir.tdaapp.diako.shaar.R.id.recycler);
        CountItem=view.findViewById(ir.tdaapp.diako.shaar.R.id.CountItem);
        img_not_item=view.findViewById(ir.tdaapp.diako.shaar.R.id.img_not_item);
    }

    void OnClick(){
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });
    }

    void SetData(){

        Cursor cursor=dbAdapter.executeQuery("select * from TblListSearch");

        CountItem.setText("تعداد آگهی: "+cursor.getCount());
        if (cursor!=null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                searches.add(new VM_List_Search(Integer.parseInt(cursor.getString(0)),cursor.getString(1)));
                cursor.moveToNext();
            }
        }else{
            img_not_item.setVisibility(View.VISIBLE);
        }
        cursor.close();
        dbAdapter.close();

        searchAdapter=new List_SearchAdapter(getContext(),searches);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(searchAdapter);
    }
}
