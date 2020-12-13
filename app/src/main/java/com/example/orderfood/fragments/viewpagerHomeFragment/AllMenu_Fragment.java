package com.example.orderfood.fragments.viewpagerHomeFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.interfaces.FoodOnClick;
import com.example.orderfood.R;
import com.example.orderfood.activities.MainActivity;
import com.example.orderfood.adapter.AllMenu_Adapter;
import com.example.orderfood.models.Food;
import com.example.orderfood.models.ListDish;
import com.example.orderfood.ultils.BaseUrl;
import com.example.orderfood.ultils.RequestSetup;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AllMenu_Fragment extends Fragment {
    RecyclerView recyclerView_AllMenu;
    List<Food> list;
    public static List<JSONObject> allDish_json ;
    Food food ;
    AllMenu_Adapter adapter;
    BottomSheetDialog bottomSheetDialog;
    ImageView img_imageDish_bottomsheet;
    ProgressBar progress_allmenu;
    TextView btn_Minus_bottomsheet, tv_Amount_bottomsheet, btn_Plus_bottomsheet,
            tv_price_bottomsheet, tv_total_bottomsheet, tv_time_bottomsheet, tv_kalo_bottomsheet,
            tv_weight_bottomsheet, tv_ingredient_bottomsheet, btn_add_bottomsheet;
    int count = 0 ;
    int cost =0;
    public static List<JSONObject> history_waits ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_menu_, container, false);
        recyclerView_AllMenu = view.findViewById(R.id.recyclerView_allMenu);
        progress_allmenu = view.findViewById(R.id.progress_allmenu);
        createList();

        return view;
    }
    private void createList(){
        list = new ArrayList<>();
        if (allDish_json != null){
            if (allDish_json.size() <= 0){
                getData();
            }else {
                initListener();
            }
        }else {
            allDish_json = new ArrayList<>();
            getData();
        }

        if (history_waits != null){
            if (history_waits.size() <= 0){
                getHistory();
            }
        }else {
            history_waits = new ArrayList<>();
            getHistory();
        }

    }


    private void getData() {
        progress_allmenu.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        AndroidNetworking.initialize(getContext().getApplicationContext(), RequestSetup.okHttpClient);
        AndroidNetworking.get(BaseUrl.baseUrl + BaseUrl.dish)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i) ;
                                allDish_json.add(jsonObject) ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        initListener();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public void initListener(){
        Gson gson = new Gson();
        for (int i = 0; i <allDish_json.size() ; i++) {
            food = gson.fromJson(allDish_json.get(i).toString(),Food.class) ;
            list.add(food);
        }
        Log.d("lll", "initListener: "+allDish_json.get(0));
        recyclerView_AllMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_AllMenu.setHasFixedSize(true);
        adapter = new AllMenu_Adapter(getContext(), list);
        recyclerView_AllMenu.setAdapter(adapter);

        progress_allmenu.setVisibility(View.GONE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        adapter.FoodOnClick(new FoodOnClick() {
            @Override
            public void onClick(Food food) {
                bottomSheetDialog = new BottomSheetDialog(getActivity());
                View view = View.inflate(getContext(), R.layout.bottom_dish_info, null);
                bottomSheetDialog.setContentView(view);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                if (behavior != null && behavior instanceof BottomSheetBehavior) {
                    ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {

                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                        }
                    });
                }
                View parent = (View) view.getParent();
                parent.setFitsSystemWindows(true);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
                view.measure(0, 0);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenHeight = displayMetrics.heightPixels;
                bottomSheetBehavior.setPeekHeight(screenHeight);
                if (params.getBehavior() instanceof BottomSheetBehavior) {
                    ((BottomSheetBehavior) params.getBehavior()).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {

                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                        }
                    });
                }

                params.height = screenHeight;
                parent.setLayoutParams(params);
                bottomSheetView(bottomSheetDialog);
                cost = count * Integer.valueOf(food.getPrice()) ;
                Glide.with(getContext()).load(food.getImageDish()).into(img_imageDish_bottomsheet);
                tv_price_bottomsheet.setText(food.getPrice() + " đ");
                tv_time_bottomsheet.setText(food.getTime() + " phút");
                tv_kalo_bottomsheet.setText(food.getCalories() + "kalo");
                tv_weight_bottomsheet.setText(food.getWeight() + " gr");
                tv_ingredient_bottomsheet.setText("Thành Phần : " + food.getIngredient());
                tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                tv_Amount_bottomsheet.setText(String.valueOf(count));
                btn_Minus_bottomsheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (count < 1 ){
                            count = 0 ;
                            cost = count * Integer.valueOf(food.getPrice()) ;
                            tv_Amount_bottomsheet.setText(String.valueOf(count));
                            tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                        }else {
                            count -- ;
                            cost = count * Integer.valueOf(food.getPrice()) ;
                            tv_Amount_bottomsheet.setText(String.valueOf(count));
                            tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                        }
                    }
                });
                btn_Plus_bottomsheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (count >= 10 ){
                            count = 10 ;
                            cost = count * Integer.valueOf(food.getPrice()) ;
                            tv_Amount_bottomsheet.setText(String.valueOf(count));
                            tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                        }else {
                            count ++ ;
                            cost = count * Integer.valueOf(food.getPrice()) ;
                            tv_Amount_bottomsheet.setText(String.valueOf(count));
                            tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                        }
                    }
                });

                btn_add_bottomsheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (count <= 0){
                            Toast.makeText(getContext(),R.string.minCount,Toast.LENGTH_SHORT).show();
                        }else {
                            MainActivity.listDishes.add(new ListDish(food.getNameDish(),food.get_id(),count,cost,food.getPrice(),food.getImageDish()));
                            Toast.makeText(getContext(),R.string.addish,Toast.LENGTH_SHORT).show();
                            count = 0 ;
                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.show();

            }
        });
    }

    private void bottomSheetView(BottomSheetDialog dialog) {
        img_imageDish_bottomsheet = dialog.findViewById(R.id.img_imageDish_bottomsheet);
        btn_Minus_bottomsheet = dialog.findViewById(R.id.btn_Minus_bottomsheet);
        btn_add_bottomsheet = dialog.findViewById(R.id.btn_add_bottomsheet);
        tv_Amount_bottomsheet = dialog.findViewById(R.id.tv_Amount_bottomsheet);
        btn_Plus_bottomsheet = dialog.findViewById(R.id.btn_Plus_bottomsheet);
        tv_ingredient_bottomsheet = dialog.findViewById(R.id.tv_ingredient_bottomsheet);
        tv_kalo_bottomsheet = dialog.findViewById(R.id.tv_kalo_bottomsheet);
        tv_price_bottomsheet = dialog.findViewById(R.id.tv_price_bottomsheet);
        tv_time_bottomsheet = dialog.findViewById(R.id.tv_time_bottomsheet);
        tv_total_bottomsheet = dialog.findViewById(R.id.tv_total_bottomsheet);
        tv_weight_bottomsheet = dialog.findViewById(R.id.tv_weight_bottomsheet);
    }
    public void getHistory(){
        AndroidNetworking.post(BaseUrl.baseUrl+BaseUrl.allBookById)
                .addUrlEncodeFormBodyParameter("id", LoginActivity.id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                history_waits.add(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("lll", "onResponse: "+history_waits.get(0).toString());
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}