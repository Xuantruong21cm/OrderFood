package com.example.orderfood.fragments.viewpagerHomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.R;
import com.example.orderfood.adapter.AllMenu_Adapter;
import com.example.orderfood.model.Food;
import com.example.orderfood.ultils.JsonExample;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AllMenu_Fragment extends Fragment {
    RecyclerView recyclerView_AllMenu ;
    List<Food> list ;
    Food food;
    AllMenu_Adapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_menu_, container, false);
        recyclerView_AllMenu = view.findViewById(R.id.recyclerView_allMenu) ;
        list = new ArrayList<>();
        getData();

        return view ;
    }
    private void getData(){
        Gson gson = new Gson();
        try {
            JSONArray  jsonArray = new JSONArray(JsonExample.json);
            for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    food =gson.fromJson(jsonObject.toString(),Food.class);
                    list.add(food);
            }
            recyclerView_AllMenu.setLayoutManager( new LinearLayoutManager(getContext()));
            recyclerView_AllMenu.setHasFixedSize(true);
            adapter = new AllMenu_Adapter(getContext(),list) ;
            recyclerView_AllMenu.setAdapter(adapter) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}