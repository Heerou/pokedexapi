package com.heerou.pokedexapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.heerou.pokedexapi.models.PokemonAns;
import com.heerou.pokedexapi.models.Pokemons;
import com.heerou.pokedexapi.pokedexApi.PokeApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter();
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getData();
    }

    private void getData()
    {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonAns> pokemonCall = service.getListPokemon();

        pokemonCall.enqueue(new Callback<PokemonAns>()
        {
            @Override
            public void onResponse(Call<PokemonAns> call, Response<PokemonAns> response)
            {
                if(response.isSuccessful())
                {
                    PokemonAns pokemonRes = response.body();
                    ArrayList<Pokemons> pokemonsList = pokemonRes.getResults();

                    pokemonListAdapter.addPokemonList(pokemonsList);
                }
                else
                {
                    Log.e(TAG, " On response" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonAns> call, Throwable t)
            {
                Log.e(TAG, " On failure" + t.getMessage());
            }
        });
    }
}
