package ir.tdaapp.diako.shaar.FragmentPage;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.Record_Moshaverin;
import ir.tdaapp.diako.shaar.Model.List_Moshaverin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/25/2019.
 */

public class Tab_Record_Moshaverin extends Fragment {

    public static final String TAG="Tab_Record_Moshaverin";
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.tab_record_moshaverin, container, false);

        SetMoshaverin(view);

        return view;
    }

    private List<List_Moshaverin>SetValue(){
        List<List_Moshaverin>lst=new ArrayList<>();

        lst.add(new List_Moshaverin("دیاکو حسنی",1,true));
        lst.add(new List_Moshaverin("حمزه باتمانی",1,true));
        lst.add(new List_Moshaverin("محمد محمدی",1,true));
        lst.add(new List_Moshaverin("مظفر مظفری",0,true));
        lst.add(new List_Moshaverin("سینا مرادی",1,true));
        lst.add(new List_Moshaverin("حسین حسن",1,true));
        return lst;
    }

    private void SetMoshaverin(View view){
        recyclerView=view.findViewById(ir.tdaapp.diako.shaar.R.id.RecordMoshaverin_Recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Record_Moshaverin record_moshaverin=new Record_Moshaverin(getActivity(),SetValue());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(record_moshaverin);
    }
}
