package ir.tdaapp.diako.shaar.FragmentPage;


import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.CountRoom;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.TypeHome_Search_Adapter;
import ir.tdaapp.diako.shaar.Data.DA_Add_Home;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.Interface.SearchHome;
import ir.tdaapp.diako.shaar.Model.Location;
import ir.tdaapp.diako.shaar.Model.Type_Home;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.ViewModel.VM_Search;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/12/2019.
 */

public class Fragment_Search_Home extends Fragment implements IBase {

    public static final String TAG="Fragment_Search_Home";

    TextView lbl_Location, lbl_Price, lbl_Area, lbl_Rent, lbl_Mortgage;
    EditText txt_LowestPrice, txt_MaximumPrice, txt_LowestArea, txt_MaximumArea, txt_LowestRent, txt_MaximumRent;
    EditText txt_LowestMortgage, txt_MaximumMortgage;
    RecyclerView recyclerView, RecyclerTypeHome;
    ImageView Close;
    Button btn_Search, ClearAll;
    SearchableSpinner cmb_Location;
    DBAdapter dbAdapter;
    ArrayList<Location> locations;
    RadioButton rdo_SellAndBuy, rdo_Rent, rdo_Residential, rdo_Commercial, rdo_Industrial;
    LinearLayout Rent_Group, Mortgage_Group, Price_Group;
    GridLayoutManager gridLayoutManager;

    TypeHome_Search_Adapter typeHomeAdapter_Residential, typeHomeAdapter_Commercial, typeHomeAdapter_Industrial, typeHomeAdapter_Empty;
    CountRoom countRoom;

    CheckBox chk_FullRent, chk_FullMortgage;

    int locationId = 0, Turn = 0;
    SearchHome searchHome;

    public Fragment_Search_Home(SearchHome searchHome) {
        this.searchHome = searchHome;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_search_home, container, false);

        //این تابع برای وصل کردن آیتم ها به متغیرها می باشد
        FindItem(view);

        //در اینجا تیک رادیو باتن خرید و فروش فعال می شود و ادیت تکست های خرید و رهن مخفی و ادیت تکست های قیمت فعال می شود
        rdo_SellAndBuy.setChecked(true);
        Hide_Show_EditTexts(1);

        //برای نمایش تعداد اتاق
        SetCountRoom();

        //در اینجا آداپترهای recycler مربوط به مسکونی صنعتی تجاری ساخته می شوند
        AdapterTypeHome();


        //در اینجا مقادیر اسپینر موقعیت set می شوند
        SetDataSpinner();
        OnClick();

//        txt_Location.requestFocus();
//        InputMethodManager imm = (InputMethodManager)((AppCompatActivity) getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        return view;
    }

    /*
     * در این متد هنگامی که روی یک ادیت تکست فوکوس می کند backGround ادیت تکست و متن تایتل ان تغییر می کند
     * */
    public void SetBackGroundTientEditText(final EditText txt, final TextView lbl) {
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

    public void FindItem(View view) {
        lbl_Location = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_lbl_Area);
        lbl_Price = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_lbl_Price);
        lbl_Area = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_lbl_TheArea_SquareMeters);
        lbl_Rent = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_lbl_Rent);
        lbl_Mortgage = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_lbl_Mortgage);


        txt_LowestPrice = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_LowestPrice);
        txt_MaximumPrice = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_MaximumPrice);
        txt_LowestArea = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_LowestArea);
        txt_MaximumArea = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_MaximumArea);
        txt_LowestRent = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_LowestRent);
        txt_MaximumRent = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_MaximumRent);
        txt_LowestMortgage = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_LowestMortgage);
        txt_MaximumMortgage = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_txt_MaximumMortgage);

        rdo_SellAndBuy = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_SellAndBuy);
        rdo_Rent = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Rent);
        rdo_Residential = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Residential);
        rdo_Commercial = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Commercial);
        rdo_Industrial = view.findViewById(ir.tdaapp.diako.shaar.R.id.rdo_Industrial);

        chk_FullRent = view.findViewById(ir.tdaapp.diako.shaar.R.id.chk_FullRent);
        chk_FullMortgage = view.findViewById(ir.tdaapp.diako.shaar.R.id.chk_FullMortgage);

        Rent_Group = view.findViewById(ir.tdaapp.diako.shaar.R.id.Rent_Group);
        Mortgage_Group = view.findViewById(ir.tdaapp.diako.shaar.R.id.Mortgage_Group);
        Price_Group = view.findViewById(ir.tdaapp.diako.shaar.R.id.Price_Group);

        RecyclerTypeHome = view.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler);
        recyclerView = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Search_Home_Recycler_CountRoom);


        btn_Search = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Search);
        ClearAll = view.findViewById(R.id.ClearAll);
        Close = view.findViewById(ir.tdaapp.diako.shaar.R.id.Close);
        cmb_Location = view.findViewById(ir.tdaapp.diako.shaar.R.id.cmb_Location);
        dbAdapter = new DBAdapter(getActivity());
        locations = new ArrayList<>();


        SetBackGroundTientEditText(txt_LowestPrice, lbl_Price);
        SetBackGroundTientEditText(txt_MaximumPrice, lbl_Price);
        SetBackGroundTientEditText(txt_MaximumArea, lbl_Area);
        SetBackGroundTientEditText(txt_LowestArea, lbl_Area);
        SetBackGroundTientEditText(txt_LowestRent, lbl_Rent);
        SetBackGroundTientEditText(txt_MaximumRent, lbl_Rent);
        SetBackGroundTientEditText(txt_MaximumMortgage, lbl_Mortgage);
        SetBackGroundTientEditText(txt_LowestMortgage, lbl_Mortgage);
    }

    /*
     * در اینجا مقادیر تعداد اتاق set می شوند
     * */
    public String[] SetValueCountRoom(int Count) {
        String[] val = new String[Count + 1];
        val[0] = "بدون اتاق";

        for (int i = 1; i <= Count; i++) {
            val[i] = String.valueOf(i);
        }
        return val;
    }

    /*
     * در اینجا مقادیر تعداد اتاق در recycler تعداد اتاق set می شوند
     * */
    public void SetCountRoom() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        countRoom = new CountRoom(getActivity(), SetValueCountRoom(10));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(countRoom);
    }

    void OnClick() {

        ClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //در اینجا اگر کاربر یک از آیتم های درون رسایکر ویو را انتخاب کرده باشد خالی می شود
                DA_Add_Home.ClearDA_AddHome();

                //در اینجا رسایکلر ها خالی می شوند
                RecyclerTypeHome.setAdapter(typeHomeAdapter_Empty);
                rdo_Commercial.setChecked(false);
                rdo_Residential.setChecked(false);
                rdo_Industrial.setChecked(false);

                //در اینجا لوکیشن ریست می شود
                locationId = 0;
                Turn = 0;

                txt_LowestPrice.setText("");
                txt_MaximumPrice.setText("");

                chk_FullMortgage.setChecked(false);

                txt_LowestMortgage.setText("");
                txt_MaximumMortgage.setText("");

                chk_FullRent.setChecked(false);

                txt_LowestRent.setText("");
                txt_MaximumRent.setText("");

                txt_LowestArea.setText("");
                txt_MaximumArea.setText("");

                typeHomeAdapter_Commercial.RemoveVal();
                typeHomeAdapter_Industrial.RemoveVal();
                typeHomeAdapter_Residential.RemoveVal();

                countRoom = new CountRoom(getActivity(), SetValueCountRoom(11));
                recyclerView.setAdapter(countRoom);

            }
        });

        /*
         * در این دکمه صفحه جاری بسته می شود
         * */
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        /*
         * کاربر با فشار این دکمه فیلتر های او به سمت سرور ارسال شده و نتایج او برگشت داده می شود و وارد صفحه Resault Search می شود
         * */
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final VM_Search vm_search = new VM_Search();

                //در اینجا خریدوفروش یا اجاره بودن را چک می کند و آن را در ViewModel ست می کند
                if (rdo_SellAndBuy.isChecked()) {
                    vm_search.setTarget(1);
                } else {
                    vm_search.setTarget(0);
                }

                //در اینجا تایپ منزل را به دست می اوریم
                if (rdo_Residential.isChecked() ) {
                    vm_search.setTypeHome(typeHomeAdapter_Residential.GetId());
                } else if (rdo_Commercial.isChecked()) {
                    vm_search.setTypeHome(typeHomeAdapter_Commercial.GetId());
                } else {
                    vm_search.setTypeHome(typeHomeAdapter_Industrial.GetId());
                }

                //در اینجا موقعیت منزل ست می شود
                vm_search.setLocationId(locationId);

                BigInteger MinPrice, MaxPrice;

                //در اینجا اگر کاربر رادیو باتن خرید و فروش را انتخاب کرده باشد ادیت تکست قیمت در MinPrice,MaxPrice ست خواهد شد ولی اگر اجاره را ست کرده باشد ادیت تکست اجاره ست خواهد شد
                if (rdo_SellAndBuy.isChecked()) {
                    if (txt_LowestPrice.getText().toString().equalsIgnoreCase("")) {
                        MinPrice = new BigInteger("0");
                    } else {
                        MinPrice = new BigInteger(txt_LowestPrice.getText().toString());
                    }

                    if (txt_MaximumPrice.getText().toString().equalsIgnoreCase("")) {
                        MaxPrice = new BigInteger("0");
                    } else {
                        MaxPrice = new BigInteger(txt_MaximumPrice.getText().toString());
                    }
                } else {
                    if (txt_LowestRent.getText().toString().equalsIgnoreCase("")) {
                        MinPrice = new BigInteger("0");
                    } else {
                        MinPrice = new BigInteger(txt_LowestRent.getText().toString());
                    }

                    if (txt_MaximumRent.getText().toString().equalsIgnoreCase("")) {
                        MaxPrice = new BigInteger("0");
                    } else {
                        MaxPrice = new BigInteger(txt_MaximumRent.getText().toString());
                    }
                }

                vm_search.setMinPrice(MinPrice);
                vm_search.setMaxPrice(MaxPrice);

                BigInteger MinMortgage, MaxMortgage;

                //در اینجا پایین ترین قیمت رهن ست می شود
                if (txt_LowestMortgage.getText().toString().equalsIgnoreCase("")) {
                    MinMortgage = new BigInteger("0");
                } else {
                    MinMortgage = new BigInteger(txt_LowestMortgage.getText().toString());
                }
                vm_search.setMinMortgage(MinMortgage);

                //در اینجا بالاترین قیمت رهن ست می شود
                if (txt_MaximumMortgage.getText().toString().equalsIgnoreCase("")) {
                    MaxMortgage = new BigInteger("0");
                } else {
                    MaxMortgage = new BigInteger(txt_MaximumMortgage.getText().toString());
                }
                vm_search.setMaxMortgage(MaxMortgage);

                BigInteger MinArea, MaxArea;

                //در اینجا پایین ترین متراژ ست می شود
                if (txt_LowestArea.getText().toString().equalsIgnoreCase("")) {
                    MinArea = new BigInteger("0");
                } else {
                    MinArea = new BigInteger(txt_LowestArea.getText().toString());
                }
                vm_search.setMinArea(MinArea);

                //ر اینجا بالاترین متراژ ست می شود
                if (txt_MaximumArea.getText().toString().equalsIgnoreCase("")) {
                    MaxArea = new BigInteger("0");
                } else {
                    MaxArea = new BigInteger(txt_MaximumArea.getText().toString());
                }
                vm_search.setMaxArea(MaxArea);

                //در اینجا مقدار تعداد اتاق ست می شود
                vm_search.setCountRoom(countRoom.GetCountRoom());

                if (chk_FullRent.isChecked()) {
                    vm_search.setFullRent(true);
                }
                if (chk_FullMortgage.isChecked()) {
                    vm_search.setFullMortgage(true);
                }

                SaveToLastSearch(vm_search);

                if (searchHome!=null){
                    searchHome.search();
                }
                getActivity().onBackPressed();
//                Bundle bundle = new Bundle();
//                bundle.putInt("Type", 1);
//                Fragment_Resault_Search fragment_resault_search=new Fragment_Resault_Search();
//                fragment_resault_search.setArguments(bundle);
//                ((MainActivity)getActivity()).onAddFragment(fragment_resault_search,0,0,true,Fragment_Resault_Search.TAG);
//                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search", getContext(), bundle);

            }
        });

        /*
         * کاربر با انتخاب این گزینه آیتم های مربوط به خرید و فروش نمایش داده می شود
         * */
        rdo_SellAndBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hide_Show_EditTexts(1);
            }
        });

        /*
         * کاربر با انتخاب این گزینه آیتم های مربوط به اجاره نمایش داده می شود
         * */
        rdo_Rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hide_Show_EditTexts(2);

                /*
                 * در اینجا برنامه چک می کند اگر اجاره کامل تیک دار باشد رهن را مخفی کند و بلعکس و اگر هیچکدام تیک دار نباشند همه نمایش داده می شوند
                 * */
                if (chk_FullRent.isChecked())
                    Hide_Show_FullRent_Mortgage(1);

                else if (chk_FullMortgage.isChecked())
                    Hide_Show_FullRent_Mortgage(2);

                else
                    Hide_Show_FullRent_Mortgage(3);
            }
        });

        /*
         * کاربر با انتخاب این گزینه بخش مربوط به رهن غیر فعال می شود
         * */
        chk_FullRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk_FullRent.isChecked())
                    Hide_Show_FullRent_Mortgage(1);
                else
                    Hide_Show_FullRent_Mortgage(3);
            }
        });

        /*
         * کاربر با انتخاب این گزینه بخش مربوط به اجاره غیر فعال می شود
         * */
        chk_FullMortgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk_FullMortgage.isChecked())
                    Hide_Show_FullRent_Mortgage(2);
                else
                    Hide_Show_FullRent_Mortgage(3);
            }
        });

        /*
         * با انتخاب این گزینه آداپتر مسکونی نمایش داده می شود
         * */
        RecyclerTypeHome.setAdapter(typeHomeAdapter_Residential);

        rdo_Residential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerTypeHome.setAdapter(typeHomeAdapter_Residential);


                typeHomeAdapter_Residential.SetId(1);
            }
        });

        /*
         * با انتخاب این گزینه آداپتر تجاری نمایش داده می شود
         * */
        rdo_Commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerTypeHome.setAdapter(typeHomeAdapter_Commercial);

                typeHomeAdapter_Residential.SetId(2);
            }
        });

        /*
         * با انتخاب این گزینه آداپتر صنعتی نمایش داده می شود
         * */
        rdo_Industrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerTypeHome.setAdapter(typeHomeAdapter_Industrial);

                typeHomeAdapter_Residential.SetId(32);
            }
        });

        cmb_Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Turn != 0) {
                    Location location = (Location) adapterView.getSelectedItem();
                    locationId = location.getId();
                }
                Turn++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //در اینجا مقادیر اسپینر موقعیت set می شوند
    void SetDataSpinner() {

        Cursor cursor = dbAdapter.ExecuteQ("select * from TblLocation order by Title asc");
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                locations.add(new Location(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbAdapter.close();

        ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(getActivity(), R.layout.spinner_layout, locations);
        cmb_Location.setAdapter(adapter);
        cmb_Location.setTitle(getResources().getString(R.string.SelectedLocation));
        cmb_Location.setPositiveButton(getResources().getString(R.string.close));

    }

    /*در این متد ادیت تکست های رهن و اجاره و قیمت را نمایش یا مخفی می کند
     * ودر ورودی یک عدد را خواهد گرفت که عدد 1 به معنای نمایش قیمت و مخفی کردن رهن و اجاره و عدد 2 به معنای نمایش رهن و اجاره و مخفی کردن قیمت*/
    void Hide_Show_EditTexts(int a) {
        if (a == 1) {
            chk_FullRent.setVisibility(View.GONE);
            lbl_Rent.setVisibility(View.GONE);
            Rent_Group.setVisibility(View.GONE);

            chk_FullMortgage.setVisibility(View.GONE);
            lbl_Mortgage.setVisibility(View.GONE);
            Mortgage_Group.setVisibility(View.GONE);

            lbl_Price.setVisibility(View.VISIBLE);
            Price_Group.setVisibility(View.VISIBLE);
        } else {
            chk_FullRent.setVisibility(View.VISIBLE);
            lbl_Rent.setVisibility(View.VISIBLE);
            Rent_Group.setVisibility(View.VISIBLE);

            chk_FullMortgage.setVisibility(View.VISIBLE);
            lbl_Mortgage.setVisibility(View.VISIBLE);
            Mortgage_Group.setVisibility(View.VISIBLE);

            lbl_Price.setVisibility(View.GONE);
            Price_Group.setVisibility(View.GONE);
        }
    }

    /*
     * این متد برای نمایش و مخفی کردن اجاره و رهن می باشد
     * هنگامی که کاربر چک باکس رهن کامل را انتخاب کند ادیت تکست های اجاره مخفی می شوند و بلعکس
     * و یک متغیر از ورودی می گیرد عدد 1 به برای مخفی کردن رهن و 2 برای مخفی کردن اجاره و3 برای نمایش همه
     * */
    void Hide_Show_FullRent_Mortgage(int a) {
        if (a == 1) {
            chk_FullMortgage.setVisibility(View.GONE);
            lbl_Mortgage.setVisibility(View.GONE);
            Mortgage_Group.setVisibility(View.GONE);
        } else if (a == 2) {
            chk_FullRent.setVisibility(View.GONE);
            lbl_Rent.setVisibility(View.GONE);
            Rent_Group.setVisibility(View.GONE);
        } else {
            chk_FullMortgage.setVisibility(View.VISIBLE);
            lbl_Mortgage.setVisibility(View.VISIBLE);
            Mortgage_Group.setVisibility(View.VISIBLE);

            chk_FullRent.setVisibility(View.VISIBLE);
            lbl_Rent.setVisibility(View.VISIBLE);
            Rent_Group.setVisibility(View.VISIBLE);
        }
    }

    /*
     * در اینجا لیستی از دیتاهای جدول TblType برگشت داده می شود که در recycler Type Home نمایش داده شود
     * اینجا یک متغیر از ورودی می گیرد عدد 1 به معنای داده های که مربوط به مسکونی نمایش داده شود
     * عدد 2 برای داده های از نوع تجاری برگشت داده شود
     * و عدد3 داده های از نوع صنعتی برگشت داده شود
     * */
    public List<Type_Home> SetDataTypeHome(int type) {
        List<Type_Home> lst = new ArrayList<>();
        int Id;
        if (type == 1) {
            Cursor cursor = dbAdapter.ExecuteQ("select * from TblType where ParentId=1");
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Id = Integer.parseInt(cursor.getString(0));
                    if (Id != 37 && Id != 29 && Id != 38 && Id != 18)
                        lst.add(new Type_Home(cursor.getString(1), Id));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            dbAdapter.close();
            return lst;
        } else if (type == 2) {
            Cursor cursor = dbAdapter.ExecuteQ("select * from TblType where ParentId=2");
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Id = Integer.parseInt(cursor.getString(0));
                    if (Id != 40 && Id != 24 && Id != 41)
                        lst.add(new Type_Home(cursor.getString(1), Id));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            dbAdapter.close();
            return lst;
        } else {
            Cursor cursor = dbAdapter.ExecuteQ("select * from TblType where ParentId=32");
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Id = Integer.parseInt(cursor.getString(0));
                    if (Id != 36)
                        lst.add(new Type_Home(cursor.getString(1), Id));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            dbAdapter.close();
            return lst;
        }
    }

    //در اینجا آداپترهای recycler مربوط به مسکونی صنعتی تجاری ساخته می شوند
    void AdapterTypeHome() {
        typeHomeAdapter_Residential = new TypeHome_Search_Adapter(getActivity(), SetDataTypeHome(1));
        typeHomeAdapter_Commercial = new TypeHome_Search_Adapter(getActivity(), SetDataTypeHome(2));
        typeHomeAdapter_Industrial = new TypeHome_Search_Adapter(getActivity(), SetDataTypeHome(3));
        List<Type_Home> type_homes = new ArrayList<>();
        typeHomeAdapter_Empty = new TypeHome_Search_Adapter(getContext(), type_homes);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerTypeHome.setLayoutManager(gridLayoutManager);
    }

    //در اینجا داده های که کاربر وارد کرده در دیتابیس ذخیره می شوند
    void SaveToLastSearch(VM_Search vm_search) {

        String MinPrice = String.valueOf(vm_search.getMinPrice());
        String MaxPrice = String.valueOf(vm_search.getMaxPrice());
        String MinMortgage = String.valueOf(vm_search.getMinMortgage());
        String MaxMortgage = String.valueOf(vm_search.getMaxMortgage());
        String MinArea = String.valueOf(vm_search.getMinArea());
        String MaxArea = String.valueOf(vm_search.getMaxArea());

        Cursor RemoveFromLastSearch = dbAdapter.ExecuteQ("delete from TblLastSearch");
        RemoveFromLastSearch.close();

        //در اینجا بزرگترین Id را به دست می اوریم
        Cursor GetId = dbAdapter.ExecuteQ("select Max(Id) from TblLastSearch");
        int Id;
        if (GetId.getString(0) != null)
            Id = Integer.parseInt(GetId.getString(0)) + 1;
        else
            Id = 1;

        Cursor Insert = dbAdapter.ExecuteQ("insert into TblLastSearch (Id,EnumTarget,FullMortgage,FullPrice,LocationId,MaxArea,MaxMortgage,MaxPrice,MinArea,MinMortgage,MinPrice,Room,TypeHomeId) values (" + Id + "," + vm_search.getTarget() + ",'" + vm_search.isFullMortgage() + "','" + vm_search.isFullRent() + "'," + vm_search.getLocationId() + "," + MaxArea + "," + MaxMortgage + "," + MaxPrice + "," + MinArea + "," + MinMortgage + "," + MinPrice + "," + vm_search.getCountRoom() + "," + vm_search.getTypeHome() + ")");
        Insert.close();
        GetId.close();
        dbAdapter.close();
    }

}
