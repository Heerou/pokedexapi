package com.heerou.pokedexapi;

import android.support.annotation.NonNull;
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

    private int offset;

    private boolean canLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0)
                {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (canLoad)
                    {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            canLoad = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        canLoad = true;
        offset = 0;
        getData(offset);
    }

    private void getData(int offset)
    {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonAns> pokemonCall = service.getListPokemon(20, offset);

        pokemonCall.enqueue(new Callback<PokemonAns>()
        {
            @Override
            public void onResponse(Call<PokemonAns> call, Response<PokemonAns> response)
            {
                canLoad = true;
                if (response.isSuccessful())
                {
                    PokemonAns pokemonRes = response.body();
                    ArrayList<Pokemons> pokemonsList = pokemonRes.getResults();

                    pokemonListAdapter.addPokemonList(pokemonsList);
                } else
                {
                    Log.e(TAG, " On response" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonAns> call, Throwable t)
            {
                canLoad = true;
                Log.e(TAG, " On failure" + t.getMessage());
            }
        });
    }
}
