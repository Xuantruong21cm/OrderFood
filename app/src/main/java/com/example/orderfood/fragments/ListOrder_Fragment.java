package com.example.orderfood.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.orderfood.R;
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.activities.MainActivity;
import com.example.orderfood.adapter.ListOrder_Adapter;
import com.example.orderfood.fragments.viewpagerHomeFragment.AllMenu_Fragment;
import com.example.orderfood.models.Hour;
import com.example.orderfood.ultils.BaseUrl;
import com.example.orderfood.ultils.RequestSetup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListOrder_Fragment extends Fragment {
    RecyclerView recyclerView_listOrder;
    ImageView img_add_ListOder;
    TextView tv_cost_listOrder, btn_submit_listOrder, tv_empty_listorder;
    ListOrder_Adapter adapter;
    int cost = 0;
    List<String> listTime;
    List<String> id;
    Dialog dialog;
    ProgressBar progress_listOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_order_, container, false);
        initUI(view);
        AndroidNetworking.initialize(getContext().getApplicationContext(), RequestSetup.okHttpClient);
        getTime();
        if (MainActivity.listDishes.size() <= 0) {
            tv_empty_listorder.setVisibility(View.VISIBLE);
            recyclerView_listOrder.setVisibility(View.INVISIBLE);
            img_add_ListOder.setVisibility(View.INVISIBLE);
            tv_cost_listOrder.setVisibility(View.INVISIBLE);
            btn_submit_listOrder.setVisibility(View.INVISIBLE);
        } else {
            tv_empty_listorder.setVisibility(View.INVISIBLE);
            recyclerView_listOrder.setVisibility(View.VISIBLE);
            img_add_ListOder.setVisibility(View.VISIBLE);
            tv_cost_listOrder.setVisibility(View.VISIBLE);
            btn_submit_listOrder.setVisibility(View.VISIBLE);
        }
        adapter = new ListOrder_Adapter(getContext(), MainActivity.listDishes);
        recyclerView_listOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_listOrder.setHasFixedSize(true);
        recyclerView_listOrder.setAdapter(adapter);

        for (int i = 0; i < MainActivity.listDishes.size(); i++) {
            cost += MainActivity.listDishes.get(i).getCost();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_cost_listOrder.setText("Tổng Tiền: " + decimalFormat.format(cost));

        adapter.deleteItem(new ListOrder_Adapter.ItemListOrderListener() {
            @Override
            public void delete(int position) {
                cost -= MainActivity.listDishes.get(position).getCost();
                MainActivity.listDishes.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
                tv_cost_listOrder.setText("Tổng Tiền: " + decimalFormat.format(cost));
                if (MainActivity.listDishes.size() <= 0) {
                    tv_empty_listorder.setVisibility(View.VISIBLE);
                    recyclerView_listOrder.setVisibility(View.INVISIBLE);
                    img_add_ListOder.setVisibility(View.INVISIBLE);
                    tv_cost_listOrder.setVisibility(View.INVISIBLE);
                    btn_submit_listOrder.setVisibility(View.INVISIBLE);
                } else {
                    tv_empty_listorder.setVisibility(View.INVISIBLE);
                    recyclerView_listOrder.setVisibility(View.VISIBLE);
                    img_add_ListOder.setVisibility(View.VISIBLE);
                    tv_cost_listOrder.setVisibility(View.VISIBLE);
                    btn_submit_listOrder.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_submit_listOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_spinner_time);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, 1500);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);

                ListView listView = dialog.findViewById(R.id.listView_Spinner);
                final ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listTime);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.d("idTime", "onItemClick: " + listTime.get(i));
                        dialog.dismiss();
                        Dialog people = new Dialog(getActivity());
                        people.setContentView(R.layout.dialog_people);
                        people.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, 1800);
                        people.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        people.getWindow().setGravity(Gravity.CENTER);
                        EditText edt_number_people = people.findViewById(R.id.edt_number_people);
                        TextView btn_submit_people = people.findViewById(R.id.btn_submit_people);

                        btn_submit_people.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String number = edt_number_people.getText().toString().trim();
                                if (number.isEmpty() || Integer.parseInt(number) <= 0) {
                                    Toast.makeText(getContext(), "Số Người Tối Thiểu Phải Là 1", Toast.LENGTH_SHORT).show();
                                } else {
                                    progress_listOrder.setVisibility(View.VISIBLE);
                                    String Time = listTime.get(i);
                                    JSONArray jsonArray = new JSONArray();
                                    for (int j = 0; j < MainActivity.listDishes.size(); j++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("imageDish", MainActivity.listDishes.get(j).getImageDish());
                                            jsonObject.put("nameDish", MainActivity.listDishes.get(j).getNameDish());
                                            jsonObject.put("amount", MainActivity.listDishes.get(j).getCount());
                                        } catch (Exception e) {
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    AndroidNetworking.post(BaseUrl.baseUrl + BaseUrl.bookDish)
                                            .addUrlEncodeFormBodyParameter("iduser", LoginActivity.id)
                                            .addUrlEncodeFormBodyParameter("time", Time)
                                            .addUrlEncodeFormBodyParameter("people", number)
                                            .addUrlEncodeFormBodyParameter("listdist", jsonArray.toString())
                                            .addUrlEncodeFormBodyParameter("money", String.valueOf(cost))
                                            .addUrlEncodeFormBodyParameter("status", "1")
                                            .setPriority(Priority.HIGH)
                                            .build()
                                            .getAsString(new StringRequestListener() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.d("datban", "onResponse: " + response);
                                                    MainActivity.listDishes.clear();
                                                    progress_listOrder.setVisibility(View.GONE);
                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    people.dismiss();
                                                    AllMenu_Fragment.getHistory();
                                                    Toast.makeText(getContext(), "Đặt Bàn Thành Công", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(ANError anError) {

                                                }
                                            });
                                }
                            }
                        });

                        people.show();

                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void getTime() {
        AndroidNetworking.get(BaseUrl.baseUrl + BaseUrl.time)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Hour.class, new ParsedRequestListener<ArrayList<Hour>>() {

                    @Override
                    public void onResponse(ArrayList<Hour> response) {
                        listTime.clear();
                        for (int i = 0; i < response.size(); i++) {
                            if (response.get(i).getSlot() != 0){
                                listTime.add(response.get(i).getStartingTime() + " - " + response.get(i).getEndTime());
                                id.add(response.get(i).get_id());
                            }
                        }
                        progress_listOrder.setVisibility(View.GONE);
                        btn_submit_listOrder.setEnabled(true);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void initUI(View view) {
        recyclerView_listOrder = view.findViewById(R.id.recyclerView_listOrder);
        img_add_ListOder = view.findViewById(R.id.img_add_ListOder);
        tv_cost_listOrder = view.findViewById(R.id.tv_costDish_listOrder);
        btn_submit_listOrder = view.findViewById(R.id.btn_submit_listOrder);
        tv_empty_listorder = view.findViewById(R.id.tv_empty_listorder);
        progress_listOrder = view.findViewById(R.id.progress_listOrder);

        btn_submit_listOrder.setEnabled(false);
        listTime = new ArrayList<>();
        id = new ArrayList<>();
    }
}