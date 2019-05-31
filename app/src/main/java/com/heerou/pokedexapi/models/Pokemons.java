package com.heerou.pokedexapi.models;

public class Pokemons
{
    private int number;
    private String url;
    private String name;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumber()
    {
        String[] urlParts = url.split("/");
        return Integer.parseInt(urlParts[urlParts.length-1]);
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
}
