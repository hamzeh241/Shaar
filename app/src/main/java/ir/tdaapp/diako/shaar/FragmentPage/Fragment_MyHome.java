package ir.tdaapp.diako.shaar.FragmentPage;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import ir.tdaapp.diako.shaar.Adapter.ViewPager_Tab_Moshaverin;

/**
 * Created by Diako on 7/7/2019.
 */

public class Fragment_MyHome extends Fragment{

    public static final String TAG="Fragment_MyHome";

    ViewPager viewPager;
    TabLayout tabLayout;
    RelativeLayout backall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_myhome,container,false);


        FindItem(view);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        Onclick();

        return view;
    }

    public void FindItem(View view){
        viewPager=view.findViewById(ir.tdaapp.diako.shaar.R.id.ViewPager);
        tabLayout=view.findViewById(ir.tdaapp.diako.shaar.R.id.tab_layout);
        backall=view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
    }

    private void setViewPager(ViewPager viewPager){
        ViewPager_Tab_Moshaverin adapter=new ViewPager_Tab_Moshaverin(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        adapter.addFragment(new Tab_Published_Home(),getResources().getString(ir.tdaapp.diako.shaar.R.string.Published));
        adapter.addFragment(new Tab_Draft_Home(),getResources().getString(ir.tdaapp.diako.shaar.R.string.Draft));
        viewPager.setAdapter(adapter);
    }

    void Onclick(){
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });
    }
}
