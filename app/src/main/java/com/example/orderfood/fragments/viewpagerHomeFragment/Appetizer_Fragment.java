package com.example.orderfood.fragments.viewpagerHomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.R;
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.activities.MainActivity;


public class Appetizer_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_appetizer_, container, false);

        return view ;
    }
}