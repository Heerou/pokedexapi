package com.heerou.pokedexapi.pokedexApi;

import com.heerou.pokedexapi.models.PokemonAns;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApiService
{
    @GET("pokemon")
    Call<PokemonAns> getListPokemon();
}
