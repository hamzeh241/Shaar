package ir.tdaapp.diako.shaar.FragmentPage;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import ir.tdaapp.diako.shaar.Adapter.ViewPager_Tab_Moshaverin;

/**
 * Created by Diako on 5/25/2019.
 */

public class Fragment_Seting_Moshaverin extends Fragment {

    public static final String TAG="Fragment_Seting_Moshaverin";

    ViewPager viewPager;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_seting_moshaverin,container,false);


        FindItem(view);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }



    public void FindItem(View view){
        viewPager=view.findViewById(ir.tdaapp.diako.shaar.R.id.ViewPager);
        tabLayout=view.findViewById(ir.tdaapp.diako.shaar.R.id.tab_layout);
    }

    private void setViewPager(ViewPager viewPager){
        ViewPager_Tab_Moshaverin adapter=new ViewPager_Tab_Moshaverin(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        adapter.addFragment(new Tab_Record_Moshaverin(),"ثبت");
        adapter.addFragment(new Tab_Edit_Moshaverin(),"ویرایش");
        viewPager.setAdapter(adapter);
    }
}
