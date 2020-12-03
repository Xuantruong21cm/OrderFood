package com.example.orderfood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.orderfood.adapter.HomeViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeMenu_Fragment extends Fragment {
    Toolbar toolbar ;
    TabLayout tabLayout ;
    ViewPager viewPager ;
    HomeViewPagerAdapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu_, container, false);
        initUI(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar) ;
        setHasOptionsMenu(true);
        adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager(),1);
        viewPager.setAdapter(adapter) ;
        tabLayout.setupWithViewPager(viewPager) ;
        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i <tabs.getChildCount() ; i++) {
            View tab = tabs.getChildAt(i) ;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = 0 ;
            layoutParams.setMargins(0,22,0,22);
            tab.setLayoutParams(layoutParams);
            tabLayout.requestLayout();
        }


        return view ;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_home,menu);
        return;
    }

    private void initUI(View view) {
        toolbar = view.findViewById(R.id.toolbar_homeFragment) ;
        tabLayout = view.findViewById(R.id.tablayout_home) ;
        viewPager = view.findViewById(R.id.view_pager) ;
    }

}