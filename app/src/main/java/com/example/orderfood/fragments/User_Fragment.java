package com.example.orderfood.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.fragments.viewpagerHistoryOrder.HistoryOrder_Fragment;

public class User_Fragment extends Fragment {
    LinearLayout layout_history_order, layout_location,
            layout_support , layout_logout ;
    TextView tv_userName ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_, container, false);
        initUI(view) ;
        tv_userName.setText(LoginActivity.username);
        initListener();

        return view ;
    }

    private void initListener() {
        layout_history_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                FragmentTransaction transaction = fragmentManager.beginTransaction() ;
                Fragment fragment = new HistoryOrder_Fragment() ;
                transaction.replace(R.id.fragment,fragment).commit() ;
                transaction.addToBackStack(fragment.getClass().getSimpleName()) ;
            }
        });

        layout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
                builder.setTitle("Đăng Xuất !!!") ;
                builder.setMessage("Bạn Có Muốn Đăng Xuất Không ?") ;
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(),LoginActivity.class) ;
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void initUI(View view) {
        layout_history_order = view.findViewById(R.id.layout_history_order) ;
        layout_location = view.findViewById(R.id.layout_location) ;
        layout_support = view.findViewById(R.id.layout_support) ;
        layout_logout = view.findViewById(R.id.layout_logout) ;
        tv_userName = view.findViewById(R.id.tv_userName) ;
    }
}