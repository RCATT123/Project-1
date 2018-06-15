package com.specifix.pureleagues.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndPoints {

    /*
    * Base url
    */
    private static final String API_URL = "http://10.10.11.54:8080/pureleague/public/api/";

    public static final String Login_url = API_URL.concat("user/");

}
