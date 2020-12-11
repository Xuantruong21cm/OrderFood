package com.example.orderfood.fragments.viewpagerHistoryOrder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.orderfood.R;
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.adapter.Ordering_Adapter;
import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.models.History_Wait;
import com.example.orderfood.ultils.BaseUrl;
import com.example.orderfood.ultils.RequestSetup;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Ordering_Fragment extends Fragment {
    RecyclerView recyclerView_history_wait ;
    Ordering_Adapter adapter ;
    List<History_Wait> list ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering_, container, false);
        unitUI(view);
        AndroidNetworking.initialize(getActivity().getApplicationContext(), RequestSetup.okHttpClient);
        getData();
        return view ;
    }

    private void unitUI(View view) {
        recyclerView_history_wait = view.findViewById(R.id.recyclerView_history_wait) ;
    }

    private void getData(){
        list = new ArrayList<>() ;
        Gson gson = new Gson();
        History_Wait history_wait ;
        for (int i = 0; i <AllMenu_Fragment.history_waits.size() ; i++) {
            history_wait =gson.fromJson(AllMenu_Fragment.history_waits.get(i).toString(),History_Wait.class);
            if (history_wait.getStatus() == 1){
                list.add(history_wait) ;
            }
        }
        adapter = new Ordering_Adapter(list,getContext()) ;
        recyclerView_history_wait.setHasFixedSize(true);
        recyclerView_history_wait.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_history_wait.setAdapter(adapter) ;
    }
}