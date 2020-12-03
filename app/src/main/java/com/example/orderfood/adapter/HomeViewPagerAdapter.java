package com.example.orderfood.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.orderfood.HomeMenu_Fragment;
import com.example.orderfood.ListTable_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.Appetizer_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.Drink_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.Grilled_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.HotPot_Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list = new ArrayList<>();

    public HomeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        list = new ArrayList<>();
        list.add(new AllMenu_Fragment());
        list.add(new Drink_Fragment());
        list.add(new Grilled_Fragment());
        list.add(new HotPot_Fragment());
        list.add(new Appetizer_Fragment());
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
                return "Tất Cả";
            case 1:
                return "Đồ Uống";
            case 2:
                return "Nướng";
            case 3:
                return "Lẩu";
            case 4:
                return "Khai Vị";
            default:
                return "" ;
        }
    }
}
