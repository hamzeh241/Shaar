package ir.tdaapp.diako.shaar.FragmentPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Diako on 7/31/2019.
 */

public class Fragment_Call_Me extends Fragment {

    public static final String TAG="Fragment_Call_Me";

    RelativeLayout backall;
    Button btn_call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_call_me,container,false);

        FindItem(view);
        OnClick();

        return view;
    }

    void FindItem(View view){
        backall=view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
        btn_call=view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_call);
    }

    void OnClick(){
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "۰۹۱۸۳۷۱۴۹۱۷"));
                startActivity(intent);
            }
        });
    }
}
