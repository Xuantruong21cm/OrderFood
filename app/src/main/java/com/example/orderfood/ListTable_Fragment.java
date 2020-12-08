package com.example.orderfood;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.adapter.ListTable_Adapter;
import com.example.orderfood.models.Hour;
import com.example.orderfood.models.Table;
import com.example.orderfood.ultils.BaseUrl;
import com.example.orderfood.ultils.RequestSetup;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.orderfood.ultils.RequestSetup.okHttpClient;


public class ListTable_Fragment extends Fragment {
    TextView spinner_hour_listTable, spinner_day_listTable;
    RecyclerView recyclerView_listTable;
    ListTable_Adapter adapter;
    Dialog dialog;
    List<String> listTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_table_, container, false);
        initUI(view);
        getData();
        return view;
    }

    private void initUI(View view) {
        spinner_day_listTable = view.findViewById(R.id.spinner_day_listTable);
        spinner_hour_listTable = view.findViewById(R.id.spinner_hour_listTable);
        recyclerView_listTable = view.findViewById(R.id.recyclerView_listTable);
        listTime = new ArrayList<>();
    }

    private void getData() {
        AndroidNetworking.initialize(getActivity().getApplicationContext(), okHttpClient);
        AndroidNetworking.get(BaseUrl.baseUrl + BaseUrl.table)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Table.class, new ParsedRequestListener<List<Table>>() {
                    @Override
                    public void onResponse(List<Table> response) {
                        adapter = new ListTable_Adapter(response, getContext());
                        recyclerView_listTable.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        recyclerView_listTable.setHasFixedSize(true);
                        recyclerView_listTable.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        AndroidNetworking.get(BaseUrl.baseUrl + BaseUrl.time)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Hour.class, new ParsedRequestListener<ArrayList<Hour>>() {

                    @Override
                    public void onResponse(ArrayList<Hour> response) {

                        spinner_hour_listTable.setText(response.get(0).getStartingTime() + " - " + response.get(0).getEndTime());
                        spinner_hour_listTable.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                listTime.clear();
                                dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.dialog_spinner_time);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, 1500);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().setGravity(Gravity.CENTER);
                                for (int i = 0; i < response.size(); i++) {
                                    listTime.add(response.get(i).getStartingTime() + " - " + response.get(i).getEndTime());
                                }

                                ListView listView = dialog.findViewById(R.id.listView_Spinner);
                                final ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listTime);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        spinner_hour_listTable.setText(adapter.getItem(i));
                                        Log.d("idTime", "onItemClick: " + response.get(i).get_id());
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}