package com.example.orderfood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.orderfood.R;
import com.example.orderfood.fragments.FogetPassword_Fragment;
import com.example.orderfood.fragments.SignUp_Fragment;
import com.example.orderfood.models.User;
import com.example.orderfood.ultils.BaseUrl;

public class LoginActivity extends AppCompatActivity {
    TextView btn_login,tv_signUp,tv_forgotPassword ;
    EditText edt_numberPhone_login, edt_password_login ;
    ProgressBar progress_Login ;
    public static String token = "" ;
    public static int permission  ;
    public static String username = "" ;
    public static String id = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login) ;
        AndroidNetworking.initialize(getApplicationContext());
        initUI();
        initListener();
    }

    private void initListener() {
        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
            Fragment fragment = new FogetPassword_Fragment();
            fragmentTransaction.add(R.id.layout_login,fragment).commit() ;
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()) ;
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_Login.setVisibility(View.VISIBLE);
                String phone = edt_numberPhone_login.getText().toString().trim() ;
                String password = edt_password_login.getText().toString().trim() ;
                if (phone.isEmpty() || password.isEmpty()){
                    progress_Login.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,R.string.Empty,Toast.LENGTH_SHORT).show();
                }else {

                    AndroidNetworking.post(BaseUrl.baseUrl+BaseUrl.login)
                            .addUrlEncodeFormBodyParameter("phone",phone)
                            .addUrlEncodeFormBodyParameter("password",password)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsObject(User.class, new ParsedRequestListener<User>() {
                                @Override
                                public void onResponse(User response) {
                                    if (response != null){
                                        id = response.getId() ;
                                         token = response.getToken() ;
                                         permission = response.getPermission() ;
                                         username = response.getUsername() ;
                                        Log.d("test", "onCreateViewLogin: "+ id);
                                        progress_Login.setVisibility(View.GONE);
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    if (anError != null){
                                        progress_Login.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this,R.string.AccountErro,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        SpannableString string = new SpannableString("Bạn chưa có tài khoản ? Đăng Ký") ;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                FragmentManager fragmentManager = getSupportFragmentManager() ;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
                Fragment fragment = new SignUp_Fragment() ;
                fragmentTransaction.add(R.id.layout_login,fragment).commit() ;
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName()) ;
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.rgb(255,123,1));
            }
        };
        string.setSpan(clickableSpan,24,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_signUp.setText(string);
        tv_signUp.setMovementMethod(LinkMovementMethod.getInstance());
        tv_signUp.setHighlightColor(Color.TRANSPARENT);
    }

    @SuppressLint("ResourceType")
    private void initUI() {
        progress_Login = findViewById(R.id.progress_Login) ;
        tv_forgotPassword = findViewById(R.id.tv_forgotPassword) ;
        edt_numberPhone_login = findViewById(R.id.edt_numberPhone_login) ;
        edt_password_login = findViewById(R.id.edt_password_login) ;
        btn_login = findViewById(R.id.btn_login) ;
        tv_signUp = findViewById(R.id.tv_signUp) ;
    }


    public void noClick(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        token = "" ;
        permission = 0 ;
        username = "" ;
    }
}