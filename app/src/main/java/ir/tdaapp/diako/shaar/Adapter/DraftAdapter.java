package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.database.Cursor;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Data.DA_Add_Home;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Boolean;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Number;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Select;
import ir.tdaapp.diako.shaar.Data.DA_TypeHome_Text;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Add_Home;
import ir.tdaapp.diako.shaar.Model.Draft_Home;
import ir.tdaapp.diako.shaar.Model.Type_Home;
import ir.tdaapp.diako.shaar.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/8/2019.
 */

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.MyViewHolder> {

    Context context;
    List<Draft_Home> values;
    DBAdapter dbAdapter;
    ProgressBar progress;

    public DraftAdapter(Context context, List<Draft_Home> values, View viewPage) {
        this.context = context;
        this.values = values;
        dbAdapter = new DBAdapter(context);
        progress = viewPage.findViewById(R.id.loadingdata_progress);
        progress.setVisibility(View.GONE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_my_home, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Address.setText(values.get(position).getAddress());
        holder.CountRoom.setText(String.valueOf(values.get(position).getCountRoom()));
        holder.Price.setText(values.get(position).getPrice());
        holder.img.setImageBitmap(values.get(position).getImage());

        final Animation slide_in = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_left);

        final Animation slide_out = AnimationUtils.loadAnimation(context,
                R.anim.slide_out_left);

        holder.the_operation.setVisibility(View.GONE);
        holder.ThreePoint.setVisibility(View.VISIBLE);

        holder.ThreePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.the_operation.startAnimation(slide_in);
                holder.ThreePoint.startAnimation(slide_out);
                holder.the_operation.setVisibility(View.VISIBLE);
                holder.ThreePoint.setVisibility(View.GONE);
            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.the_operation.startAnimation(slide_out);
                holder.ThreePoint.startAnimation(slide_in);
                holder.the_operation.setVisibility(View.GONE);
                holder.ThreePoint.setVisibility(View.VISIBLE);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor del = dbAdapter.executeQuery("delete from TblItem where Id=" + values.get(position).getId());
                values.remove(position);
                notifyDataSetChanged();
            }
        });

        //این بخش دکمه ویرایش می باشد که با فشار دادن آن وارد صفحه ویرایش خواهد شد
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToHome(position);
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToHome(position);
            }
        });


    }

    //در اینجا کاربر با انتخاب یک آیتم وارد جزئیات آن می شود
    void GoToHome(int position) {
        //در اینجا progressbar لودینگ نمایش داده می شود
        progress.setVisibility(View.VISIBLE);

        Cursor cursor = dbAdapter.executeQuery("select * from TblItem where Id=" + values.get(position).getId());

        MyType myType = GetTypeHomeName(Integer.parseInt(cursor.getString(0)));

        DA_Add_Home.txt_Address = cursor.getString(10);
        DA_Add_Home.txt_CodePosty = cursor.getString(9);
        DA_Add_Home.txt_VideoLink = cursor.getString(6);
        DA_Add_Home.txt_TheArea = cursor.getString(3);
        DA_Add_Home.txt_Rent = cursor.getString(2);
        DA_Add_Home.txt_Price = cursor.getString(2);
        DA_Add_Home.txt_Phone = cursor.getString(5);
        DA_Add_Home.txt_Mortgage = cursor.getString(18);
        DA_Add_Home.txt_Mob = cursor.getString(4);
        DA_Add_Home.txt_DiscriptionHome = cursor.getString(19);
        DA_Add_Home.txt_AdTitle = cursor.getString(1);
        DA_Add_Home.TitleProperty = myType.Title;
        DA_Add_Home.ItemId = values.get(position).getId();

        //در اینجا بخش مربوط به مسکونی یا تجاری یا صنعتی بررسی می شود
        DA_Add_Home.rdo_Industrial = false;
        DA_Add_Home.rdo_Commercial = false;
        DA_Add_Home.rdo_Residential = false;

        if (myType.GetParentId == 0) {
            if (myType.Id == 1)
                DA_Add_Home.rdo_Residential = true;
            else if (myType.Id == 2)
                DA_Add_Home.rdo_Commercial = true;
            else
                DA_Add_Home.rdo_Industrial = true;
        } else {
            if (myType.GetParentId == 1)
                DA_Add_Home.rdo_Residential = true;
            else if (myType.GetParentId == 2)
                DA_Add_Home.rdo_Commercial = true;
            else
                DA_Add_Home.rdo_Industrial = true;
        }

        //در اینجا خرید یا فروش بودن آیتم چک می شود
        int Target = Integer.parseInt(cursor.getString(11));
        DA_Add_Home.rdo_Sell = false;
        DA_Add_Home.rdo_Rent = false;

        if (Target == 0)
            DA_Add_Home.rdo_Rent = true;
        else
            DA_Add_Home.rdo_Sell = true;

        DA_Add_Home.CountRoom = Integer.parseInt(cursor.getString(7));
        DA_Add_Home.TypeHome = Integer.parseInt(cursor.getString(13));


        int PositionLocation = 0;
        Cursor cursor2 = dbAdapter.executeQuery("select * from TblLocation ORDER BY Title");
        if (cursor2 != null && cursor2.moveToFirst()) {
            while (!cursor2.isAfterLast()) {

                if (Integer.parseInt(cursor2.getString(0)) == Integer.parseInt(cursor.getString(14))){
                    break;
                }
                PositionLocation++;
                cursor2.moveToNext();
            }
        }
        cursor2.close();

        DA_Add_Home.cmb_Location = PositionLocation;

        DA_Add_Home.cmb_Year_of_construction = 1405 - Integer.parseInt(cursor.getString(8));

        Cursor ItemFeatures = dbAdapter.executeQuery("select * from TblItemFeatuers where FkItem=" + cursor.getString(0));

        DA_Add_Home.booleans = new ArrayList<>();
        DA_Add_Home.selects = new ArrayList<>();
        DA_Add_Home.numbers = new ArrayList<>();
        DA_Add_Home.texts = new ArrayList<>();
        //در اینجا ویژگی های منزل set می شوند
        if (ItemFeatures != null && ItemFeatures.moveToFirst()) {

            while (!ItemFeatures.isAfterLast()) {
                int Type = Integer.parseInt(ItemFeatures.getString(3));

                if (Type == 1) {
                    int Id = Integer.parseInt(ItemFeatures.getString(2));
                    String Val = ItemFeatures.getString(5);
                    DA_Add_Home.texts.add(new DA_TypeHome_Text(Id, Val));
                } else if (Type == 2) {
                    int Id = Integer.parseInt(ItemFeatures.getString(2));
                    Boolean Val = Boolean.parseBoolean(ItemFeatures.getString(5));
                    DA_Add_Home.booleans.add(new DA_TypeHome_Boolean(Id, Val));
                } else if (Type == 3) {
                    int Id = Integer.parseInt(ItemFeatures.getString(2));
                    int Val = Integer.parseInt(ItemFeatures.getString(5));
                    DA_Add_Home.numbers.add(new DA_TypeHome_Number(Id, Val));
                } else {
                    int Id = Integer.parseInt(ItemFeatures.getString(2));
                    String Val = ItemFeatures.getString(5);
                    int Position = 0;
                    Cursor GetPosition = dbAdapter.executeQuery("select [Option] from TblFeatures where Id=" + Id);
                    if (GetPosition != null && GetPosition.moveToFirst()) {
                        String c = GetPosition.getString(0);
                        String[] DataArray = c.split(",");

                        for (int i = 0; i < DataArray.length; i++) {
                            if (DataArray[i].equalsIgnoreCase(Val)) {
                                Position = i;
                            }
                        }

                        DA_Add_Home.selects.add(new DA_TypeHome_Select(Id, Val, Position));
                        GetPosition.close();
                    }
                }

                ItemFeatures.moveToNext();
            }
        }
        ItemFeatures.close();
        dbAdapter.close();
        progress.setVisibility(View.GONE);
//        Stack_Back.MyStack_Back.Push("Fragment_Add_Home", context);
        ((MainActivity)context).onAddFragment(new Fragment_Add_Home(),0,0,true,Fragment_Add_Home.TAG);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    //این متد برای گرفتن بخشی از اطلاعات TypeHome
    MyType GetTypeHomeName(int id) {
        List<Type_Home> lst = new ArrayList<>();
        MyType myType = new MyType();
        Cursor cursor = dbAdapter.executeQuery("select TblType.Id,TblType.Titel,TblType.ParentId from TblType inner join TblItem on TblType.Id=TblItem.FkType where TblItem.Id=" + id);
        if (cursor != null && cursor.moveToFirst()) {
            myType.Id = Integer.parseInt(cursor.getString(0));
            myType.Title = cursor.getString(1);
            myType.ParentId = Integer.parseInt(cursor.getString(2));
        }
        cursor.close();

        if (myType.ParentId != 0) {
            Cursor c = dbAdapter.executeQuery("SELECT Id from TblType where Id=" + myType.ParentId);
            if (c != null && c.moveToFirst()) {
                myType.GetParentId = Integer.parseInt(c.getString(0));
            }
            c.close();
        }
        dbAdapter.close();
        return myType;
    }

    //در این کلاس داده های از Id, Title , Parent Id که مربوط به TblType می باشد نگهداری می شود
    class MyType {
        private int Id;
        private String Title;
        private int ParentId;

        //در اینجا اگر parentId نامساوی صفر باشد سردسته او را در در این فیلد قرار داده می شود
        private int GetParentId = 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img, edit, remove, close, ThreePoint;
        TextView Price, CountRoom, Address;
        CardView item;
        RelativeLayout the_operation;

        public MyViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            Price = itemView.findViewById(R.id.price);
            CountRoom = itemView.findViewById(R.id.CountRoom);
            Address = itemView.findViewById(R.id.address);
            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);
            close = itemView.findViewById(R.id.close);
            ThreePoint = itemView.findViewById(R.id.ThreePoint);
            the_operation = itemView.findViewById(R.id.the_operation);
            item = itemView.findViewById(R.id.item);
        }
    }
}
