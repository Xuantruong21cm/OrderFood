package com.example.orderfood.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

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
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                String username = edt_userName_SignUp.getText().toString().trim() ;
                String numberPhone = edt_numberPhone_SignUp.getText().toString().trim() ;
                String password = edt_password_SignUp.getText().toString().trim() ;
                if (username.isEmpty() || numberPhone.isEmpty() || password.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),R.string.Empty,Toast.LENGTH_SHORT).show();
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }else {
                    AndroidNetworking.post(BaseUrl.baseUrl + BaseUrl.comparisonPhone)
                            .addUrlEncodeFormBodyParameter("phone", numberPhone)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getContext(),R.string.AccExists,Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }

                                @Override
                                public void onError(ANError anError) {
                                    if (anError != null) {
                                        PhoneAuthOptions options =
                                                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                                                        .setPhoneNumber("+84" + numberPhone.substring(1))       // Phone number to verify
                                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                                        .setActivity(getActivity())                 // Activity (for callback binding)
                                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                            @Override
                                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                                Log.d("code", "complete: "+phoneAuthCredential.toString());
                                                            }

                                                            @Override
                                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                Log.d("erro", "Erro: "+e.getMessage());
                                                                Toast.makeText(getContext(),R.string.erroPhone,Toast.LENGTH_SHORT).show();
                                                            }

                                                            @Override
                                                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                super.onCodeSent(s, forceResendingToken);
                                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                                                                FragmentTransaction transaction = fragmentManager.beginTransaction() ;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("username",username);
                                                                bundle.putString("numberPhone",numberPhone);
                                                                bundle.putString("password",password);
                                                                bundle.putString("code",s);
                                                                Fragment fragment = new Authentication_OTP_login() ;
                                                                fragment.setArguments(bundle);
                                                                transaction.replace(R.id.layout_login,fragment).commit() ;
                                                                transaction.addToBackStack(fragment.getClass().getSimpleName()) ;
                                                                progressBar.setVisibility(View.GONE);
                                                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                            }
                                                        })
                                                        .build();

                                        PhoneAuthProvider.verifyPhoneNumber(options);
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