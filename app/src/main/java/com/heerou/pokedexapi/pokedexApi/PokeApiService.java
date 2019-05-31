package com.heerou.pokedexapi.pokedexApi;

import com.heerou.pokedexapi.models.PokemonAns;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService
{
    @GET("pokemon")
    Call<PokemonAns> getListPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
