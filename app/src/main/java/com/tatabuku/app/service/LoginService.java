package com.tatabuku.app.service;

import com.tatabuku.app.model.pembelian.GetURLResponse;
import com.tatabuku.app.model.pembelian.LoginModel;
import com.tatabuku.app.model.pembelian.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    @POST("/web/session/authenticate")
    Call<LoginResponse> login(@Body LoginModel loginModel);

    @GET("/api/res.partner?query={name,tatabuku_url,tatabuku_db,tatabuku_password,tatabuku_user}&filter=[[\"is_tatabuku_client\",\"=\",\"true\"]]")
    Call<GetURLResponse> getUrl(@Query("id") Integer id);
}
