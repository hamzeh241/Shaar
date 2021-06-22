package ir.tdaapp.diako.shaar.FragmentPage;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ir.tdaapp.diako.shaar.MainActivity;

/**
 * Created by Diako on 7/23/2019.
 */

public class Fragment_Dialog_Filter_Search extends DialogFragment {

    public static final String TAG="Fragment_Dialog_Filter_Search";

    RelativeLayout close;
    RadioButton rdo_New_old,rdo_Old_New,rdo_Expensiv_Cheap,rdo_Cheap_Expensiv,rdo_Room_l_h,rdo_Room_h_l;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_dialog_filter_search,container,false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        FindItem(view);

        OnClick();

        return view;
    }

    void FindItem(View view){
        close=view.findViewById(ir.tdaapp.diako.shaar.R.id.close);
        rdo_New_old=view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_New_old);
        rdo_Old_New=view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Old_New);
        rdo_Expensiv_Cheap=view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Expensiv_Cheap);
        rdo_Cheap_Expensiv=view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Cheap_Expensiv);
        rdo_Room_l_h=view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Room_l_h);
        rdo_Room_h_l=view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Room_h_l);
    }

    void OnClick(){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

        rdo_New_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 2);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search2", getContext(), bundle);
                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
                fragment_resault_search.setArguments(bundle);
                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,true,Fragment_Resault_Search.TAG);
                getDialog().dismiss();
            }
        });

        rdo_Old_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 3);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search2", getContext(), bundle);
                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
                fragment_resault_search.setArguments(bundle);
                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,false,Fragment_Resault_Search.TAG);
                getDialog().dismiss();
            }
        });

        rdo_Expensiv_Cheap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 4);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search2", getContext(), bundle);
                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
                fragment_resault_search.setArguments(bundle);
                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,false,Fragment_Resault_Search.TAG);
                getDialog().dismiss();
            }
        });

        rdo_Cheap_Expensiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 5);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search2", getContext(), bundle);
                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
                fragment_resault_search.setArguments(bundle);
                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,false,Fragment_Resault_Search.TAG);
                getDialog().dismiss();
            }
        });

        rdo_Room_l_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 6);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search2", getContext(), bundle);
                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
                fragment_resault_search.setArguments(bundle);
                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,false,Fragment_Resault_Search.TAG);
                getDialog().dismiss();
            }
        });

        rdo_Room_h_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 7);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search2", getContext(), bundle);
                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
                fragment_resault_search.setArguments(bundle);
                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,false,Fragment_Resault_Search.TAG);
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
