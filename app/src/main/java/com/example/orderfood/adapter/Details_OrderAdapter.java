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
import com.example.orderfood.models.Dish;

import java.text.DecimalFormat;
import java.util.List;

public class Details_OrderAdapter extends RecyclerView.Adapter<Details_OrderAdapter.ViewHolder>{
    List<Dish> list ;
    Context context ;

    public Details_OrderAdapter(List<Dish> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_order,parent,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish dish = list.get(position) ;
        holder.tv_nameDish_details.setText(dish.getNameDish());
        holder.tv_count_details.setText("Số Lượng : " + dish.getAmount());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Glide.with(context).load(dish.getImageDish()).into(holder.img_imageDish_deltails) ;
        int price = dish.getAmount() * dish.getPrice() ;
        holder.tv_price_details.setText("Giá Tiền : "+decimalFormat.format(price) +" đ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_imageDish_deltails ;
        TextView tv_nameDish_details, tv_count_details,tv_price_details ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_imageDish_deltails = itemView.findViewById(R.id.img_imageDish_deltails) ;
            tv_nameDish_details = itemView.findViewById(R.id.tv_nameDish_details) ;
            tv_count_details = itemView.findViewById(R.id.tv_count_details) ;
            tv_price_details = itemView.findViewById(R.id.tv_price_details) ;
        }
    }
}
