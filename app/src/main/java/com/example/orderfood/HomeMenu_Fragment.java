package com.example.orderfood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeMenu_Fragment extends Fragment {
    Toolbar toolbar ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu_, container, false);
        initUI(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar) ;
        setHasOptionsMenu(true);


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
    }

}