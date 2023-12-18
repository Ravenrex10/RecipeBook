package com.example.recipebook;

import android.provider.BaseColumns;

import java.util.List;

public class RecipeContract {
    private RecipeContract() {}

    public static abstract class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Receta";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_INGREDIENTES = "Ingredientes";
        public static final String COLUMN_NAME_PASOS = "Pasos";
        public static final String COLUMN_NAME_TIEMPO = "Tiempo";
    }
}
