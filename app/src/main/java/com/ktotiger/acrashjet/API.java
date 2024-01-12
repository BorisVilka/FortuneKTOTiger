package com.ktotiger.acrashjet;


import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("/D8n87hr8")
    Call<Answer> getAns();

    @GET("/D8n87hr8?setting=ok")
    Call<Answer2> getAns2();

}