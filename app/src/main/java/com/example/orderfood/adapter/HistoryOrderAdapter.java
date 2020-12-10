package com.example.orderfood.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.orderfood.fragments.viewpagerHistoryOrder.CompletedFragment;
import com.example.orderfood.fragments.viewpagerHistoryOrder.CancelOrderFragment;
import com.example.orderfood.fragments.viewpagerHistoryOrder.Ordering_Fragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderAdapter extends FragmentPagerAdapter {
    List<Fragment> list ;
    public HistoryOrderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        list = new ArrayList<>() ;
        list.add(new Ordering_Fragment()) ;
        list.add(new CompletedFragment()) ;
        list.add(new CancelOrderFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Đang Đặt" ;
            case 1:
                return "Hoàn Thành";
            case 2:
                return "Hủy" ;
            default:
                return "" ;
        }
    }
}
