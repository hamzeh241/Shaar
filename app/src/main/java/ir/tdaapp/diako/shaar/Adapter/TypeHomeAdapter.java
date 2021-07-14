package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import ir.tdaapp.diako.shaar.Data.DA_Add_Home;
import ir.tdaapp.diako.shaar.Model.Type_Home;
import ir.tdaapp.diako.shaar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/11/2019.
 */

public class TypeHomeAdapter extends RecyclerView.Adapter<TypeHomeAdapter.MyViewHolder> {

    Context context;
    List<Type_Home> type_homes;
    MyViewHolder holder2;
    RelativeLayout Property_Home;
    TextView lbl_Property,lbl_AgeHome,lbl_CountRoom;
    Spinner cmb_AgeHome,cmb_CountRoom,cmb_Location;
    ScrollView scroll;
    DBAdapter dbAdapter;

    public TypeHomeAdapter(Context context, List<Type_Home> type_homes,View view,DBAdapter dbAdapter) {
        this.context = context;
        this.type_homes = type_homes;
        Property_Home=view.findViewById(R.id.Property_Home);
        lbl_Property=view.findViewById(R.id.lbl_Property);
        lbl_AgeHome=view.findViewById(R.id.lbl_Year_of_construction);
        cmb_AgeHome=view.findViewById(R.id.AgeHome);
        lbl_CountRoom=view.findViewById(R.id.lbl_CountRoom);
        scroll=view.findViewById(R.id.scroll);
        cmb_CountRoom=view.findViewById(R.id.cmb_CountRoom);
        cmb_Location=view.findViewById(R.id.Fragment_Add_Home_cmb_Location);
        this.dbAdapter=dbAdapter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_type_home,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txt.setText(type_homes.get(position).GetText());

        //در اینجا کلاس استاتیک را چک کرده و اگر با پوزیشن برابر باشد آیتم را در حالت Select در می آورد
        if (type_homes.get(position).getId()==DA_Add_Home.TypeHome){
            holder.rel.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
            holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
            DA_Add_Home.TypeHome=type_homes.get(position).getId();
            DA_Add_Home.TitleProperty=type_homes.get(position).GetText();
            holder2=holder;
            Property_Home.setVisibility(View.VISIBLE);
            lbl_Property.setVisibility(View.VISIBLE);
            Visible_Age_CountRoom();
        }

        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DA_Add_Home.TypeHome==0){
                    holder.rel.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                    holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
                    DA_Add_Home.TypeHome=type_homes.get(position).getId();
                    DA_Add_Home.TitleProperty=type_homes.get(position).GetText();
                    holder2=holder;
                    Property_Home.setVisibility(View.VISIBLE);
                    lbl_Property.setVisibility(View.VISIBLE);
                    DA_Add_Home.selects=new ArrayList<>();
                    DA_Add_Home.texts=new ArrayList<>();
                    DA_Add_Home.numbers=new ArrayList<>();
                    DA_Add_Home.booleans=new ArrayList<>();
                }
                else if (type_homes.get(position).getId()== DA_Add_Home.TypeHome){
                    holder.rel.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.border_card_view_type_home));
                    holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                    DA_Add_Home.TitleProperty="";
                    DA_Add_Home.TypeHome=0;
                    Property_Home.setVisibility(View.GONE);
                    lbl_Property.setVisibility(View.GONE);
                    DA_Add_Home.selects=new ArrayList<>();
                    DA_Add_Home.texts=new ArrayList<>();
                    DA_Add_Home.numbers=new ArrayList<>();
                    DA_Add_Home.booleans=new ArrayList<>();
                    HideAllAgeCount();
                }
                else{
                    holder.rel.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                    holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
                    DA_Add_Home.TypeHome=type_homes.get(position).getId();
                    DA_Add_Home.TitleProperty=type_homes.get(position).GetText();

                    if (holder2!=null) {
                        holder2.rel.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.border_card_view_type_home));
                        holder2.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                    }
                    holder2=holder;
                    Property_Home.setVisibility(View.VISIBLE);
                    lbl_Property.setVisibility(View.VISIBLE);
                    DA_Add_Home.selects=new ArrayList<>();
                    DA_Add_Home.texts=new ArrayList<>();
                    DA_Add_Home.numbers=new ArrayList<>();
                    DA_Add_Home.booleans=new ArrayList<>();
                }

                if (DA_Add_Home.TypeHome==31){
                    Cursor locations=dbAdapter.executeQuery("select * from TblLocation ORDER BY Title");
                    int positionSpiner=0;

                    locations.moveToFirst();
                    for (int i=0;i<locations.getCount();i++){
                        if (locations.getInt(0)==62){
                            positionSpiner=i;
                            break;
                        }
                        locations.moveToNext();
                    }
                    cmb_Location.setSelection(positionSpiner);
                    cmb_Location.setEnabled(false);
                    locations.close();
                    dbAdapter.close();
                }else {
                    cmb_Location.setSelection(0);
                    cmb_Location.setEnabled(true);
                }

                Visible_Age_CountRoom();
            }
        });
    }

    void ShowAgeCount(int n){

        if (n==0){
            if (lbl_AgeHome.getVisibility()!=View.VISIBLE) {
                lbl_AgeHome.setVisibility(View.VISIBLE);
            }
            if (lbl_CountRoom.getVisibility()!=View.VISIBLE) {
                lbl_CountRoom.setVisibility(View.VISIBLE);
            }
            if (cmb_AgeHome.getVisibility()!=View.VISIBLE) {
                cmb_AgeHome.setVisibility(View.VISIBLE);
            }
            if (cmb_CountRoom.getVisibility()!=View.VISIBLE) {
                cmb_CountRoom.setVisibility(View.VISIBLE);
            }
        }else if (n==1){
            if (cmb_AgeHome.getVisibility()!=View.GONE) {
                cmb_AgeHome.setVisibility(View.GONE);
            }
            if (lbl_AgeHome.getVisibility()!=View.GONE) {
                lbl_AgeHome.setVisibility(View.GONE);
            }
            if (lbl_CountRoom.getVisibility()!=View.VISIBLE) {
                lbl_CountRoom.setVisibility(View.VISIBLE);
            }
            if (cmb_CountRoom.getVisibility()!=View.VISIBLE) {
                cmb_CountRoom.setVisibility(View.VISIBLE);
            }
        }
        else if (n==2){
            if (lbl_CountRoom.getVisibility()!=View.GONE) {
                lbl_CountRoom.setVisibility(View.GONE);
            }
            if (cmb_CountRoom.getVisibility()!=View.GONE) {
                cmb_CountRoom.setVisibility(View.GONE);
            }
            if (lbl_AgeHome.getVisibility()!=View.VISIBLE) {
                lbl_AgeHome.setVisibility(View.VISIBLE);
            }
            if (cmb_AgeHome.getVisibility()!=View.VISIBLE) {
                cmb_AgeHome.setVisibility(View.VISIBLE);
            }
        }
    }

    void HideAllAgeCount(){
        if (lbl_AgeHome.getVisibility()!=View.GONE) {
            lbl_AgeHome.setVisibility(View.GONE);
        }
        if (lbl_CountRoom.getVisibility()!=View.GONE) {
            lbl_CountRoom.setVisibility(View.GONE);
        }
        if (cmb_AgeHome.getVisibility()!=View.GONE) {
            cmb_AgeHome.setVisibility(View.GONE);
        }
        if (cmb_CountRoom.getVisibility()!=View.GONE) {
            cmb_CountRoom.setVisibility(View.GONE);
        }
    }

    //برای نمایش دادن سن بنا و تعداد اتاق
    void Visible_Age_CountRoom(){

        int id=DA_Add_Home.TypeHome;

        if (id==3){
            ShowAgeCount(0);
        }else if (id == 4) {
            ShowAgeCount(0);
        }else if (id == 9) {
            ShowAgeCount(0);
        }else if (id == 18) {
            ShowAgeCount(0);
        }else if (id == 27) {
            ShowAgeCount(2);
        }else if (id == 28) {
            ShowAgeCount(1);
        }else if (id == 29) {
            ShowAgeCount(0);
        }else if (id == 37) {
            ShowAgeCount(2);
        }else if (id == 38) {
            ShowAgeCount(2);
        }else if (id == 39) {
            ShowAgeCount(2);
        }else if (id == 5) {
            ShowAgeCount(0);
        }else if (id == 6) {
            ShowAgeCount(0);
        }else if (id == 7) {
            ShowAgeCount(0);
        }else if (id == 26) {
            ShowAgeCount(0);
        }else if (id == 31) {
            ShowAgeCount(1);
        }else if (id == 24) {
            ShowAgeCount(0);
        }else if (id == 40) {
            ShowAgeCount(2);
        }else if (id == 41) {
            ShowAgeCount(2);
        }else if (id == 8) {
            ShowAgeCount(0);
        }else if (id == 33) {
            ShowAgeCount(2);
        }else if (id == 34) {
            ShowAgeCount(2);
        }else if (id == 35) {
            ShowAgeCount(2);
        }else if (id == 36) {
            ShowAgeCount(2);
        }
    }

    @Override
    public int getItemCount() {
        return type_homes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt;
        RelativeLayout rel;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_Type_Home_SubTitle);
            rel=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Type_Home_Relative);
        }
    }
}
