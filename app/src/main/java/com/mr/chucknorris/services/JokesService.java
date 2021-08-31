package com.mr.chucknorris.services;

import com.mr.chucknorris.model.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokesService {

    @GET("jokes/random")
    Call<Joke> getRandomJoke();

}
