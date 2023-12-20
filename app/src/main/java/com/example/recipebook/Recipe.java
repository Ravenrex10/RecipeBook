package com.example.recipebook;

import java.util.List;

public class Recipe {
    private Long id;
    private String nombre;
    private List<String> ingredientes;
    private String pasos;
    private Double tiempo;

    public Recipe(Long id, String nombre, List<String> ingredientes, String pasos, Double tiempo){
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.tiempo = tiempo;
    }

    public Long getId() {return id;}
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
