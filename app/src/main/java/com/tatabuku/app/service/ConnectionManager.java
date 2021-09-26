package com.tatabuku.app.service;

import android.util.Log;

import com.tatabuku.app.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionManager {
    private Retrofit retrofit;
    private APIService mInterface;
    private SessionCookieJar sessionCookieJar;
    private static ConnectionManager ourInstance;
    private static String url;

    public static String getUrl() {
        return url;
    }

    public static void newInstance(String url) {
        ConnectionManager.url = url;
        ourInstance = new ConnectionManager(url);
    }

    public static ConnectionManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ConnectionManager(BuildConfig.BASE_URL);
        }
        return ourInstance;
    }

    private ConnectionManager(String baseUrl) {
        sessionCookieJar = new SessionCookieJar();
        Log.d("BaseURL", BuildConfig.BASE_URL);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(sessionCookieJar)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();

        mInterface = retrofit.create(APIService.class);
    }

    public APIService getService() {
        return mInterface;
    }
}
