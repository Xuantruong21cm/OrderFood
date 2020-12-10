package com.example.orderfood.fragments.viewpagerHistoryOrder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.orderfood.R;
import com.example.orderfood.adapter.HistoryOrderAdapter;
import com.google.android.material.tabs.TabLayout;


public class HistoryOrder_Fragment extends Fragment {
    TabLayout tablayout_historyOrder ;
    ViewPager viewpager_HistoryOrder ;
    HistoryOrderAdapter adapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_order_, container, false);
        viewpager_HistoryOrder = view.findViewById(R.id.viewpager_HistoryOrder) ;
        tablayout_historyOrder = view.findViewById(R.id.tablayout_historyOrder) ;
        adapter = new HistoryOrderAdapter(getChildFragmentManager(),1) ;
        viewpager_HistoryOrder.setAdapter(adapter) ;
        tablayout_historyOrder.setupWithViewPager(viewpager_HistoryOrder) ;
        ViewGroup tabs = (ViewGroup) tablayout_historyOrder.getChildAt(0);
        for (int i = 0; i <tabs.getChildCount() ; i++) {
            View tab = tabs.getChildAt(i) ;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = 0 ;
            layoutParams.setMargins(0,22,0,22);
            tab.setLayoutParams(layoutParams);
            tablayout_historyOrder.requestLayout();
        }
        return  view ;
    }
}