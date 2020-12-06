package com.example.orderfood.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.Appetizer_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.Drink_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.MainCourse_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.Dessert_Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list ;

    public HomeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        list = new ArrayList<>();
        list.add(new AllMenu_Fragment()) ;
        list.add(new Appetizer_Fragment());
        list.add(new MainCourse_Fragment());
        list.add(new Dessert_Fragment());
        list.add(new Drink_Fragment());
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
                return "Tất Cả" ;
            case 1:
                return "Món Khai Vị";
            case 2:
                return "Món Chính";
            case 3:
                return "Món Tráng Miệng";
            case 4:
                return "Đồ Uống";
            default:
                return "" ;
        }
    }
}
