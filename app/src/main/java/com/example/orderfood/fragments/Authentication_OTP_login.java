package com.example.orderfood.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.orderfood.fragments.viewpagerHomeFragment.ChangePassword_Fragment;
import com.example.orderfood.ultils.BaseUrl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

public class Authentication_OTP_login extends Fragment {
    EditText edt_otp_authen_login;
    TextView btn_authenOTP_login;
    Bundle bundle;
    String username, numberPhone, password, code;
    ProgressBar progress_signUp_authen ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication__o_t_p_login, container, false);
        initUI(view);
        bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            numberPhone = bundle.getString("numberPhone");
            password = bundle.getString("password");
            code = bundle.getString("code");
        }
        initListener();

        return view;
    }

    private void initListener() {
        btn_authenOTP_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edt_otp_authen_login.getText().toString().trim();
                if (otp.isEmpty()) {
                    Toast.makeText(getContext(), "Không Được Để Trống Dữ Liệu", Toast.LENGTH_SHORT).show();
                } else {
                    progress_signUp_authen.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (code != null) {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                code, otp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            AndroidNetworking.post(BaseUrl.baseUrl + BaseUrl.signUp)
                                                    .addBodyParameter("phone", numberPhone)
                                                    .addBodyParameter("password", password)
                                                    .addBodyParameter("fullname", username)
                                                    .addBodyParameter("permission", "1")
                                                    .setPriority(Priority.HIGH)
                                                    .build()
                                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            if (response != null) {
                                                                Toast.makeText(getContext(), R.string.signUpDone, Toast.LENGTH_SHORT).show();
                                                                progress_signUp_authen.setVisibility(View.GONE);
                                                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                onDestroy();
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(ANError anError) {
                                                            if (anError != null) {
                                                                Toast.makeText(getContext(), R.string.AccExists, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(getContext(), "Sai OTP", Toast.LENGTH_SHORT).show();
                                            progress_signUp_authen.setVisibility(View.GONE);
                                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    private void initUI(View view) {
        edt_otp_authen_login = view.findViewById(R.id.edt_otp_authen_login);
        btn_authenOTP_login = view.findViewById(R.id.btn_authenOTP_login);
        progress_signUp_authen = view.findViewById(R.id.progress_signUp_authen) ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction().remove(Authentication_OTP_login.this).commitAllowingStateLoss();
    }
}