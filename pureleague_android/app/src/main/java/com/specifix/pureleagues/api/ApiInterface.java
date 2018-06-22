package com.specifix.pureleagues.api;

import com.specifix.pureleagues.model.User;
import com.specifix.pureleagues.util.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/user/{username}/{password}")
    Call<Login> authenticate(@Path("username") String username, @Path("password") String password);

    @POST("api/newUser")
    Call<Login> registration(@Query("name") String name, @Query("email") String email, @Query("gender") String gender,
                             @Query("dob") String dob, @Query("username") String username, @Query("user_type") String user_type,
                             @Query("pass") String pass, @Query("height") String height, @Query("weight") String weight,
                             @Query("position") String position, @Query("profile") String profile);

    @PUT("api/resetPassword/{email}/{pass}")
    Call<Login> resetPassword(@Path("email") String email, @Path("pass") String pass);

}
