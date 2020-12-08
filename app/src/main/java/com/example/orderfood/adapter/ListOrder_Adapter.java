package com.example.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.models.ListDish;

import java.util.List;

public class ListOrder_Adapter extends RecyclerView.Adapter<ListOrder_Adapter.ViewHolder> {
    Context context ;
    List<ListDish> list ;
    ItemListOrderListener listener ;

    public ListOrder_Adapter(Context context, List<ListDish> list) {
        this.context = context;
        this.list = list;
    }

    public void deleteItem(ItemListOrderListener listener ){
        this.listener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order,parent,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListDish listDish = list.get(position) ;
        Glide.with(context).load(listDish.getImageDish()).into(holder.img_imageDish_listOrder) ;
        holder.tv_nameDish_listOrder.setText(listDish.getNameDish());
        holder.tv_count_listOrder.setText("Số Lượng: "+listDish.getCount());
        holder.tv_cost_listOrder.setText(listDish.getCost()+" đ");
        holder.tv_price_listOrder.setText(listDish.getPrice());
        holder.img_deleteItem_listOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_imageDish_listOrder, img_deleteItem_listOrder;
        TextView tv_nameDish_listOrder, tv_price_listOrder, tv_count_listOrder, tv_cost_listOrder ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_deleteItem_listOrder = itemView.findViewById(R.id.img_deleteItem_listOrder) ;
            img_imageDish_listOrder = itemView.findViewById(R.id.img_imageDish_listOrder) ;
            tv_nameDish_listOrder = itemView.findViewById(R.id.tv_nameDish_listOrder) ;
            tv_price_listOrder = itemView.findViewById(R.id.tv_price_listOrder) ;
            tv_count_listOrder = itemView.findViewById(R.id.tv_count_listOrder) ;
            tv_cost_listOrder = itemView.findViewById(R.id.tv_cost_listOrder) ;
        }
    }

    public interface ItemListOrderListener{
        void delete(int position) ;
    }
}
