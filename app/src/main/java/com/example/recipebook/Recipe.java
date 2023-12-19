package com.example.recipebook;

import java.util.List;

public class Recipe {

    private String nombre;
    private List<String> ingredientes;
    private String pasos;
    private Double tiempo;

    public Recipe(String nombre, List<String> ingredientes, String pasos, Double tiempo){
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public String getPasos() {
        return pasos;
    }

    public Double getTiempo() {
        return tiempo;
    }
}
