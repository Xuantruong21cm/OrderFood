package com.example.orderfood.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.orderfood.R;
import com.example.orderfood.adapter.Notification_Adapter;
import com.example.orderfood.models.Notification;
import com.example.orderfood.ultils.BaseUrl;
import com.example.orderfood.ultils.RequestSetup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification_Fragment extends Fragment {
    public static List<Notification> list;
    Notification_Adapter adapter ;
    RecyclerView recyclerView_notification ;
    SwipeRefreshLayout SwipeRefreshLayout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification_, container, false);
        recyclerView_notification = view.findViewById(R.id.recyclerView_notification) ;
        SwipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout) ;
        if (list != null) {
            if (list.size() <= 0) {
                getNotification();
            } else {
                Log.d("lll", "onCreateView: " + list.get(0).getContent());
                adapter = new Notification_Adapter(list,getContext()) ;
                recyclerView_notification.setHasFixedSize(true);
                recyclerView_notification.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView_notification.setAdapter(adapter) ;
            }
        } else {
            list = new ArrayList<>();
            getNotification();
        }

        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getNotification();
                SwipeRefreshLayout.setRefreshing(false );
            }
        });
        return view;
    }

    private void getNotification() {
        AndroidNetworking.initialize(getActivity().getApplicationContext(), RequestSetup.okHttpClient);
        AndroidNetworking.get(BaseUrl.baseUrl + BaseUrl.notification)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Notification notification;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                notification = gson.fromJson(String.valueOf(jsonObject), Notification.class);
                                list.add(notification);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("lll", "onCreateView: " + list.get(0).getContent());
                        adapter = new Notification_Adapter(list,getContext()) ;
                        recyclerView_notification.setHasFixedSize(true);
                        recyclerView_notification.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView_notification.setAdapter(adapter) ;
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}