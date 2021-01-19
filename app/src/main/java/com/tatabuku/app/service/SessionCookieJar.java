package com.tatabuku.app.service;

import android.webkit.CookieManager;

import com.tatabuku.app.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SessionCookieJar implements CookieJar {

    private List<Cookie> cookies;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//        if (url.encodedPath().endsWith("authenticate")) {
        this.cookies = new ArrayList<>(cookies);
        for (Cookie cookie :
                cookies) {
            CookieManager.getInstance().setCookie(url.host(), cookie.toString());
        }

//        }
    }


    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (cookies != null) {
            return cookies;
        }
        return Collections.emptyList();
    }
}
