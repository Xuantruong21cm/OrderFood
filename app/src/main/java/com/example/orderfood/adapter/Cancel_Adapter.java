package com.example.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.models.History_Wait;

import java.util.List;

public class Cancel_Adapter extends RecyclerView.Adapter<Cancel_Adapter.ViewHolder>{
    List<History_Wait> list ;
    Context context ;

    public Cancel_Adapter(List<History_Wait> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_cancel,parent,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History_Wait history_wait = list.get(position) ;
        holder.tv_count_dishes_cancel.setText(String.valueOf(history_wait.getDish().size()));
        holder.tv_time_orderhistory_cancel.setText(history_wait.getTime());
        holder.tv_cost_orderhistory_cancel.setText(history_wait.getMoney() + " đ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_count_dishes_cancel, tv_time_orderhistory_cancel,
                tv_cost_orderhistory_cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_count_dishes_cancel = itemView.findViewById(R.id.tv_count_dishes_cancel) ;
            tv_time_orderhistory_cancel = itemView.findViewById(R.id.tv_time_orderhistory_cancel) ;
            tv_cost_orderhistory_cancel = itemView.findViewById(R.id.tv_cost_orderhistory_cancel) ;
        }
    }
}
