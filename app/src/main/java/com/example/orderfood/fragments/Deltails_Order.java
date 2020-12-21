package com.example.orderfood.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.R;
import com.example.orderfood.adapter.Details_OrderAdapter;
import com.example.orderfood.fragments.viewpagerHistoryOrder.CompletedFragment;
import com.example.orderfood.fragments.viewpagerHistoryOrder.Ordering_Fragment;
import com.example.orderfood.models.Dish;

import java.util.ArrayList;
import java.util.List;


public class Deltails_Order extends Fragment {
    RecyclerView recyclerView_details_order ;
    Details_OrderAdapter adapter ;
    Bundle bundle  ;
    List<Dish> list ;
    int amount ;
    String imageDish ;
    String imageName ;
    int price  ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deltails__order, container, false);
        recyclerView_details_order = view.findViewById(R.id.recyclerView_details_order) ;
        getData();
        adapter = new Details_OrderAdapter(list,getContext()) ;
        recyclerView_details_order.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_details_order.setHasFixedSize(true);
        recyclerView_details_order.setAdapter(adapter) ;

        return view ;
    }
    private void getData(){
        bundle = getArguments() ;
        list = new ArrayList<>();
        if (bundle != null){
            int potision = bundle.getInt("position") ;
            String fragment = bundle.getString("fragment") ;
            if (fragment.equals("ordering")){
                for (int i = 0; i <Ordering_Fragment.list.get(potision).getDish().size() ; i++) {
                    amount = Ordering_Fragment.list.get(potision).getDish().get(i).getAmount();
                    imageDish = Ordering_Fragment.list.get(potision).getDish().get(i).getImageDish();
                    imageName = Ordering_Fragment.list.get(potision).getDish().get(i).getNameDish();
                    price = Ordering_Fragment.list.get(potision).getDish().get(i).getPrice();
                    list.add(new Dish(null,imageDish,imageName,amount,price)) ;
                }
            }else if (fragment.equals("completed")){
                for (int i = 0; i < CompletedFragment.list.get(potision).getDish().size() ; i++) {
                    amount = CompletedFragment.list.get(potision).getDish().get(i).getAmount();
                    imageDish = CompletedFragment.list.get(potision).getDish().get(i).getImageDish();
                    imageName = CompletedFragment.list.get(potision).getDish().get(i).getNameDish();
                    price = CompletedFragment.list.get(potision).getDish().get(i).getPrice();
                    list.add(new Dish(null,imageDish,imageName,amount,price)) ;
                }
            }
        }
    }
}