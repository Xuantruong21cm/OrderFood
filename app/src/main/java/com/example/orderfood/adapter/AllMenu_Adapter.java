package com.example.orderfood.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.orderfood.R;
import com.example.orderfood.model.Food;

import java.util.List;

public class AllMenu_Adapter extends RecyclerView.Adapter<AllMenu_Adapter.ViewHolder>{
    Context context ;
    List<Food> list ;

    public AllMenu_Adapter(Context context, List<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = list.get(position) ;
        Glide.with(context).asBitmap().load(food.getImageDish()).into(holder.img_imageDish) ;
        holder.tv_nameDish.setText(food.getNameDish());
        holder.tv_time.setText(food.getTime() + " phút");
        holder.tv_price.setText(food.getPrice() + " đ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_imageDish, img_addOder ;
        TextView tv_nameDish, tv_price, tv_time ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_addOder = itemView.findViewById(R.id.img_addOder) ;
            img_imageDish = itemView.findViewById(R.id.img_imageDish);
            tv_nameDish = itemView.findViewById(R.id.tv_nameDish) ;
            tv_price = itemView.findViewById(R.id.tv_price) ;
            tv_time = itemView.findViewById(R.id.tv_time) ;
        }
    }
}