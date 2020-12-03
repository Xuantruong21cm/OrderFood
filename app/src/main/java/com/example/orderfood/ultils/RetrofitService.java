package com.example.orderfood.ultils;

import com.example.orderfood.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {

//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    @FormUrlEncoded
//    @POST("api/v1/signin")
//    public Call<msg> signUp(
//            @Field("phone") String phone,
//            @Field("password") String password,
//            @Field("fullname") String fullname,
//            @Field("permission") String permission
//    );

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("api/v1/login")
    public Call<User> login(
            @Field("phone") String phone,
            @Field("password") String password
    );

}
