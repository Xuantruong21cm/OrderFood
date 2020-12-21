package com.example.orderfood.fragments.viewpagerHistoryOrder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.example.orderfood.R;
import com.example.orderfood.adapter.Completed_Adapter;
import com.example.orderfood.adapter.Ordering_Adapter;
import com.example.orderfood.fragments.Deltails_Order;
import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.interfaces.Detail_Order;
import com.example.orderfood.models.History_Wait;
import com.example.orderfood.ultils.RequestSetup;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CompletedFragment extends Fragment {

    RecyclerView recyclerView_history_completed ;
    Completed_Adapter adapter ;
    public static List<History_Wait> list ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);
        unitUI(view);
        getData();
        return view ;
    }

    private void unitUI(View view) {
        recyclerView_history_completed = view.findViewById(R.id.recyclerView_history_completed) ;
    }

    private void getData(){
        list = new ArrayList<>() ;
        Gson gson = new Gson();
        History_Wait history_wait ;
        for (int i = 0; i < AllMenu_Fragment.history_waits.size() ; i++) {
            history_wait =gson.fromJson(AllMenu_Fragment.history_waits.get(i).toString(),History_Wait.class);
            if (history_wait.getStatus() == 2){
                list.add(history_wait) ;
            }
        }
        adapter = new Completed_Adapter(list,getContext()) ;
        recyclerView_history_completed.setHasFixedSize(true);
        recyclerView_history_completed.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_history_completed.setAdapter(adapter) ;
        adapter.Details_OnClick(new Detail_Order() {
            @Override
            public void onClick_Details(int position) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                FragmentTransaction transaction = fragmentManager.beginTransaction() ;
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putString("fragment","completed");
                Fragment fragment = new Deltails_Order();
                fragment.setArguments(bundle);
                transaction.replace(R.id.fragment,fragment).commit() ;
                transaction.addToBackStack(fragment.getClass().getSimpleName()) ;
            }
        });
    }
}