package com.example.orderfood.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.fragments.viewpagerHomeFragment.ChangePassword_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Authentication_OTP extends Fragment {
    Toolbar toolbar_otp;
    EditText edt_otp_authen;
    TextView btn_authenOTP;
    LinearLayout layout_authenOTP;
    Bundle bundle;
    String id,keyOTP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_authentication__o_t_p, container, false);
        initUI(view);
        bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id") ;
            keyOTP = bundle.getString("code");
        }
        inintListener();
        return view;
    }

    private void inintListener() {
        btn_authenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edt_otp_authen.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    Toast.makeText(getContext(), R.string.alertOTP, Toast.LENGTH_SHORT).show();
                } else {
                    layout_authenOTP.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (keyOTP != null) {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                keyOTP, code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                                            FragmentTransaction transaction = fragmentManager.beginTransaction() ;
                                            Bundle bundle = new Bundle() ;
                                            bundle.putString("idCode",id);
                                            Fragment fragment = new ChangePassword_Fragment();
                                            fragment.setArguments(bundle) ;
                                            transaction.replace(R.id.layout_login,fragment).commit() ;
                                            transaction.addToBackStack(fragment.getClass().getSimpleName()) ;
                                            layout_authenOTP.setVisibility(View.GONE);
                                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        }else {
                                            Toast.makeText(getContext(), "Sai OTP", Toast.LENGTH_SHORT).show();
                                            layout_authenOTP.setVisibility(View.GONE);
                                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        }
                                    }
                                });
                    }
                }
            }
        });

        toolbar_otp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.exitOTP);
                builder.setMessage(R.string.messOTP);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onDestroy();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void initUI(View view) {
        toolbar_otp = view.findViewById(R.id.toolbar_otp_authen);
        edt_otp_authen = view.findViewById(R.id.edt_otp_authen);
        btn_authenOTP = view.findViewById(R.id.btn_authenOTP);
        layout_authenOTP = view.findViewById(R.id.layout_authenOTP);
        toolbar_otp.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction().remove(Authentication_OTP.this).commitAllowingStateLoss();
    }
}