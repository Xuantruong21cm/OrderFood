package com.example.orderfood.fragments.viewpagerHomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.activities.MainActivity;
import com.example.orderfood.adapter.AllMenu_Adapter;
import com.example.orderfood.interfaces.FoodOnClick;
import com.example.orderfood.models.Food;
import com.example.orderfood.models.ListDish;
import com.example.orderfood.ultils.BaseUrl;
import com.example.orderfood.ultils.RequestSetup;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class MainCourse_Fragment extends Fragment {

    RecyclerView recyclerView_mainCourse ;
    public static List<Food> list ;
    AllMenu_Adapter adapter ;
    Food food ;
    TextView btn_Minus_bottomsheet, tv_Amount_bottomsheet, btn_Plus_bottomsheet,
            tv_price_bottomsheet, tv_total_bottomsheet, tv_time_bottomsheet, tv_kalo_bottomsheet,
            tv_weight_bottomsheet, tv_ingredient_bottomsheet, btn_add_bottomsheet;
    ImageView img_imageDish_bottomsheet;
    BottomSheetDialog bottomSheetDialog ;
    int count = 0 ;
    int cost =0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main_course, container, false);
        recyclerView_mainCourse = view.findViewById(R.id.recyclerView_mainCourse) ;
        AndroidNetworking.initialize(getActivity().getApplicationContext(), RequestSetup.okHttpClient);
        if (list != null){
            if (list.size() <= 0){
                getData();
                initRecyclerView();
            }else {
                initRecyclerView();
            }
        }else {
            list = new ArrayList<>() ;
            getData();
            initRecyclerView();
        }
        return view ;
    }

    private void getData(){
        AndroidNetworking.post(BaseUrl.baseUrl+BaseUrl.dishByCategory)
                .addUrlEncodeFormBodyParameter("idCategory","5fb49b0c90efaf1e1c4ab6f5")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson() ;
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                food = gson.fromJson(response.getJSONObject(i).toString(),Food.class) ;
                                list.add(food) ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter = new AllMenu_Adapter(getContext(),list);
                        recyclerView_mainCourse.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView_mainCourse.setHasFixedSize(true);
                        recyclerView_mainCourse.setAdapter(adapter) ;
                        adapter.FoodOnClick(new FoodOnClick() {
                            @Override
                            public void onClick(Food food) {
                                onClickListener(food);
                            }
                        });
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    private void initRecyclerView(){
        adapter = new AllMenu_Adapter(getContext(),list);
        recyclerView_mainCourse.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_mainCourse.setHasFixedSize(true);
        recyclerView_mainCourse.setAdapter(adapter) ;
        adapter.FoodOnClick(new FoodOnClick() {
            @Override
            public void onClick(Food food) {
             onClickListener(food);
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
    private void onClickListener(Food food){
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
        cost = count * Integer.valueOf(food.getPrice());
        Glide.with(getContext().getApplicationContext()).load(food.getImageDish()).into(img_imageDish_bottomsheet);
        Log.d("imageda", "onClick: " + food.getImageDish());
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
                if (count < 1) {
                    count = 0;
                    cost = count * Integer.valueOf(food.getPrice());
                    tv_Amount_bottomsheet.setText(String.valueOf(count));
                    tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                } else {
                    count--;
                    cost = count * Integer.valueOf(food.getPrice());
                    tv_Amount_bottomsheet.setText(String.valueOf(count));
                    tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                }
            }
        });
        btn_Plus_bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count >= 10) {
                    count = 10;
                    cost = count * Integer.valueOf(food.getPrice());
                    tv_Amount_bottomsheet.setText(String.valueOf(count));
                    tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                } else {
                    count++;
                    cost = count * Integer.valueOf(food.getPrice());
                    tv_Amount_bottomsheet.setText(String.valueOf(count));
                    tv_total_bottomsheet.setText(String.valueOf(cost) + " đ");
                }
            }
        });

        btn_add_bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 0) {
                    Toast.makeText(getContext(), R.string.minCount, Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.listDishes.add(new ListDish(food.getNameDish(), food.get_id(), count, cost, food.getPrice(), food.getImageDish()));
                    Toast.makeText(getContext(), R.string.addish, Toast.LENGTH_SHORT).show();
                    count = 0;
                    bottomSheetDialog.dismiss();
                }
            }
        });

        bottomSheetDialog.show();
    }
}