package com.example.orderfood.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.adapter.HomeViewPagerAdapter;
import com.example.orderfood.fragments.viewpagerHistoryOrder.HistoryOrder_Fragment;
import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.models.Food;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeMenu_Fragment extends Fragment {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu_, container, false);
        initUI(view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        adapter = new HomeViewPagerAdapter(getChildFragmentManager(), 1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i < tabs.getChildCount(); i++) {
            View tab = tabs.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = 0;
            layoutParams.setMargins(0, 22, 0, 22);
            tab.setLayoutParams(layoutParams);
            tabLayout.requestLayout();
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_home, menu);
        SearchView searchView;
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_homefragment)
                .getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                      @Override
                                                      public boolean onQueryTextSubmit(String query) {
                                                          filter(query);
                                                          return false;
                                                      }

                                                      @Override
                                                      public boolean onQueryTextChange(String query) {
                                                            filter(query);
                                                          return false;
                                                      }
                                                  });
        return;
    }
    private void filter(String s){
        List<Food> list = new ArrayList<>();
        for (Food food : AllMenu_Fragment.list){
            if (food.getNameDish().toLowerCase().contains(s.toLowerCase())){
                list.add(food) ;
            }
        }
        AllMenu_Fragment.adapter.filterList(list) ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.control_homefragment:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new HistoryOrder_Fragment();
                transaction.replace(R.id.fragment, fragment).commit();
                transaction.addToBackStack(fragment.getClass().getSimpleName());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI(View view) {
        toolbar = view.findViewById(R.id.toolbar_homeFragment);
        tabLayout = view.findViewById(R.id.tablayout_home);
        viewPager = view.findViewById(R.id.view_pager);
    }

}