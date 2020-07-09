package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Diako on 5/12/2019.
 */

public class CountRoom extends RecyclerView.Adapter<CountRoom.MyViewHolder> {

    Context context;
    String[] val;
    MyViewHolder myViewHolder;
    int CountRoom=-1;

    public CountRoom(Context context, String[] val) {
        this.context = context;
        this.val = val;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_count_room, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt.setText(val[position]);

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myViewHolder==null){

                    holder.Layout.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                    holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));

                    myViewHolder=holder;

                    if (position==0)
                        CountRoom=0;
                    else
                        CountRoom=Integer.parseInt(val[position]);
                }else{

                    if (position==CountRoom){
                        holder.Layout.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.border_card_view_count_room));
                        holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlack));

                        myViewHolder=null;

                        CountRoom=-1;
                    }else{

                        myViewHolder.Layout.setBackground(context.getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.border_card_view_count_room));
                        myViewHolder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlack));

                        holder.Layout.setBackgroundColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorBlue));
                        holder.txt.setTextColor(context.getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorWhite));

                        myViewHolder=holder;

                        if (position==0)
                            CountRoom=0;
                        else
                            CountRoom=Integer.parseInt(val[position]);

                    }

                }
            }
        });
   }

    @Override
    public int getItemCount() {
        return val.length;
    }

    public int GetCountRoom(){
        return CountRoom;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt;
        LinearLayout Layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_CountRoom_SubTitle);
            Layout = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_CountRoom_Layout);
        }
    }
}
