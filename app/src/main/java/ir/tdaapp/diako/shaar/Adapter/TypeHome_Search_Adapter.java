package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.tdaapp.diako.shaar.Model.Type_Home;

import java.util.List;

/**
 * Created by Diako on 7/16/2019.
 */

public class TypeHome_Search_Adapter extends RecyclerView.Adapter<TypeHome_Search_Adapter.MyViewHolder> {

    Context context;
    List<Type_Home> values;
    MyViewHolder myViewHolder;
    int Id=0;

    public TypeHome_Search_Adapter(Context context,List<Type_Home> values){
        this.context=context;
        this.values=values;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_type_home,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt.setText(values.get(position).GetText());

        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                * در اینجا زمانی که کاربر یکی از آیتم ها را انتخاب می کند رنگ آن آیتم را تغییر پیدا می کند و Id آن آیتم برداشته می شود
                * */
                if (myViewHolder==null){
                    holder.rel.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                    holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
                    myViewHolder=holder;
                    Id=values.get(position).getId();
                }else{
                    if (values.get(position).getId()==Id){
                        holder.rel.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.border_card_view_type_home));
                        holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                        myViewHolder=null;
                        Id=0;
                    }else {
                        myViewHolder.rel.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.border_card_view_type_home));
                        myViewHolder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                        holder.rel.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                        holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
                        myViewHolder = holder;
                        Id=values.get(position).getId();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void SetId(int Id){
        this.Id=Id;
    }

    public int GetId(){
        return this.Id;
    }

    public void RemoveVal(){
        Id=0;
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
