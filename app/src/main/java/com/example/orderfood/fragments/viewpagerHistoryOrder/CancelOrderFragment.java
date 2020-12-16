package com.example.orderfood.fragments.viewpagerHistoryOrder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.R;
import com.example.orderfood.adapter.Cancel_Adapter;
import com.example.orderfood.adapter.Completed_Adapter;
import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.models.History_Wait;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class CancelOrderFragment extends Fragment {
    RecyclerView recyclerView_history_cancel ;
    Cancel_Adapter adapter ;
    List<History_Wait> list ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancel_order, container, false);
        recyclerView_history_cancel = view.findViewById(R.id.recyclerView_history_cancel) ;
        getData();
        return view ;
    }
    private void getData(){
        list = new ArrayList<>() ;
        Gson gson = new Gson();
        History_Wait history_wait ;
        for (int i = 0; i < AllMenu_Fragment.history_waits.size() ; i++) {
            history_wait =gson.fromJson(AllMenu_Fragment.history_waits.get(i).toString(),History_Wait.class);
            if (history_wait.getStatus() == 3){
                list.add(history_wait) ;
            }
        }
        adapter = new Cancel_Adapter(list,getContext()) ;
        recyclerView_history_cancel.setHasFixedSize(true);
        recyclerView_history_cancel.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_history_cancel.setAdapter(adapter) ;
    }
}