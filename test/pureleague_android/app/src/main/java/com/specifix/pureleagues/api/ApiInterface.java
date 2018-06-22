package com.specifix.pureleagues.api;

import com.specifix.pureleagues.util.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("check-login.php")
    Call<Login> authenticate(@Path("username") String username, @Path("password") String password);

    @POST("api/{email}/{password}")
    Call<Login> registration(@Path("email") String email, @Path("password") String password);

}
