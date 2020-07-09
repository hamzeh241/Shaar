package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ir.tdaapp.diako.shaar.Model.List_Moshaverin;

import java.util.List;

/**
 * Created by Diako on 5/25/2019.
 */

public class Record_Moshaverin extends RecyclerView.Adapter<Record_Moshaverin.MyViewHolder> {

    Context context;
    List<List_Moshaverin> list_moshaverins;

    public Record_Moshaverin(Context context, List<List_Moshaverin> list_moshaverins) {
        this.context = context;
        this.list_moshaverins = list_moshaverins;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_record_moshaverin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int temp = list_moshaverins.get(position).getType();
        if (temp == 1) {
            holder.btn.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.color.colorBlue));
            holder.btn.setText("تایید شده");
        } else {
            holder.btn.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.color.colorRed));
            holder.btn.setText("تایید نشده");
        }
        holder.txt.setText(list_moshaverins.get(position).getName());
        holder.btn.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));
    }

    @Override
    public int getItemCount() {
        return list_moshaverins.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.TextRecordMoshaverin);
            btn = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.btnRecordMoshaverin);
        }
    }
}
