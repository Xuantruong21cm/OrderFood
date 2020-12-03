package com.example.orderfood.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.orderfood.activities.LoginActivity;
import com.example.orderfood.ultils.BaseUrl;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class FogetPassword_Fragment extends Fragment {
    Toolbar toolbar_forget;
    EditText edt_numberPhone_forget;
    TextView btn_sendOTP;
    ProgressBar progress_forget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foget_password_, container, false);
        AndroidNetworking.initialize(getActivity().getApplicationContext());
        initUI(view);
        ininListener();


        return view;
    }

    private void ininListener() {
        toolbar_forget.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
            }
        });

        btn_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberPhone = edt_numberPhone_forget.getText().toString().trim();
                if (numberPhone.isEmpty()) {
                    Toast.makeText(getContext(), R.string.Empty, Toast.LENGTH_SHORT).show();
                } else {

                    AndroidNetworking.post(BaseUrl.baseUrl + BaseUrl.comparisonPhone)
                            .addUrlEncodeFormBodyParameter("phone", numberPhone)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null) {
                                        PhoneAuthOptions options =
                                                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                                                        .setPhoneNumber("+84"+numberPhone.substring(1))       // Phone number to verify
                                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                                        .setActivity(getActivity())                 // Activity (for callback binding)
                                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                            @Override
                                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                                Log.d("code", "onCodeSent: ");
                                                            }

                                                            @Override
                                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                Log.d("code", "onCodeSent: ");
                                                            }
                                                        })          // OnVerificationStateChangedCallbacks
                                                        .build();
                                        PhoneAuthProvider.verifyPhoneNumber(options);
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    if (anError != null) {
                                        Toast.makeText(getContext(), R.string.phoneEmpty, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initUI(View view) {
        toolbar_forget = view.findViewById(R.id.toolbar_forget);
        edt_numberPhone_forget = view.findViewById(R.id.edt_numberPhone_forget);
        btn_sendOTP = view.findViewById(R.id.btn_sendOTP);
        progress_forget = view.findViewById(R.id.progress_forget);
        toolbar_forget.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentManager().beginTransaction().remove(FogetPassword_Fragment.this).commitAllowingStateLoss();
    }
}