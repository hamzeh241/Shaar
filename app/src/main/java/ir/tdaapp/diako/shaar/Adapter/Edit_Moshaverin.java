package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Model.List_Moshaverin;

import java.util.List;

/**
 * Created by Diako on 5/26/2019.
 */

public class Edit_Moshaverin extends RecyclerView.Adapter<Edit_Moshaverin.MyViewHolder> {
    List<List_Moshaverin> list_moshaverins;
    Context context;

    public Edit_Moshaverin(List<List_Moshaverin> list_moshaverins,Context context){
        this.list_moshaverins=list_moshaverins;
        this.context=context;
    }
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_edit_moshaverin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt.setText(list_moshaverins.get(position).getName());
        holder.btnEdit.setText("ویرایش");
        boolean temp=list_moshaverins.get(position).isActive();
        if (temp){
            holder.btnActive.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.color.colorBlue));
            holder.btnActive.setText("فعال");
        }
        else {
            holder.btnActive.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.color.colorRed));
            holder.btnActive.setText("غیر فعال");
        }
    }

    @Override
    public int getItemCount() {
        return list_moshaverins.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        Button btnEdit, btnActive;
        TextView txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnActive = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.EditMoshaverin_btnActive);
            btnEdit = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.EditMoshaverin_btnEdit);
            txt = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.EditMoshaverin_txt);
        }
    }

}
