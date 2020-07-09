package ir.tdaapp.diako.shaar.FragmentPage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import ir.tdaapp.diako.shaar.ETC.Stack_Back;

/**
 * Created by Diako on 7/31/2019.
 */

public class Fragment_About_Me extends Fragment {

    RelativeLayout backall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_about_me,container,false);




        FindItem(view);
        OnClick();

        return view;
    }

    void FindItem(View view){
        backall=view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
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
