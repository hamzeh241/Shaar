package ir.tdaapp.diako.shaar.FragmentPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.CarFavoriteItemsFragment;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryDetailsFragmentCityGuide;
import ir.tdaapp.diako.shaar.ETC.LinkedList;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.ETC.Vibrate;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.ViewModel.VM_Search;

import java.math.BigInteger;

/**
 * Created by Diako on 4/24/2019.
 */

public class Fragment_Home extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    ImageView AddHouse, Car, b_information;
    TextView txt_AddHouse, txt_car, txt_NewHome;
    Toolbar mtoolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DBAdapter dbAdapter;
    Dialog_Search_By_Id dialog_search_by_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_home, container, false);

        //در اینجا لیست پشته صفحات را خالی می کند
        Stack_Back.MyStack_Back.RemoveAll();

        LinkedList.My_Linked.SetExit(2);

        FindItem(view);

        CheckTheHaveAccount();

        mtoolbar.setTitle("");


        DrawerLayout drawer = view.findViewById(ir.tdaapp.diako.shaar.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, mtoolbar, ir.tdaapp.diako.shaar.R.string.OpenNavigation, ir.tdaapp.diako.shaar.R.string.CloseNavigation);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, mtoolbar, ir.tdaapp.diako.shaar.R.string.OpenNavigation, ir.tdaapp.diako.shaar.R.string.CloseNavigation);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

//        drawerLayout.openDrawer(Gravity.LEFT);
        HidenItemNavigation();

        OnClick();


        return view;
    }

    void FindItem(View view) {
        AddHouse = view.findViewById(ir.tdaapp.diako.shaar.R.id.Home_AddHouse);
        Car = view.findViewById(ir.tdaapp.diako.shaar.R.id.Home_SearchHouse);
        b_information = view.findViewById(ir.tdaapp.diako.shaar.R.id.Home_NewsHome);

        txt_AddHouse = view.findViewById(ir.tdaapp.diako.shaar.R.id.Home_AddHomeText);
        txt_car = view.findViewById(ir.tdaapp.diako.shaar.R.id.Home_SearchHometext);
        txt_NewHome = view.findViewById(ir.tdaapp.diako.shaar.R.id.NewsHomeText);
        mtoolbar = view.findViewById(ir.tdaapp.diako.shaar.R.id.mtoolbar);
        navigationView = view.findViewById(ir.tdaapp.diako.shaar.R.id.nav_view);
        drawerLayout = view.findViewById(ir.tdaapp.diako.shaar.R.id.drawer_layout);
        dbAdapter = new DBAdapter(getActivity());
        dialog_search_by_id = new Dialog_Search_By_Id();
    }

    void OnClick() {

        AddHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrate.ButtonClick(getActivity());
                SetNews();
                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search", getActivity());
            }
        });
        txt_AddHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrate.ButtonClick(getActivity());
                SetNews();
                Stack_Back.MyStack_Back.Push("Fragment_Resault_Search", getActivity());
            }
        });

        Car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCar = new Intent(getActivity(), CarActivity.class);
                intentCar.putExtra("carList", "car");
                startActivity(intentCar);

            }
        });

        txt_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CarActivity.class));
            }
        });

        b_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Vibrate.ButtonClick(getActivity());
                //  SetNews();
                startActivity(new Intent(getActivity(), GuideActivity.class));
            }
        });

        txt_NewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vibrate.ButtonClick(getActivity());
                // SetNews();
                startActivity(new Intent(getActivity(), GuideActivity.class));
            }
        });
    }

    //در اینجا فیلترها جستجو ست خواهند شد و وارد صفحه املاک می شود
    void SetNews() {
        VM_Search vm_search = new VM_Search();

        vm_search.setTarget(2);
        vm_search.setFullMortgage(false);
        vm_search.setFullRent(false);
        vm_search.setLocationId(0);
        vm_search.setMaxArea(new BigInteger("0"));
        vm_search.setMaxMortgage(new BigInteger("0"));
        vm_search.setMaxPrice(new BigInteger("0"));
        vm_search.setMinArea(new BigInteger("0"));
        vm_search.setMinMortgage(new BigInteger("0"));
        vm_search.setMinPrice(new BigInteger("0"));
        vm_search.setCountRoom(-1);
        vm_search.setTypeHome(0);
        SaveToLastSearch(vm_search);

        Bundle bundle = new Bundle();
        bundle.putInt("Type", 1);
        Stack_Back.MyStack_Back.Push("Fragment_Resault_Search", getContext(), bundle);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case ir.tdaapp.diako.shaar.R.id.navigation_LogIn:
                Stack_Back.MyStack_Back.Push("Fragment_Login_Home", getActivity());
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_MyHome:
                Stack_Back.MyStack_Back.Push("Fragment_MyHome", getActivity());
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_LastSearch:
                Stack_Back.MyStack_Back.Push("Fragment_LastSearch", getActivity());
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_Home:
                drawerLayout.closeDrawers();
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_NewSearch:
                Stack_Back.MyStack_Back.Push("Fragment_Search_Home", getContext());
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_AddHome:
                Stack_Back.MyStack_Back.Push("Fragment_Add_Home", getContext());
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_Like:
                Stack_Back.MyStack_Back.Push("Fragment_Like_Home", getContext());
                break;
            case ir.tdaapp.diako.shaar.R.id.navigation_SearchSaved:
                Stack_Back.MyStack_Back.Push("Fragment_List_Search", getContext());
                break;

            case ir.tdaapp.diako.shaar.R.id.navigation_AboutMe:
                Stack_Back.MyStack_Back.Push("Fragment_About_Me", getContext());
                break;

            case R.id.navigation_carFavorite:

                Intent intentFavCar = new Intent(getActivity(), CarActivity.class);
                intentFavCar.putExtra("FavCar", 1);
                startActivity(intentFavCar);

                break;

            case R.id.navigation_myCar:
                Intent intentMyCar = new Intent(getActivity(), CarActivity.class);
                intentMyCar.putExtra("FavCar", 2);
                startActivity(intentMyCar);
                break;

            case R.id.city_guide_favorite:
                Intent intentBank = new Intent(getActivity(), GuideActivity.class);
                intentBank.putExtra("cityGyideFav", 1);
                startActivity(intentBank);
                break;

            case R.id.city_guide_user_item:
                Intent intentUserItemCityGuide = new Intent(getActivity(), GuideActivity.class);
                intentUserItemCityGuide.putExtra("cityGyideFav", 2);
                startActivity(intentUserItemCityGuide);
                break;


            case R.id.navigation_GetById:
                drawerLayout.closeDrawers();
                dialog_search_by_id.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "Dialog_Search_By_Id");
                break;

            case R.id.navigation_Exit:
                drawerLayout.closeDrawers();

                new AlertDialog.Builder(getContext())
                        .setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>")))
                        .setMessage((Html.fromHtml("<font color='#FF7F27'>آیا می خواهید خارج شوید</font>")))
                        .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>بله</font>")), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        }).setNegativeButton((Html.fromHtml("<font color='#FF7F27'>خیر</font>")), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                break;

            case ir.tdaapp.diako.shaar.R.id.navigation_CallMe:
                Stack_Back.MyStack_Back.Push("Fragment_Call_Me", getContext());
                break;
        }
        return true;
    }

    void HidenItemNavigation() {
        Cursor cursor = dbAdapter.ExecuteQ("select * from TblUser");
        if (cursor != null && cursor.moveToFirst()) {
            int a = cursor.getCount();
            if (a > 0) {
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(ir.tdaapp.diako.shaar.R.id.navigation_LogIn).setVisible(false);
            }
        }

        cursor.close();
    }

    //    در اینجا در اسکیولایت چک می شود که آیا کاربر حساب کاربری دارد یا خیر
    void CheckTheHaveAccount() {

        //در اینجا ما به navigation header دسترسی پیدا خواهیم کرد
        View view = navigationView.getHeaderView(0);
        TextView txt_Name = view.findViewById(ir.tdaapp.diako.shaar.R.id.lblName_NavigationMenu);
        final RelativeLayout btn_NewAccount = view.findViewById(ir.tdaapp.diako.shaar.R.id.BtnNewAccount_NavigationMenu);

        Cursor cursor = dbAdapter.ExecuteQ("select * from TblUser");

        if (cursor != null && cursor.moveToFirst()) {
            int a = cursor.getCount();
            if (a > 0) {
                btn_NewAccount.setVisibility(View.GONE);
                txt_Name.setVisibility(View.VISIBLE);
                txt_Name.setText(cursor.getString(1));
            } else {
                btn_NewAccount.setVisibility(View.VISIBLE);
                txt_Name.setVisibility(View.GONE);
            }
        } else {
            btn_NewAccount.setVisibility(View.VISIBLE);
            txt_Name.setVisibility(View.GONE);
        }

        cursor.close();

        btn_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_NewAccount.setBackground(getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.stroke_new_account_nav2));
                Stack_Back.MyStack_Back.Push("Fragment_Add_Account", getActivity());
            }
        });


    }

}