package com.example.orderfood.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.orderfood.R;
import com.example.orderfood.ultils.BaseUrl;

import org.json.JSONObject;

public class SignUp_Fragment extends Fragment {
    Toolbar toolbar_signUp ;
    EditText edt_userName_SignUp, edt_numberPhone_SignUp,
            edt_password_SignUp ;
    TextView btn_signUp ;
    ProgressBar progressBar ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_, container, false);
        AndroidNetworking.initialize(getActivity().getApplicationContext());
        initUI(view);
        initListener();

        return view ;
    }

    private void initListener(){
        toolbar_signUp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String username = edt_userName_SignUp.getText().toString().trim() ;
                String numberPhone = edt_numberPhone_SignUp.getText().toString().trim() ;
                String password = edt_password_SignUp.getText().toString().trim() ;
                String permission = "1" ;
                if (username.isEmpty() || numberPhone.isEmpty() || password.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),R.string.Empty,Toast.LENGTH_SHORT).show();
                }else {
                    AndroidNetworking.post(BaseUrl.baseUrl+BaseUrl.signUp)
                            .addBodyParameter("phone",numberPhone)
                            .addBodyParameter("password",password)
                            .addBodyParameter("fullname",username)
                            .addBodyParameter("permission","1")
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null){
                                        Toast.makeText(getContext(),R.string.signUpDone,Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        onDestroy();
                                    }
                                }
                                @Override
                                public void onError(ANError anError) {
                                    if (anError != null){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(),R.string.AccExists, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
    }

    private void initUI(View view){
        progressBar = view.findViewById(R.id.progress_signUp) ;
        edt_userName_SignUp = view.findViewById(R.id.edt_userName_SignUp) ;
        edt_numberPhone_SignUp = view.findViewById(R.id.edt_numberPhone_SignUp) ;
        edt_password_SignUp = view.findViewById(R.id.edt_password_SignUp) ;
        btn_signUp = view.findViewById(R.id.btn_signUp) ;
        toolbar_signUp = view.findViewById(R.id.toolbar_signUp) ;
        toolbar_signUp.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentManager().beginTransaction().remove(SignUp_Fragment.this).commitAllowingStateLoss();
    }

}