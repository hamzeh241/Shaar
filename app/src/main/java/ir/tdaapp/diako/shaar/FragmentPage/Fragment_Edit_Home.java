package ir.tdaapp.diako.shaar.FragmentPage;

import android.graphics.PorterDuff;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.TypeHomeAdapter;
import ir.tdaapp.diako.shaar.Model.Type_Home;
import ir.tdaapp.diako.shaar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/31/2019.
 */

public class Fragment_Edit_Home extends Fragment {

    RadioButton rdo_Sell;
    EditText txt_Area, txt_AdTitle, txt_Discription, txt_Price, txt_TheArea, txt_Mobile, txt_Phone, txt_VideoLink;
    TextView lbl_Location, lbl_Property_details, lbl_Price, lbl_TheArea, lbl_DiscriptionCall, lbl_VideoLink,Back_Text;
    Spinner Spinner_VideoLink;
    RecyclerView recyclerView;
    ImageView Back;
    Button AfterToSave;
    DBAdapter dbAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_edit_home, container, false);

        FindItem(view);
        OnClick();
        SetTypeHome(view);

        return view;
    }

    void FindItem(View view) {
        dbAdapter=new DBAdapter(getContext());
        rdo_Sell = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_RadioButton_Sell);
        txt_Area = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_Area);
        txt_AdTitle = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_AdTitle);
        txt_Discription = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_DiscriptionHome);
        txt_Price = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_Price);
        txt_TheArea = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_TheArea);
        txt_Mobile = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_Mob);
        txt_Phone = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_Phone);
        txt_VideoLink = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_txt_VideoLink);
        Spinner_VideoLink = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_Spinner_VideoLink);

        lbl_Location = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_lbl_Location);
        lbl_Property_details = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_lbl_Property_details);
        lbl_Price = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_lbl_Price);
        lbl_TheArea = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_lbl_TheArea);
        lbl_DiscriptionCall = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_lbl_DiscriptionCall);
        lbl_VideoLink = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Edit_Home_lbl_AddVideo);
        Back=view.findViewById(ir.tdaapp.diako.shaar.R.id.BackIcon);
        Back_Text=view.findViewById(ir.tdaapp.diako.shaar.R.id.Back_Text);
        AfterToSave=view.findViewById(ir.tdaapp.diako.shaar.R.id.AfterToSave);

        SetBackGroundTientEditText(txt_Area, lbl_Location);
        SetBackGroundTientEditText(txt_AdTitle, lbl_Property_details);
        SetBackGroundTientEditText(txt_Discription, lbl_Property_details);
        SetBackGroundTientEditText(txt_Price, lbl_Price);
        SetBackGroundTientEditText(txt_TheArea, lbl_TheArea);
        SetBackGroundTientEditText(txt_Mobile, lbl_DiscriptionCall);
        SetBackGroundTientEditText(txt_Phone, lbl_DiscriptionCall);
        SetBackGroundTientEditText(txt_VideoLink, lbl_VideoLink);
    }

    void SetBackGroundTientEditText(final EditText txt, final TextView lbl) {
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    txt.getBackground().setColorFilter(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorCenteral), PorterDuff.Mode.SRC_ATOP);
                    lbl.setTextColor(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorCenteral));
                } else {
                    txt.getBackground().setColorFilter(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorPaleBlack), PorterDuff.Mode.SRC_ATOP);
                    lbl.setTextColor(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                }
            }
        });
    }

    public void SetTypeHome(View view) {
        recyclerView = view.findViewById(R.id.Fragment_Edit_Home_Recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        TypeHomeAdapter typeHomeAdapter = new TypeHomeAdapter(getActivity(), SetDataTypeHome(),view,dbAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(typeHomeAdapter);
    }

    public List<Type_Home> SetDataTypeHome() {
        List<Type_Home> lst = new ArrayList<>();
        lst.add(new Type_Home("آپارتمان",1));
        lst.add(new Type_Home("ویلا",1));
        lst.add(new Type_Home("زمین",1));
        lst.add(new Type_Home("ویلایی",1));
        lst.add(new Type_Home("پنت هاوس",1));
        return lst;
    }

    void OnClick(){
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        Back_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        AfterToSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });
    }
}
