package com.specifix.pureleagues.api;

import com.specifix.pureleagues.api.model.FixtureResponse;
import com.specifix.pureleagues.api.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LivescoreApiInterface {

    @GET("fixtures/matches.json")
    Call<FixtureResponse> getFixtures(
            @Query("key") String key,
            @Query("secret") String secret);

}
