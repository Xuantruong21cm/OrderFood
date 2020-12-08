package com.example.orderfood.ultils;

import com.example.orderfood.activities.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestSetup {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader("Authorization","Bearer "+ LoginActivity.token).build() ;
            return chain.proceed(request);
        }
    }).build() ;
}
