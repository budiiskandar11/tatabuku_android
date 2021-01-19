package com.tatabuku.app.service;

import com.tatabuku.app.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginConnectionManager {
    private Retrofit retrofit;
    private LoginService mInterface;
    private static LoginConnectionManager ourInstance;

    public static LoginConnectionManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new LoginConnectionManager();
        }
        return ourInstance;
    }

    private LoginConnectionManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).cookieJar(new SessionCookieJar()).build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .build();

        mInterface = retrofit.create(LoginService.class);
    }

    public LoginService getService() {
        return mInterface;
    }
}
