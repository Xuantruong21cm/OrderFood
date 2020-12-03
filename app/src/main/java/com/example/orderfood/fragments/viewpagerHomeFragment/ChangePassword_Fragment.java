package com.example.orderfood.fragments.viewpagerHomeFragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.orderfood.R;
import com.example.orderfood.ultils.BaseUrl;

import org.json.JSONObject;

public class ChangePassword_Fragment extends Fragment {
    String id ;
    Bundle bundle ;
    TextView btn_newPass ;
    EditText edt_newPass ;
    LinearLayout layout_newPass ;
    Toolbar toolbar_newPass ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password_, container, false);
        AndroidNetworking.initialize(getActivity().getApplicationContext());
        bundle = getArguments() ;
        if (bundle != null){
            id = bundle.getString("idCode") ;
            Log.d("test", "onCreateView: "+id);
        }
        initUI(view);
        initListener();
        return view ;
    }

    private void initListener() {
        btn_newPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = edt_newPass.getText().toString().trim() ;
                if (newPass.isEmpty()){
                    Toast.makeText(getContext(), R.string.Empty, Toast.LENGTH_SHORT).show();
                }else {
                    layout_newPass.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    AndroidNetworking.post(BaseUrl.baseUrl+BaseUrl.forgotPasswordById)
                            .addUrlEncodeFormBodyParameter("id",id)
                            .addUrlEncodeFormBodyParameter("password",newPass)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null){
                                        Toast.makeText(getContext(),R.string.newPassOK,Toast.LENGTH_SHORT).show();
                                        onDestroy();
                                        layout_newPass.setVisibility(View.GONE);
                                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    if (anError !=null){
                                        Toast.makeText(getContext(),R.string.newPassErro,Toast.LENGTH_SHORT).show();
                                        layout_newPass.setVisibility(View.GONE);
                                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    }
                                }
                            });
                }
            }
        });

        toolbar_newPass.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.cancelPass) ;
                builder.setMessage(R.string.cancelNewPass) ;
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
        btn_newPass = view.findViewById(R.id.btn_newPass) ;
        edt_newPass = view.findViewById(R.id.edt_newPass) ;
        layout_newPass = view.findViewById(R.id.layout_newPass) ;
        toolbar_newPass = view.findViewById(R.id.toolbar_newPass) ;
        toolbar_newPass.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentManager manager = getActivity().getSupportFragmentManager() ;
        for (int i = 0 ; i< manager.getBackStackEntryCount() ; i++){
            manager.popBackStack();
        }
    }
}