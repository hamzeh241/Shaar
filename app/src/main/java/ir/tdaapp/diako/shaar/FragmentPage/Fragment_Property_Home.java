package ir.tdaapp.diako.shaar.FragmentPage;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Data.DA_Add_Home;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Boolean;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Number;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Select;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Text;
import ir.tdaapp.diako.shaar.Model.Id_FeaturesGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/26/2019.
 */

public class Fragment_Property_Home extends Fragment {
    public static final String TAG="Fragment_Property_Home";

    RelativeLayout Header_Possibilities, Header_Rooms, Header_Commerce, Header_Health, Header_Nearby, Header_Other, Header_Security, Header_Amazing;

    LinearLayout Child_Possibilities, Child_Rooms, Child_Commerce, Child_Health, Child_Nearby,
            Child_Other, Child_Security, Child_Amazing;
    ImageView img_Icon_Possibilities, img_Icon_Rooms, img_Icon_Comprece, img_Icon_Health, img_Icon_Nearby,
            img_Icon_Other, img_Icon_Security, img_Icon_Amazing, Back;

    Button btn_Done;
    DBAdapter dbAdapter;
    TextView Property_Title;

    LinearLayout p1, p11, p12, p13, p15, p37, p40, p44, p45, p46, p47, p48, p49, p50, p51, p84, p85, p86, p87, p91, p92, p93, p25, p28, p29, p30, p31, p32, p33;
    CheckBox p2, p3, p4, p5, p6, p7, p8, p9, p14, p16, p17, p24, p52, p53, p54, p55, p56, p57, p58, p81, p82, p83, p88, p89, p90, p19, p20, p26, p38, p39, p21, p22, p23, p10, p18, p27, p34, p35, p36, p59, p60, p61, p64, p65, p66, p67, p68, p69, p70, p71, p72, p73, p74, p75, p76, p77, p78, p79;
    CheckBox p41, p42, p43, p62, p63, p80, p94;
    Spinner cmb_p13, cmb_p44, cmb_p45, cmb_p46, cmb_p47, cmb_p48, cmb_p50, cmb_p51;

    EditText txt_p1, txt_p11, txt_p12, txt_p15, txt_p37, txt_p40, txt_p49, txt_p84, txt_p85, txt_p86, txt_p87, txt_p91, txt_p92, txt_p93, txt_p25, txt_p28, txt_p29, txt_p30, txt_p31, txt_p32, txt_p33;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_property_home, container, false);

//        ((MainActivity)getActivity()).frameLayout1.setVisibility(View.GONE);


        Find(view);
        OnClick();
        SettingListGroup(view);

        SetShowProperty(view);
        SetDataSpinner();
        SetValueToElement();
        Property_Title.setText("ویژگی ها (" + DA_Add_Home.TitleProperty + ")");

        return view;
    }

    void Find(View view) {

        Header_Possibilities = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Possibilities);
        Child_Possibilities = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Possibilities);
        img_Icon_Possibilities = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Possibilities);
        Header_Rooms = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Rooms);
        Child_Rooms = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Rooms);
        img_Icon_Rooms = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Rooms);
        Header_Commerce = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Commerce);
        Child_Commerce = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Commerce);
        img_Icon_Comprece = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Commerce);
        Header_Health = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Health);
        Child_Health = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Health);
        img_Icon_Health = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Health);
        Header_Nearby = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Nearby);
        Child_Nearby = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Nearby);
        img_Icon_Nearby = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Nearby);
        Header_Other = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Other);
        Child_Other = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Other);
        img_Icon_Other = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Other);
        Header_Security = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Security);
        Child_Security = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Security);
        img_Icon_Security = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Security);
        Header_Amazing = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Amazing);
        Child_Amazing = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Child_Amazing);
        img_Icon_Amazing = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_PropertyHome_Icon_Amazing);
        btn_Done = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Done);
        Back = view.findViewById(ir.tdaapp.diako.shaar.R.id.Back);
        Property_Title = view.findViewById(ir.tdaapp.diako.shaar.R.id.Property_Home_Title);

        dbAdapter = new DBAdapter(getActivity());
    }

    // در اینجا داده ها درون اسپینر set می شوند
    void SetDataSpinner() {

        Cursor cursor = dbAdapter.ExecuteQ("select * from TblFeatures where TblFeatures.FkFormat=4");

        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {

                String c = cursor.getString(2);
                String[] DataArray = c.split(",");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, DataArray);
                adapter.setDropDownViewResource(ir.tdaapp.diako.shaar.R.layout.property_spinner);

                if (cmb_p13 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 13) {
                        cmb_p13.setAdapter(adapter);
                    }
                }

                if (cmb_p44 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 44) {
                        cmb_p44.setAdapter(adapter);
                    }
                }

                if (cmb_p45 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 45) {
                        cmb_p45.setAdapter(adapter);
                    }
                }

                if (cmb_p46 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 46) {
                        cmb_p46.setAdapter(adapter);
                    }
                }

                if (cmb_p47 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 47) {
                        cmb_p47.setAdapter(adapter);
                    }
                }

                if (cmb_p48 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 48) {
                        cmb_p48.setAdapter(adapter);
                    }
                }

                if (cmb_p50 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 50) {
                        cmb_p50.setAdapter(adapter);
                    }
                }

                if (cmb_p51 != null) {
                    if (Integer.parseInt(cursor.getString(0)) == 51) {
                        cmb_p51.setAdapter(adapter);
                    }
                }
                cursor.moveToNext();
            }
        }
    }

    void OnClick() {
        Header_Possibilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Possibilities.getVisibility() == View.VISIBLE) {
                    Child_Possibilities.setVisibility(View.GONE);
                    img_Icon_Possibilities.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Possibilities.setVisibility(View.VISIBLE);
                    img_Icon_Possibilities.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Header_Rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Rooms.getVisibility() == View.VISIBLE) {
                    Child_Rooms.setVisibility(View.GONE);
                    img_Icon_Rooms.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Rooms.setVisibility(View.VISIBLE);
                    img_Icon_Rooms.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });
        Header_Commerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Commerce.getVisibility() == View.VISIBLE) {
                    Child_Commerce.setVisibility(View.GONE);
                    img_Icon_Comprece.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Commerce.setVisibility(View.VISIBLE);
                    img_Icon_Comprece.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Header_Health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Health.getVisibility() == View.VISIBLE) {
                    Child_Health.setVisibility(View.GONE);
                    img_Icon_Health.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Health.setVisibility(View.VISIBLE);
                    img_Icon_Health.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Header_Nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Nearby.getVisibility() == View.VISIBLE) {
                    Child_Nearby.setVisibility(View.GONE);
                    img_Icon_Nearby.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Nearby.setVisibility(View.VISIBLE);
                    img_Icon_Nearby.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Header_Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Other.getVisibility() == View.VISIBLE) {
                    Child_Other.setVisibility(View.GONE);
                    img_Icon_Other.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Other.setVisibility(View.VISIBLE);
                    img_Icon_Other.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Header_Security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Security.getVisibility() == View.VISIBLE) {
                    Child_Security.setVisibility(View.GONE);
                    img_Icon_Security.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Security.setVisibility(View.VISIBLE);
                    img_Icon_Security.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Header_Amazing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Child_Amazing.getVisibility() == View.VISIBLE) {
                    Child_Amazing.setVisibility(View.GONE);
                    img_Icon_Amazing.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
                } else {
                    Child_Amazing.setVisibility(View.VISIBLE);
                    img_Icon_Amazing.setImageResource(ir.tdaapp.diako.shaar.R.drawable.down);
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetData();
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

    }

    void SettingListGroup(View view) {
        Child_Amazing.setVisibility(View.GONE);
        Child_Security.setVisibility(View.GONE);
        Child_Other.setVisibility(View.GONE);
        Child_Nearby.setVisibility(View.GONE);
        Child_Health.setVisibility(View.GONE);
        Child_Commerce.setVisibility(View.GONE);
        Child_Rooms.setVisibility(View.GONE);
        Child_Possibilities.setVisibility(View.GONE);

        img_Icon_Amazing.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Security.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Other.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Nearby.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Comprece.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Rooms.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Possibilities.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
        img_Icon_Health.setImageResource(ir.tdaapp.diako.shaar.R.drawable.top);
    }

    //در ابنجا ویژگی های مربوطه نمایش و بقیه مخفی می شوند
    void SetShowProperty(View view) {
        Cursor cursor = dbAdapter.ExecuteQ("SELECT TblFeatures.Id,TblFeatures.FkFeaturesGroup FROM TblTypeFeatures INNER JOIN TblFeatures on TblTypeFeatures.FkFeatures=TblFeatures.Id WHERE TblTypeFeatures.FkType =" + DA_Add_Home.TypeHome);

        List<Id_FeaturesGroup> id_featuresGroups = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                id_featuresGroups.add(new Id_FeaturesGroup(Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(0))));
                cursor.moveToNext();
            }
        }

        //در اینجا ابتدا هدرها برای نمایش چک می شوند
        boolean Group_Possibilities = false, Group_Rooms = false, Group_Commerce = false, Group_Health = false;
        boolean Group_Nearby = false, Group_Other = false, Group_Security = false, Group_Amazing = false;

        for (int i = 0; i < id_featuresGroups.size(); i++) {

            if (id_featuresGroups.get(i).getGroupId() == 1)
                Group_Possibilities = true;

            if (id_featuresGroups.get(i).getGroupId() == 2)
                Group_Rooms = true;

            if (id_featuresGroups.get(i).getGroupId() == 3)
                Group_Commerce = true;

            if (id_featuresGroups.get(i).getGroupId() == 4)
                Group_Health = true;

            if (id_featuresGroups.get(i).getGroupId() == 5)
                Group_Nearby = true;

            if (id_featuresGroups.get(i).getGroupId() == 6)
                Group_Other = true;

            if (id_featuresGroups.get(i).getGroupId() == 7)
                Group_Security = true;

            if (id_featuresGroups.get(i).getGroupId() == 8)
                Group_Amazing = true;
        }

        //در اینجا مشخصات نمایش داده می شوند
        if (!Group_Possibilities) {
            Header_Possibilities.setVisibility(View.GONE);
        }

        if (!Group_Rooms) {
            Header_Rooms.setVisibility(View.GONE);
        }

        if (!Group_Commerce) {
            Header_Commerce.setVisibility(View.GONE);
        }

        if (!Group_Health) {
            Header_Health.setVisibility(View.GONE);
        }

        if (!Group_Nearby) {
            Header_Nearby.setVisibility(View.GONE);
        }

        if (!Group_Other) {
            Header_Other.setVisibility(View.GONE);
        }

        if (!Group_Security) {
            Header_Security.setVisibility(View.GONE);
        }

        if (!Group_Amazing) {
            Header_Amazing.setVisibility(View.GONE);
        }

        ////////////////////////////////////////

        //دراینجا ویژگی ها نمایش داده می شوند
        for (int i = 0; i < id_featuresGroups.size(); i++) {

            if (id_featuresGroups.get(i).getFeaturesId() == 1) {
                p1 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p1);
                p1.setVisibility(View.VISIBLE);
                txt_p1 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p1);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 2) {
                p2 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p2);
                p2.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 3) {
                p3 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p3);
                p3.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 4) {
                p4 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p4);
                p4.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 5) {
                p5 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p5);
                p5.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 6) {
                p6 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p6);
                p6.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 7) {
                p7 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p7);
                p7.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 8) {
                p8 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p8);
                p8.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 9) {
                p9 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p9);
                p9.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 10) {
                p10 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p10);
                p10.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 11) {
                p11 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p11);
                p11.setVisibility(View.VISIBLE);
                txt_p11 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p11);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 12) {
                p12 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p12);
                p12.setVisibility(View.VISIBLE);
                txt_p12 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p12);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 13) {
                p13 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p13);
                p13.setVisibility(View.VISIBLE);
                cmb_p13 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p13);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 14) {
                p14 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p14);
                p14.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 15) {
                p15 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p15);
                p15.setVisibility(View.VISIBLE);
                txt_p15 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p15);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 16) {
                p16 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p16);
                p16.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 17) {
                p17 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p17);
                p17.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 18) {
                p18 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p18);
                p18.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 19) {
                p19 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p19);
                p19.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 20) {
                p20 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p20);
                p20.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 21) {
                p21 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p21);
                p21.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 22) {
                p22 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p22);
                p22.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 23) {
                p23 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p23);
                p23.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 24) {
                p24 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p24);
                p24.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 25) {
                p25 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p25);
                p25.setVisibility(View.VISIBLE);
                txt_p25 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p25);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 26) {
                p26 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p26);
                p26.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 27) {
                p27 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p27);
                p27.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 28) {
                p28 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p28);
                p28.setVisibility(View.VISIBLE);
                txt_p28 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p28);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 29) {
                p29 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p29);
                p29.setVisibility(View.VISIBLE);
                txt_p29 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p29);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 30) {
                p30 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p30);
                p30.setVisibility(View.VISIBLE);
                txt_p30 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p30);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 31) {
                p31 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p31);
                p31.setVisibility(View.VISIBLE);
                txt_p31 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p31);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 32) {
                p32 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p32);
                p32.setVisibility(View.VISIBLE);
                txt_p32 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p32);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 33) {
                p33 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p33);
                p33.setVisibility(View.VISIBLE);
                txt_p33 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p33);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 34) {
                p34 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p34);
                p34.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 35) {
                p35 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p35);
                p35.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 36) {
                p36 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p36);
                p36.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 37) {
                p37 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p37);
                p37.setVisibility(View.VISIBLE);
                txt_p37 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p37);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 38) {
                p38 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p38);
                p38.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 39) {
                p39 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p39);
                p39.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 40) {
                p40 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p40);
                p40.setVisibility(View.VISIBLE);
                txt_p40 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p40);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 41) {
                p41 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p41);
                p41.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 42) {
                p42 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p42);
                p42.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 43) {
                p43 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p43);
                p43.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 44) {
                p44 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p44);
                p44.setVisibility(View.VISIBLE);
                cmb_p44 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p44);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 45) {
                p45 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p45);
                p45.setVisibility(View.VISIBLE);
                cmb_p45 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p45);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 46) {
                p46 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p46);
                p46.setVisibility(View.VISIBLE);
                cmb_p46 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p46);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 47) {
                p47 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p47);
                p47.setVisibility(View.VISIBLE);
                cmb_p47 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p47);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 48) {
                p48 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p48);
                p48.setVisibility(View.VISIBLE);
                cmb_p48 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p48);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 49) {
                p49 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p49);
                p49.setVisibility(View.VISIBLE);
                txt_p49 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p49);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 50) {
                p50 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p50);
                p50.setVisibility(View.VISIBLE);
                cmb_p50 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p50);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 51) {
                p51 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p51);
                p51.setVisibility(View.VISIBLE);
                cmb_p51 = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_p51);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 52) {
                p52 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p52);
                p52.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 53) {
                p53 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p53);
                p53.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 54) {
                p54 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p54);
                p54.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 55) {
                p55 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p55);
                p55.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 56) {
                p56 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p56);
                p56.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 57) {
                p57 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p57);
                p57.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 58) {
                p58 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p58);
                p58.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 59) {
                p59 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p59);
                p59.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 60) {
                p60 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p60);
                p60.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 61) {
                p61 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p61);
                p61.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 62) {
                p62 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p62);
                p62.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 63) {
                p63 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p63);
                p63.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 64) {
                p64 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p64);
                p64.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 65) {
                p65 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p65);
                p65.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 66) {
                p66 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p66);
                p66.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 67) {
                p67 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p67);
                p67.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 68) {
                p68 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p68);
                p68.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 69) {
                p69 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p69);
                p69.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 70) {
                p70 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p70);
                p70.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 71) {
                p71 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p71);
                p71.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 72) {
                p72 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p72);
                p72.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 73) {
                p73 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p73);
                p73.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 74) {
                p74 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p74);
                p74.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 75) {
                p75 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p75);
                p75.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 76) {
                p76 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p76);
                p76.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 77) {
                p77 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p77);
                p77.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 78) {
                p78 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p78);
                p78.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 79) {
                p79 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p79);
                p79.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 80) {
                p80 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p80);
                p80.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 81) {
                p81 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p81);
                p81.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 82) {
                p82 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p82);
                p82.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 83) {
                p83 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p83);
                p83.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 84) {
                p84 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p84);
                p84.setVisibility(View.VISIBLE);
                txt_p84 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p84);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 85) {
                p85 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p85);
                p85.setVisibility(View.VISIBLE);
                txt_p85 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p85);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 86) {
                p86 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p86);
                p86.setVisibility(View.VISIBLE);
                txt_p86 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p86);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 87) {
                p87 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p87);
                p87.setVisibility(View.VISIBLE);
                txt_p87 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p87);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 88) {
                p88 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p88);
                p88.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 89) {
                p89 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p89);
                p89.setVisibility(View.VISIBLE);

            }

            if (id_featuresGroups.get(i).getFeaturesId() == 90) {
                p90 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p90);
                p90.setVisibility(View.VISIBLE);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 91) {
                p91 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p91);
                p91.setVisibility(View.VISIBLE);
                txt_p91 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p91);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 92) {
                p92 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p92);
                p92.setVisibility(View.VISIBLE);
                txt_p92 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p92);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 93) {
                p93 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p93);
                p93.setVisibility(View.VISIBLE);
                txt_p93 = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_p93);
            }

            if (id_featuresGroups.get(i).getFeaturesId() == 94) {
                p94 = view.findViewById(ir.tdaapp.diako.shaar.R.id.p94);
                p94.setVisibility(View.VISIBLE);
            }
        }
    }

    // در اینجا اطلاعات وارد شده توسط کاربر در کلاس استاتیک DA_AddHome ذخیره می شوند
    void GetData() {

        //برای داده های از نوع عدد
        List<DA_TypeHome_Number> numbers = new ArrayList<>();

        //برای داده های از نوع بولین
        List<DA_TypeHome_Boolean> booleans = new ArrayList<>();

        //برای داده های از نوع spinner
        List<DA_TypeHome_Select> selects = new ArrayList<>();

        //برای داده های از نوع متن
        List<DA_TypeHome_Text> texts = new ArrayList<>();


        //در اینجا داده های از نوع عدد دریافت میشود

        if (txt_p11 != null) {
            if (!txt_p11.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(11, Integer.parseInt(txt_p11.getText().toString())));
            }
        }

        if (txt_p12 != null) {
            if (!txt_p12.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(12, Integer.parseInt(txt_p12.getText().toString())));
            }
        }


        if (txt_p37 != null) {
            if (!txt_p37.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(37, Integer.parseInt(txt_p37.getText().toString())));
            }
        }

        if (txt_p40 != null) {
            if (!txt_p40.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(40, Integer.parseInt(txt_p40.getText().toString())));
            }
        }

        if (txt_p49 != null) {
            if (!txt_p49.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(49, Integer.parseInt(txt_p49.getText().toString())));
            }
        }

        if (txt_p84 != null) {
            if (!txt_p84.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(84, Integer.parseInt(txt_p84.getText().toString())));
            }
        }

        if (txt_p85 != null) {
            if (!txt_p85.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(85, Integer.parseInt(txt_p85.getText().toString())));
            }
        }

        if (txt_p86 != null) {
            if (!txt_p86.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(86, Integer.parseInt(txt_p86.getText().toString())));
            }
        }

        if (txt_p87 != null) {
            if (!txt_p87.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(87, Integer.parseInt(txt_p87.getText().toString())));
            }
        }

        if (txt_p91 != null) {
            if (!txt_p91.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(91, Integer.parseInt(txt_p91.getText().toString())));
            }
        }

        if (txt_p92 != null) {
            if (!txt_p92.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(92, Integer.parseInt(txt_p92.getText().toString())));
            }
        }

        if (txt_p93 != null) {
            if (!txt_p93.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(93, Integer.parseInt(txt_p93.getText().toString())));
            }
        }

        if (txt_p25 != null) {
            if (!txt_p25.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(25, Integer.parseInt(txt_p25.getText().toString())));
            }
        }

        if (txt_p31 != null) {
            if (!txt_p31.getText().toString().equalsIgnoreCase("")) {
                numbers.add(new DA_TypeHome_Number(31, Integer.parseInt(txt_p31.getText().toString())));
            }
        }

        //////////////////////////////////////////////////////////////////////////////////

        //در اینجا داده های از نوع متن دریافت میشود
        if (txt_p1 != null) {
            if (!txt_p1.getText().equals("")) {
                texts.add(new DA_TypeHome_Text(1, txt_p1.getText().toString()));
            }
        }

        if (txt_p15 != null) {
            if (!txt_p15.getText().toString().equalsIgnoreCase("")) {
                texts.add(new DA_TypeHome_Text(15, txt_p15.getText().toString()));
            }
        }

        if (txt_p28 != null) {
            if (!txt_p28.getText().toString().equalsIgnoreCase("")) {
                texts.add(new DA_TypeHome_Text(28, txt_p28.getText().toString()));
            }
        }

        if (txt_p29 != null) {
            if (!txt_p29.getText().toString().equalsIgnoreCase("")) {
                texts.add(new DA_TypeHome_Text(29, txt_p29.getText().toString()));
            }
        }

        if (txt_p30 != null) {
            if (!txt_p30.getText().toString().equalsIgnoreCase("")) {
                texts.add(new DA_TypeHome_Text(30, txt_p30.getText().toString()));
            }
        }

        if (txt_p32 != null) {
            if (!txt_p32.getText().toString().equalsIgnoreCase("")) {
                texts.add(new DA_TypeHome_Text(32, txt_p32.getText().toString()));
            }
        }

        if (txt_p33 != null) {
            if (!txt_p33.getText().toString().equalsIgnoreCase("")) {
                texts.add(new DA_TypeHome_Text(33, txt_p33.getText().toString()));
            }
        }
        //////////////////////////////////////////////////////////////

        //دراینجا داده های از نوع اسپینر دریافت می شود

        if (cmb_p13 != null) {
            selects.add(new DA_TypeHome_Select(13, cmb_p13.getSelectedItem().toString(), cmb_p13.getSelectedItemPosition()));
        }

        if (cmb_p44 != null) {
            selects.add(new DA_TypeHome_Select(44, cmb_p44.getSelectedItem().toString(), cmb_p44.getSelectedItemPosition()));
        }

        if (cmb_p45 != null) {
            selects.add(new DA_TypeHome_Select(45, cmb_p45.getSelectedItem().toString(), cmb_p45.getSelectedItemPosition()));
        }

        if (cmb_p46 != null) {
            selects.add(new DA_TypeHome_Select(46, cmb_p46.getSelectedItem().toString(), cmb_p46.getSelectedItemPosition()));
        }

        if (cmb_p47 != null) {
            selects.add(new DA_TypeHome_Select(47, cmb_p47.getSelectedItem().toString(), cmb_p47.getSelectedItemPosition()));
        }

        if (cmb_p48 != null) {
            selects.add(new DA_TypeHome_Select(48, cmb_p48.getSelectedItem().toString(), cmb_p48.getSelectedItemPosition()));
        }

        if (cmb_p50 != null) {
            selects.add(new DA_TypeHome_Select(50, cmb_p50.getSelectedItem().toString(), cmb_p50.getSelectedItemPosition()));
        }

        if (cmb_p51 != null) {
            selects.add(new DA_TypeHome_Select(51, cmb_p51.getSelectedItem().toString(), cmb_p51.getSelectedItemPosition()));
        }
        ///////////////////////////////////////////////////////////////////

        //دراینجا داده های از نوع بولین دریافت می شود

        if (p2 != null) {
            if (p2.isChecked())
                booleans.add(new DA_TypeHome_Boolean(2, p2.isChecked()));
        }

        if (p3 != null) {
            if (p3.isChecked())
                booleans.add(new DA_TypeHome_Boolean(3, p3.isChecked()));
        }

        if (p4 != null) {
            if (p4.isChecked())
                booleans.add(new DA_TypeHome_Boolean(4, p4.isChecked()));
        }

        if (p5 != null) {
            if (p5.isChecked())
                booleans.add(new DA_TypeHome_Boolean(5, p5.isChecked()));
        }

        if (p6 != null) {
            if (p6.isChecked())
                booleans.add(new DA_TypeHome_Boolean(6, p6.isChecked()));
        }

        if (p7 != null) {
            if (p7.isChecked())
                booleans.add(new DA_TypeHome_Boolean(7, p7.isChecked()));
        }

        if (p8 != null) {
            if (p8.isChecked())
                booleans.add(new DA_TypeHome_Boolean(8, p8.isChecked()));
        }

        if (p9 != null) {
            if (p9.isChecked())
                booleans.add(new DA_TypeHome_Boolean(9, p9.isChecked()));
        }

        if (p14 != null) {
            if (p14.isChecked())
                booleans.add(new DA_TypeHome_Boolean(14, p14.isChecked()));
        }

        if (p16 != null) {
            if (p16.isChecked())
                booleans.add(new DA_TypeHome_Boolean(16, p16.isChecked()));
        }

        if (p17 != null) {
            if (p17.isChecked())
                booleans.add(new DA_TypeHome_Boolean(17, p17.isChecked()));
        }

        if (p24 != null) {
            if (p24.isChecked())
                booleans.add(new DA_TypeHome_Boolean(24, p24.isChecked()));
        }

        if (p52 != null) {
            if (p52.isChecked())
                booleans.add(new DA_TypeHome_Boolean(52, p52.isChecked()));
        }

        if (p53 != null) {
            if (p53.isChecked())
                booleans.add(new DA_TypeHome_Boolean(53, p53.isChecked()));
        }

        if (p54 != null) {
            if (p54.isChecked())
                booleans.add(new DA_TypeHome_Boolean(54, p54.isChecked()));
        }

        if (p55 != null) {
            if (p55.isChecked())
                booleans.add(new DA_TypeHome_Boolean(55, p55.isChecked()));
        }

        if (p56 != null) {
            if (p56.isChecked())
                booleans.add(new DA_TypeHome_Boolean(56, p56.isChecked()));
        }

        if (p57 != null) {
            if (p57.isChecked())
                booleans.add(new DA_TypeHome_Boolean(57, p57.isChecked()));
        }

        if (p58 != null) {
            if (p58.isChecked())
                booleans.add(new DA_TypeHome_Boolean(58, p58.isChecked()));
        }

        if (p81 != null) {
            if (p81.isChecked())
                booleans.add(new DA_TypeHome_Boolean(81, p81.isChecked()));
        }

        if (p82 != null) {
            if (p82.isChecked())
                booleans.add(new DA_TypeHome_Boolean(82, p82.isChecked()));
        }

        if (p83 != null) {
            if (p83.isChecked())
                booleans.add(new DA_TypeHome_Boolean(83, p83.isChecked()));
        }

        if (p88 != null) {
            if (p88.isChecked())
                booleans.add(new DA_TypeHome_Boolean(88, p88.isChecked()));
        }

        if (p89 != null) {
            if (p89.isChecked())
                booleans.add(new DA_TypeHome_Boolean(89, p89.isChecked()));
        }

        if (p90 != null) {
            if (p90.isChecked())
                booleans.add(new DA_TypeHome_Boolean(90, p90.isChecked()));
        }

        if (p14 != null) {
            if (p14.isChecked())
                booleans.add(new DA_TypeHome_Boolean(14, p14.isChecked()));
        }

        if (p20 != null) {
            if (p20.isChecked())
                booleans.add(new DA_TypeHome_Boolean(20, p20.isChecked()));
        }

        if (p26 != null) {
            if (p26.isChecked())
                booleans.add(new DA_TypeHome_Boolean(26, p26.isChecked()));
        }

        if (p38 != null) {
            if (p38.isChecked())
                booleans.add(new DA_TypeHome_Boolean(38, p38.isChecked()));
        }

        if (p39 != null) {
            if (p39.isChecked())
                booleans.add(new DA_TypeHome_Boolean(39, p39.isChecked()));
        }

        if (p21 != null) {
            if (p21.isChecked())
                booleans.add(new DA_TypeHome_Boolean(21, p21.isChecked()));
        }

        if (p22 != null) {
            if (p22.isChecked())
                booleans.add(new DA_TypeHome_Boolean(22, p22.isChecked()));
        }

        if (p23 != null) {
            if (p23.isChecked())
                booleans.add(new DA_TypeHome_Boolean(23, p23.isChecked()));
        }

        if (p10 != null) {
            if (p10.isChecked())
                booleans.add(new DA_TypeHome_Boolean(10, p10.isChecked()));
        }

        if (p18 != null) {
            if (p18.isChecked())
                booleans.add(new DA_TypeHome_Boolean(18, p18.isChecked()));
        }

        if (p27 != null) {
            if (p27.isChecked())
                booleans.add(new DA_TypeHome_Boolean(27, p27.isChecked()));
        }

        if (p34 != null) {
            if (p34.isChecked())
                booleans.add(new DA_TypeHome_Boolean(34, p34.isChecked()));
        }

        if (p35 != null) {
            if (p35.isChecked())
                booleans.add(new DA_TypeHome_Boolean(35, p35.isChecked()));
        }

        if (p36 != null) {
            if (p36.isChecked())
                booleans.add(new DA_TypeHome_Boolean(36, p36.isChecked()));
        }

        if (p59 != null) {
            if (p59.isChecked())
                booleans.add(new DA_TypeHome_Boolean(59, p59.isChecked()));
        }

        if (p60 != null) {
            if (p60.isChecked())
                booleans.add(new DA_TypeHome_Boolean(60, p60.isChecked()));
        }

        if (p61 != null) {
            if (p61.isChecked())
                booleans.add(new DA_TypeHome_Boolean(61, p61.isChecked()));
        }

        if (p64 != null) {
            if (p64.isChecked())
                booleans.add(new DA_TypeHome_Boolean(64, p64.isChecked()));
        }

        if (p65 != null) {
            if (p65.isChecked())
                booleans.add(new DA_TypeHome_Boolean(65, p65.isChecked()));
        }

        if (p66 != null) {
            if (p66.isChecked())
                booleans.add(new DA_TypeHome_Boolean(66, p66.isChecked()));
        }

        if (p67 != null) {
            if (p67.isChecked())
                booleans.add(new DA_TypeHome_Boolean(67, p67.isChecked()));
        }

        if (p68 != null) {
            if (p68.isChecked())
                booleans.add(new DA_TypeHome_Boolean(68, p68.isChecked()));
        }

        if (p69 != null) {
            if (p69.isChecked())
                booleans.add(new DA_TypeHome_Boolean(69, p69.isChecked()));
        }

        if (p70 != null) {
            if (p70.isChecked())
                booleans.add(new DA_TypeHome_Boolean(70, p70.isChecked()));
        }

        if (p71 != null) {
            if (p71.isChecked())
                booleans.add(new DA_TypeHome_Boolean(71, p71.isChecked()));
        }

        if (p72 != null) {
            if (p72.isChecked())
                booleans.add(new DA_TypeHome_Boolean(72, p72.isChecked()));
        }

        if (p73 != null) {
            if (p73.isChecked())
                booleans.add(new DA_TypeHome_Boolean(73, p73.isChecked()));
        }

        if (p74 != null) {
            if (p74.isChecked())
                booleans.add(new DA_TypeHome_Boolean(74, p74.isChecked()));
        }

        if (p75 != null) {
            if (p75.isChecked())
                booleans.add(new DA_TypeHome_Boolean(75, p75.isChecked()));
        }

        if (p76 != null) {
            if (p76.isChecked())
                booleans.add(new DA_TypeHome_Boolean(76, p76.isChecked()));
        }

        if (p77 != null) {
            if (p77.isChecked())
                booleans.add(new DA_TypeHome_Boolean(77, p77.isChecked()));
        }

        if (p78 != null) {
            if (p78.isChecked())
                booleans.add(new DA_TypeHome_Boolean(78, p78.isChecked()));
        }

        if (p79 != null) {
            if (p79.isChecked())
                booleans.add(new DA_TypeHome_Boolean(79, p79.isChecked()));
        }

        if (p41 != null) {
            if (p41.isChecked())
                booleans.add(new DA_TypeHome_Boolean(41, p41.isChecked()));
        }

        if (p42 != null) {
            if (p42.isChecked())
                booleans.add(new DA_TypeHome_Boolean(42, p42.isChecked()));
        }

        if (p43 != null) {
            if (p43.isChecked())
                booleans.add(new DA_TypeHome_Boolean(43, p43.isChecked()));
        }

        if (p62 != null) {
            if (p62.isChecked())
                booleans.add(new DA_TypeHome_Boolean(62, p62.isChecked()));
        }

        if (p63 != null) {
            if (p63.isChecked())
                booleans.add(new DA_TypeHome_Boolean(63, p63.isChecked()));
        }

        if (p80 != null) {
            if (p80.isChecked())
                booleans.add(new DA_TypeHome_Boolean(80, p80.isChecked()));
        }

        if (p94 != null) {
            if (p94.isChecked())
                booleans.add(new DA_TypeHome_Boolean(94, p94.isChecked()));
        }

        if (p19 != null) {
            if (p19.isChecked())
                booleans.add(new DA_TypeHome_Boolean(19, p19.isChecked()));
        }
        /////////////////////////////////////////////////////////////

        //در اینجا مقادیر در کلاس استاتیک ذخیره می شوند
        DA_Add_Home.booleans = booleans;
        DA_Add_Home.numbers = numbers;
        DA_Add_Home.selects = selects;
        DA_Add_Home.texts = texts;
    }

    //در اینجا مقادیر از کلاس استاتیک خوانده و در المنت ها جاگذاری می شوند
    void SetValueToElement() {

        //در اینجا مقادیر چک باکس ها جایگزین می شوند
        if (DA_Add_Home.booleans != null) {

            for (int i = 0; i < DA_Add_Home.booleans.size(); i++) {

                if (p2 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 2) {
                        p2.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p3 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 3) {
                        p3.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p4 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 4) {
                        p4.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p5 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 5) {
                        p5.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p6 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 6) {
                        p6.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p7 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 7) {
                        p7.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p8 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 8) {
                        p8.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p9 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 9) {
                        p9.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }
//todo diako baresi tekrari
                if (p14 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 14) {
                        p14.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p16 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 16) {
                        p16.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p17 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 17) {
                        p17.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p24 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 24) {
                        p24.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p52 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 52) {
                        p52.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p53 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 53) {
                        p53.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p54 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 54) {
                        p54.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p55 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 55) {
                        p55.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p56 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 56) {
                        p56.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p57 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 57) {
                        p57.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p58 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 58) {
                        p58.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p81 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 81) {
                        p81.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p82 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 82) {
                        p82.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p83 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 83) {
                        p83.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p88 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 88) {
                        p88.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p89 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 89) {
                        p89.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p90 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 90) {
                        p90.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }
//todo diako baresi tekrari
//                if (p14 != null) {
//                    if (DA_Add_Home.booleans.get(i).getId() == 14) {
//                        p14.setChecked(DA_Add_Home.booleans.get(i).getVal());
//                    }
//                }

                if (p20 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 20) {
                        p20.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p26 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 26) {
                        p26.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p38 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 38) {
                        p38.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p39 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 39) {
                        p39.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p21 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 21) {
                        p21.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p22 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 22) {
                        p22.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p23 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 23) {
                        p23.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p10 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 10) {
                        p10.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p18 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 18) {
                        p18.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p27 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 27) {
                        p27.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p34 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 34) {
                        p34.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p35 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 35) {
                        p35.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p36 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 36) {
                        p36.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p59 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 59) {
                        p59.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p60 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 60) {
                        p60.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p61 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 61) {
                        p61.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p64 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 64) {
                        p64.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p65 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 65) {
                        p65.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p66 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 66) {
                        p66.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p67 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 67) {
                        p67.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p68 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 68) {
                        p68.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p69 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 69) {
                        p69.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p70 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 70) {
                        p70.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p71 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 71) {
                        p71.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p72 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 72) {
                        p72.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p73 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 73) {
                        p73.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p74 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 74) {
                        p74.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p75 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 75) {
                        p75.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p76 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 76) {
                        p76.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p77 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 77) {
                        p77.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p78 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 78) {
                        p78.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p79 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 79) {
                        p79.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p41 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 41) {
                        p41.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p42 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 42) {
                        p42.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p43 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 43) {
                        p43.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p62 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 62) {
                        p62.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p63 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 63) {
                        p63.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p80 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 80) {
                        p80.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p94 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 94) {
                        p94.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

                if (p19 != null) {
                    if (DA_Add_Home.booleans.get(i).getId() == 19) {
                        p19.setChecked(DA_Add_Home.booleans.get(i).getVal());
                    }
                }

            }

        }
        ///////////////////////////

        //در ابنجا داده های از نوع عدد set می شوند
        if (DA_Add_Home.numbers != null) {

            for (int i = 0; i < DA_Add_Home.numbers.size(); i++) {

                if (txt_p11 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 11) {
                        txt_p11.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p12 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 12) {
                        txt_p12.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p37 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 37) {
                        txt_p37.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p40 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 40) {
                        txt_p40.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p49 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 49) {
                        txt_p49.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p84 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 84) {
                        txt_p84.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p85 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 85) {
                        txt_p85.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p86 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 86) {
                        txt_p86.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p87 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 87) {
                        txt_p87.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p91 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 91) {
                        txt_p91.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p92 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 92) {
                        txt_p92.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p93 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 93) {
                        txt_p93.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p25 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 25) {
                        txt_p25.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

                if (txt_p31 != null) {
                    if (DA_Add_Home.numbers.get(i).getId() == 31) {
                        txt_p31.setText(String.valueOf(DA_Add_Home.numbers.get(i).getVal()));
                    }
                }

            }
        }
        ////////////////////////

        //برای set کردن داده های تکست

        if (DA_Add_Home.texts != null) {

            for (int i = 0; i < DA_Add_Home.texts.size(); i++) {

                if (txt_p1 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 1) {
                        txt_p1.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

                if (txt_p15 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 15) {
                        txt_p15.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

                if (txt_p28 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 28) {
                        txt_p28.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

                if (txt_p29 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 29) {
                        txt_p29.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

                if (txt_p30 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 30) {
                        txt_p30.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

                if (txt_p32 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 32) {
                        txt_p32.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

                if (txt_p33 != null) {
                    if (DA_Add_Home.texts.get(i).getId() == 33) {
                        txt_p33.setText(DA_Add_Home.texts.get(i).getVal());
                    }
                }

            }

        }
        /////////////////////////////

        if (DA_Add_Home.selects != null) {

            for (int i = 0; i < DA_Add_Home.selects.size(); i++) {

                if (cmb_p13 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 13) {
                        cmb_p13.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p44 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 44) {
                        cmb_p44.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p45 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 45) {
                        cmb_p45.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p46 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 46) {
                        cmb_p46.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p47 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 47) {
                        cmb_p47.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p48 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 48) {
                        cmb_p48.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p50 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 50) {
                        cmb_p50.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }

                if (cmb_p51 != null) {
                    if (DA_Add_Home.selects.get(i).getId() == 51) {
                        cmb_p51.setSelection(DA_Add_Home.selects.get(i).getPosition());
                    }
                }
            }
        }
    }
}