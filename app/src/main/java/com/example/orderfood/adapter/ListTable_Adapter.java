package com.example.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.models.Table;

import java.util.List;

public class ListTable_Adapter extends RecyclerView.Adapter<ListTable_Adapter.ViewHolder>{
    List<Table> list ;
    Context context ;

    public ListTable_Adapter(List<Table> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Table table = list.get(position) ;
        holder.tv_nameTable.setText(table.getNameTable());
        if (table.getStatus() == 1){
            holder.tv_tableStatus.setText("Trạng Thái : Trống");
        }else {
            holder.tv_tableStatus.setText("Trạng Thái : Full");
            holder.layout_item_table.setBackgroundResource(R.color.orange);
            holder.btn_order_table.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nameTable ,tv_tableStatus ,btn_order_table ;
        LinearLayout layout_item_table ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameTable = itemView.findViewById(R.id.tv_nameTable) ;
            tv_tableStatus = itemView.findViewById(R.id.tv_tableStatus) ;
            layout_item_table = itemView.findViewById(R.id.layout_item_table) ;
            btn_order_table = itemView.findViewById(R.id.btn_order_table) ;
        }
    }
}
