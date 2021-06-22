package ir.tdaapp.diako.shaar.FragmentPage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 8/18/2019.
 */

public class Dialog_Search_By_Id extends DialogFragment {

    public static final String TAG="Dialog_Search_By_Id";

    Internet internet;

    Button btn_Search;
    EditText txt_Id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_search_by_id,container,false);

        txt_Id=view.findViewById(R.id.txt_Id);
        btn_Search=view.findViewById(R.id.btn_Search);
        internet=new Internet(getContext());

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (internet.HaveNetworkConnection()){
                        Bundle bundle=new Bundle();
                        bundle.putInt("Id",Integer.parseInt(txt_Id.getText().toString()));
//                        Stack_Back.MyStack_Back.Push("Fragment_Show_Details_Home",getActivity(),bundle);
                        Fragment_Show_Details_Home fragment_show_details_home=new Fragment_Show_Details_Home();
                        fragment_show_details_home.setArguments(bundle);
                        ((MainActivity)getActivity()).onAddFragment(fragment_show_details_home,0,0,true,Fragment_Show_Details_Home.TAG);

                        getDialog().dismiss();

                    }else{
                        Toast.makeText(getContext(), "لطفا اتصال خود را به اینترنت چک نمایید", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }
}
