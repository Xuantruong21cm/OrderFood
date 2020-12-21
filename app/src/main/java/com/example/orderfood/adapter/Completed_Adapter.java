package com.example.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.interfaces.Detail_Order;
import com.example.orderfood.models.History_Wait;

import java.text.DecimalFormat;
import java.util.List;

public class Completed_Adapter extends RecyclerView.Adapter<Completed_Adapter.ViewHolder>{
    List<History_Wait> list ;
    Context context ;
    Detail_Order onClick ;

    public void Details_OnClick(Detail_Order onClick){
        this.onClick = onClick ;
    }

    public Completed_Adapter(List<History_Wait> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_completed,parent,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History_Wait history_wait = list.get(position) ;
        holder.tv_count_dishes_completed.setText(String.valueOf(list.get(position).getDish().size()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_cost_orderhistory_completed.setText(decimalFormat.format(history_wait.getMoney())+ " đ");
        holder.tv_time_orderhistory_completed.setText(history_wait.getTime());
        holder.btn_details_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick_Details(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_count_dishes_completed, tv_time_orderhistory_completed,
                tv_cost_orderhistory_completed, btn_details_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_count_dishes_completed = itemView.findViewById(R.id.tv_count_dishes_completed) ;
            tv_time_orderhistory_completed = itemView.findViewById(R.id.tv_time_orderhistory_completed) ;
            tv_cost_orderhistory_completed = itemView.findViewById(R.id.tv_cost_orderhistory_completed) ;
            btn_details_order = itemView.findViewById(R.id.btn_details_order) ;
        }
    }
}
