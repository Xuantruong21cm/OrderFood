package com.example.orderfood.fragments;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class FogetPassword_Fragment extends Fragment {
    Toolbar toolbar_forget;
    EditText edt_numberPhone_forget;
    TextView btn_sendOTP;
    LinearLayout layout_pleaseWait;
    String id = "" ;

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
                    layout_pleaseWait.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    AndroidNetworking.post(BaseUrl.baseUrl + BaseUrl.comparisonPhone)
                            .addUrlEncodeFormBodyParameter("phone", numberPhone)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null) {
                                        try {
                                            id = response.getString("id") ;
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d("test", "onResponse: "+response.toString());
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
                                                                Log.d("code", "erro: "+e.getMessage());
                                                                layout_pleaseWait.setVisibility(View.GONE);
                                                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                Toast.makeText(getContext(),R.string.erroPhone,Toast.LENGTH_SHORT).show();
                                                            }

                                                            @Override
                                                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                super.onCodeSent(s, forceResendingToken);
                                                                Log.d("code", "onCodeSent: "+s);
                                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                                                                FragmentTransaction transaction = fragmentManager.beginTransaction() ;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("id",id);
                                                                bundle.putString("code",s);
                                                                Fragment fragment = new Authentication_OTP() ;
                                                                fragment.setArguments(bundle);
                                                                transaction.replace(R.id.layout_login,fragment).commit() ;
                                                                transaction.addToBackStack(fragment.getClass().getSimpleName()) ;
                                                                layout_pleaseWait.setVisibility(View.GONE);
                                                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                            }
                                                        })
                                                        .build();
                                        PhoneAuthProvider.verifyPhoneNumber(options);
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    if (anError != null) {
                                        layout_pleaseWait.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), R.string.phoneEmpty, Toast.LENGTH_SHORT).show();
                                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
        layout_pleaseWait = view.findViewById(R.id.layout_pleaseWait);
        toolbar_forget.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction().remove(FogetPassword_Fragment.this).commitAllowingStateLoss();
    }
}