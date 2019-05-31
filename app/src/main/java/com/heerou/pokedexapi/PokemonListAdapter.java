package com.heerou.pokedexapi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heerou.pokedexapi.models.Pokemons;

import java.util.ArrayList;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder>
{
    private ArrayList<Pokemons> dataSet;

    public PokemonListAdapter()
    {
        dataSet =  new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_pokemon, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos)
    {
        Pokemons p = dataSet.get(pos);
        viewHolder.nombreTextView.setText(p.getName());
    }

    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }

    public void addPokemonList(ArrayList<Pokemons> pokemonsList)
    {
        dataSet.addAll(pokemonsList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView fotoImgView;
        TextView nombreTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            fotoImgView = itemView.findViewById(R.id.fotoImgView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
        }
    }
}
