package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeDbHelper dbHelper;
    private SQLiteDatabase db;
    private ScrollView recipeContainer;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new RecipeDbHelper(getApplicationContext(), "recipe.db");
        db = dbHelper.getWritableDatabase();

        recipeContainer = findViewById(R.id.recipeContainer);

        listRecipes();  // Listar todas las recetas al abrir la aplicación.
    }


    public void listRecipes() {
        List<Recipe> recipeList = getAllRecipes();

        for(Recipe recipe : recipeList){
            TextView recipeTextView = new TextView(this);
            recipeTextView.setText("Recipe Name: " + recipe.getNombre() + "\n" +
                    "Ingredients: " + recipe.getIngredientes() + "\n" +
                    "Steps: " + recipe.getPasos() + "\n" +
                    "Time: " + recipe.getTiempo() + " minutes\n" +
                    "----------------------------------");

            // Set text appearance or other properties as needed
            recipeTextView.setTextSize(16);

            // Add the TextView to the LinearLayout
            recipeContainer.addView(recipeTextView);
        }
    }

    @SuppressLint("Range")
    private List<Recipe> getAllRecipes(){
        List<Recipe> recipeList = new ArrayList<>();
        String[] columns = {
                RecipeContract.RecipeEntry._ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_NOMBRE,
                RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTES,
                RecipeContract.RecipeEntry.COLUMN_NAME_PASOS,
                RecipeContract.RecipeEntry.COLUMN_NAME_TIEMPO
        };

        Cursor cursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME, null, null, null, null, null, null); //Los parametros nulos en columnas y selección cogerá todos por defecto.
        String nombre = "";
        String ingredienteString = "";
        List<String> ingredientes = new ArrayList<String>();
        String pasos = "";
        Double tiempo = -1.0;

        try {
            while (cursor.moveToNext()) {

                nombre = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_NOMBRE));
                ingredienteString = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTES));
                ingredientes = Arrays.asList(ingredienteString.split(","));
                pasos = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_PASOS));
                tiempo = Double.parseDouble(cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_TIEMPO)));

                Recipe receta = new Recipe(nombre, ingredientes, pasos, tiempo);
                recipeList.add(receta);
            }
        } finally {
            cursor.close();
        }

        recipeList.add(new Recipe("Recipe 1", Arrays.asList("Ingredient 1", "Ingredient 2"), "Step 1, Step 2", 30.0));
        recipeList.add(new Recipe("Recipe 2", Arrays.asList("Ingredient 3", "Ingredient 4"), "Step 1, Step 2, Step 3", 45.0));

        return recipeList;
    }
}