package ir.tdaapp.diako.shaar.ETC;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ir.tdaapp.diako.shaar.Data.DA_Add_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_About_Me;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Add_Account;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Add_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Call_Me;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Child_Search_List;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Edit_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_LastSearch;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Like_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_List_Search;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Email;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_MyHome;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Property_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Resault_Search;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_SMS_Panel;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Save_Search;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Seting_Moshaverin;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Show_Details_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Show_Images;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Test;
import ir.tdaapp.diako.shaar.FragmentPage.Succefull_Register;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.R;

/**
 * Created by Diako on 6/2/2019.
 */

public class Stack_Back {

    public static class MyStack_Back {

        public static SizeScreen sizeScreen;

        public static void Push(String val, Context context) {

            switch (val) {
                case "Fragment_Add_Account":

                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Add_Account()).commit();
                    break;
                case "Fragment_Add_Home":
                    LinkedList.My_Linked.AddLast(val);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Resault_Search()).commit();
                    break;

                    case "Fragment_Add_Home_fab":
                    LinkedList.My_Linked.AddLast(val);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Add_Home()).commit();
                    break;
                case "Fragment_Edit_Home":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Edit_Home()).commit();
                    break;
                case "Fragment_Home":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Home()).commit();
                    break;
                case "Fragment_Login_Home":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Login_Home()).commit();
                    break;

                case "Fragment_About_Me":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_About_Me()).commit();
                    break;

                case "Fragment_Call_Me":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Call_Me()).commit();
                    break;

                case "Fragment_Property_Home":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main2, new Fragment_Property_Home()).commit();

                    ViewGroup.MarginLayoutParams params_Property_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                    params_Property_Home.setMargins(0, 0, 0, 0);
                    ((MainActivity) context).frameLayout2.setLayoutParams(params_Property_Home);


                    break;
//                case "Fragment_Resault_Search":
//                    LinkedList.My_Linked.AddLast(val);
//                    ((AppCompatActivity) context).getSupportFragmentManager().
//                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Resault_Search()).commit();
//                    break;
//                case "Fragment_Search_Home":
//                    LinkedList.My_Linked.AddLast(val);
//                    ((AppCompatActivity) context).getSupportFragmentManager().
//                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Search_Home()).commit();
//                    break;
//                case "Fragment_Seting_Moshaverin":
//                    LinkedList.My_Linked.AddLast(val);
//                    ((AppCompatActivity) context).getSupportFragmentManager().
//                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Seting_Moshaverin()).commit();
//                    break;
                case "Fragment_Show_Details_Home":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main2, new Fragment_Show_Details_Home()).commit();

                    ViewGroup.MarginLayoutParams params_Show_Details_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                    params_Show_Details_Home.setMargins(0, 0, 0, 0);
                    ((MainActivity) context).frameLayout2.setLayoutParams(params_Show_Details_Home);
                    break;

                case "Fragment_SMS_Panel":
                    LinkedList.My_Linked.AddLast(val);
                    break;

                case "Succefull_Register":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Succefull_Register()).commit();
                    break;

                case "Fragment_MyHome":
                    LinkedList.My_Linked.AddLast(val);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_MyHome()).commit();
                    break;

                case "Fragment_LastSearch":
                    LinkedList.My_Linked.AddLast(val);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_LastSearch()).commit();
                    break;

                case "Fragment_List_Search":
                    LinkedList.My_Linked.AddLast(val);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_List_Search()).commit();
                    break;

                case "Fragment_Like_Home":
                    LinkedList.My_Linked.AddLast(val);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Like_Home()).commit();
                    break;

                case "Fragment_Save_Search":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main2, new Fragment_Save_Search()).commit();

                    ViewGroup.MarginLayoutParams params_Save_Search = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                    params_Save_Search.setMargins(0, 0, 0, 0);
                    ((MainActivity) context).frameLayout2.setLayoutParams(params_Save_Search);

                    break;

                case "Fragment_Test":
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Test()).commit();
                    break;

            }
        }

        public static void Push(String val, final Context context, Bundle bundle) {
            switch (val) {

                case "Fragment_Login_Email":
                    LinkedList.My_Linked.AddLast(val);

                    Fragment_Login_Email fragment_login_email = new Fragment_Login_Email();
                    fragment_login_email.setArguments(bundle);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(R.id.Fragment_Main4, fragment_login_email).commit();

                    ((MainActivity) context).frameLayout4.setVisibility(View.VISIBLE);
                    ((MainActivity) context).frameLayout2.setVisibility(View.GONE);

                    break;

                case "Fragment_Resault_Search":
                    LinkedList.My_Linked.AddLast("Fragment_Resault_Search");
                    Fragment_Resault_Search fragment_resault_search = new Fragment_Resault_Search();
                    fragment_resault_search.setArguments(bundle);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, fragment_resault_search).commit();
                    break;

                case "Fragment_Resault_Search2":
                    Fragment_Resault_Search fragment_resault_search2 = new Fragment_Resault_Search();
                    fragment_resault_search2.setArguments(bundle);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, fragment_resault_search2).commit();
                    break;

                case "Fragment_SMS_Panel":
                    LinkedList.My_Linked.AddLast(val);
                    Fragment_SMS_Panel fragment_sms_panel = new Fragment_SMS_Panel();
                    fragment_sms_panel.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, fragment_sms_panel).commit();
                    break;

                case "Fragment_Child_Search_List":
                    LinkedList.My_Linked.AddLast(val);
                    Fragment_Child_Search_List fragment_child_search_list = new Fragment_Child_Search_List();
                    fragment_child_search_list.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, fragment_child_search_list).commit();
                    break;

                case "Fragment_Show_Details_Home":

                    Fragment_Show_Details_Home fragment_show_details_home = new Fragment_Show_Details_Home();
                    fragment_show_details_home.setArguments(bundle);
                    LinkedList.My_Linked.AddLast(val);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main2, fragment_show_details_home).commit();

                    ViewGroup.MarginLayoutParams params_Show_Details_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                    params_Show_Details_Home.setMargins(0, 0, 0, 0);
                    ((MainActivity) context).frameLayout2.setLayoutParams(params_Show_Details_Home);


                    break;

                case "Fragment_Show_Details_Home2":

                    Fragment_Show_Details_Home fragment_show_details_home2 = new Fragment_Show_Details_Home();
                    fragment_show_details_home2.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main2, fragment_show_details_home2).commit();

                    ViewGroup.MarginLayoutParams params_Show_Details_Home2 = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                    params_Show_Details_Home2.setMargins(0, 0, 0, 0);
                    ((MainActivity) context).frameLayout2.setLayoutParams(params_Show_Details_Home2);
                    break;

                case "Fragment_Show_Images":

                    LinkedList.My_Linked.AddLast(val);

                    Fragment_Show_Images fragment_show_images = new Fragment_Show_Images();
                    fragment_show_images.setArguments(bundle);

                    ((AppCompatActivity) context).getSupportFragmentManager().
                            beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main3, fragment_show_images).commit();

                    ((MainActivity) context).frameLayout3.setVisibility(View.VISIBLE);

                    break;
            }
        }

        public static void Pop(Context context) {
            String val = LinkedList.My_Linked.RemoveLast();


            if (val == "Empty") {

                if (LinkedList.My_Linked.GetExit() == 2) {
                    LinkedList.My_Linked.SetExit(1);
                    Toast.makeText(context, "لطفا بار دیگر دکمه بازگشت را فشار دهید", Toast.LENGTH_SHORT).show();
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            } else {
                switch (val) {
                    case "Fragment_Add_Account":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Add_Account()).commit();
                        break;
                    case "Fragment_Add_Home":
                        ((MainActivity) context).frameLayout2.removeAllViews();

                        ViewGroup.MarginLayoutParams params_Add_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                        params_Add_Home.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                        ((MainActivity) context).frameLayout2.setLayoutParams(params_Add_Home);
                        ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);

                        break;
                    case "Fragment_Edit_Home":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Edit_Home()).commit();
                        break;
                    case "Fragment_Home":
                        try {

                            DA_Add_Home.ClearDA_AddHome();

                            RemoveAll();

                            ((MainActivity) context).frameLayout2.removeAllViews();
                            ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);

                            ViewGroup.MarginLayoutParams param_Show_Details_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();

                            if (param_Show_Details_Home.getMarginEnd() == 0 && param_Show_Details_Home.getMarginStart() == 0) {
                                param_Show_Details_Home.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                                ((MainActivity) context).frameLayout2.removeAllViews();
                                ((MainActivity) context).frameLayout2.setLayoutParams(param_Show_Details_Home);
                            } else {
                                ((AppCompatActivity) context).getSupportFragmentManager().
                                        beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Home()).commit();
                            }

                        } catch (Exception e) {
                            ((AppCompatActivity) context).getSupportFragmentManager().
                                    beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Home()).commit();
                            ViewGroup.MarginLayoutParams param_Show_Details_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();

                            param_Show_Details_Home.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                            ((MainActivity) context).frameLayout2.removeAllViews();
                            ((MainActivity) context).frameLayout2.setLayoutParams(param_Show_Details_Home);
                        }

                        break;
                    case "Fragment_Login_Email":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Login_Email()).commit();
                        break;
                    case "Fragment_Login_Home":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Login_Home()).commit();
                        break;
                    case "Fragment_Property_Home":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Property_Home()).commit();
                        break;
                    case "Fragment_Resault_Search":
                        ((MainActivity) context).frameLayout2.removeAllViews();

                        ViewGroup.MarginLayoutParams params_Show_Details_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                        params_Show_Details_Home.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                        ((MainActivity) context).frameLayout2.setLayoutParams(params_Show_Details_Home);
                        ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);

                        break;

                    case "Fragment_Child_Search_List":
                        ((MainActivity) context).frameLayout2.removeAllViews();

                        ViewGroup.MarginLayoutParams params_Child_Search_List = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                        params_Child_Search_List.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                        ((MainActivity) context).frameLayout2.setLayoutParams(params_Child_Search_List);
                        ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);
                        break;

//                    case "Fragment_Search_Home":
//                        ((AppCompatActivity) context).getSupportFragmentManager().
//                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Search_Home()).commit();
//                        break;
                    case "Fragment_Seting_Moshaverin":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Seting_Moshaverin()).commit();
                        break;
                    case "Fragment_Show_Details_Home":

                        ((MainActivity) context).frameLayout3.setVisibility(View.GONE);
                        ((MainActivity) context).frameLayout3.removeAllViews();
                        ((MainActivity) context).frameLayout2.setVisibility(View.VISIBLE);
                        ((MainActivity)context).frameLayout4.setVisibility(View.GONE);

                        break;
                    case "Fragment_SMS_Panel":
                        DA_Add_Home.ClearDA_AddHome();
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Home()).commit();
                        break;

                    case "Succefull_Register":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Succefull_Register()).commit();
                        break;

                    case "Fragment_About_Me":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_About_Me()).commit();
                        break;

                    case "Fragment_Call_Me":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Call_Me()).commit();
                        break;

                    case "Fragment_MyHome":
                        DA_Add_Home.ClearDA_AddHome();

                        ((MainActivity) context).frameLayout2.removeAllViews();

                        ViewGroup.MarginLayoutParams params_MyHome = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                        if (params_MyHome.getMarginEnd() == 0) {
                            params_MyHome.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                            ((MainActivity) context).frameLayout2.setLayoutParams(params_MyHome);
                            ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);
                        } else {
                            ((AppCompatActivity) context).getSupportFragmentManager().
                                    beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_MyHome()).commit();
                        }
                        break;

                    case "Fragment_LastSearch":
                        ((MainActivity) context).frameLayout2.removeAllViews();

                        ViewGroup.MarginLayoutParams params_LastSearch = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                        params_LastSearch.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                        ((MainActivity) context).frameLayout2.setLayoutParams(params_LastSearch);
                        ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);
                        break;

                    case "Fragment_Like_Home":
                        ((MainActivity) context).frameLayout2.removeAllViews();

                        ViewGroup.MarginLayoutParams params_Like_Home = (ViewGroup.MarginLayoutParams) ((MainActivity) context).frameLayout2.getLayoutParams();
                        params_Like_Home.setMargins(0, 0, sizeScreen.GetWidth(), 0);
                        ((MainActivity) context).frameLayout2.setLayoutParams(params_Like_Home);
                        ((MainActivity) context).frameLayout1.setVisibility(View.VISIBLE);
                        break;

                    case "Fragment_List_Search":
                        DA_Add_Home.ClearDA_AddHome();
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_List_Search()).commit();
                        break;

                    case "Fragment_Test":
                        ((AppCompatActivity) context).getSupportFragmentManager().
                                beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, new Fragment_Test()).commit();
                        break;
                }
            }
        }


        public static void RemoveAll() {
            while (true) {

                if (!LinkedList.My_Linked.IsEmpty()) {
                    LinkedList.My_Linked.RemoveLast();
                } else {
                    break;
                }
            }
        }
    }
}
