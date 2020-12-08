package com.example.orderfood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.asynccoroutines.AsyncCoroutines;
import com.example.orderfood.HomeMenu_Fragment;
import com.example.orderfood.ListOrder_Fragment;
import com.example.orderfood.ListTable_Fragment;
import com.example.orderfood.Notification_Fragment;
import com.example.orderfood.R;
import com.example.orderfood.User_Fragment;
import com.example.orderfood.models.ListDish;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public static List<ListDish> listDishes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        inintListener();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.homeMenu_Fragment:
                    selectedFragment = new HomeMenu_Fragment();
                    break;
                case R.id.listTable_Fragment:
                    selectedFragment = new ListTable_Fragment();
                    break;
                case R.id.listOrder_Fragment:
                    selectedFragment = new ListOrder_Fragment();
                    break;
                case R.id.notification_Fragment:
                    selectedFragment = new Notification_Fragment();
                    break;
                case R.id.user_Fragment:
                    selectedFragment = new User_Fragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFragment).commit() ;
            return true ;
        }
    };

    private void inintListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void initUI() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (listDishes != null){

        }else {
            listDishes = new ArrayList<>() ;
        }
    }

    public void noClick(View view) {
    }
}