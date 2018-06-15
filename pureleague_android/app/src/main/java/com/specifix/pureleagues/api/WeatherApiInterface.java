package com.specifix.pureleagues.api;

import com.specifix.pureleagues.api.model.TemperatureResponse;
import com.specifix.pureleagues.api.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApiInterface {

    @GET("locations/v1/cities/geoposition/search.json")
    Call<WeatherResponse> getWeatherKey(
            @Query("q") String coords,
            @Query("apikey") String apiKey);

    @GET("currentconditions/v1/{key}.json?language=en")
    Call<List<TemperatureResponse>> getTemperature(
            @Path("key") String key,
            @Query("apikey") String apiKey);
}
