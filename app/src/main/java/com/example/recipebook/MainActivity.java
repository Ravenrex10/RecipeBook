package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeDbHelper dbHelper;
    private SQLiteDatabase db;
    private LinearLayout recipeContainer;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeContainer = findViewById(R.id.recipeContainer);

        dbHelper = new RecipeDbHelper(getApplicationContext(), "recipe.db");
        db = dbHelper.getWritableDatabase();

        listRecipes();  // Listar todas las recetas.
    }


    public void listRecipes() {
        List<Recipe> recipeList = getAllRecipes();

        for(Recipe recipe : recipeList){

            View recipeCardView = LayoutInflater.from(this).inflate(R.layout.recipe_card, null);

            // Views para el "card" del frontend
            TextView textRecipeName = recipeCardView.findViewById(R.id.textRecipeName);
            TextView textIngredients = recipeCardView.findViewById(R.id.textIngredients);
            TextView textSteps = recipeCardView.findViewById(R.id.textSteps);
            TextView textTime = recipeCardView.findViewById(R.id.textTime);

            textRecipeName.setText(getString(R.string.recipeName) + ": " + recipe.getNombre());
            textIngredients.setText(getString(R.string.ingredients) + ": " + recipe.getIngredientes());
            textSteps.setText(getString(R.string.steps) + ": " + recipe.getPasos());
            textTime.setText(getString(R.string.time) + " " + recipe.getTiempo() + " " + getString(R.string.minutes));

            // Add the card to the container
            recipeContainer.addView(recipeCardView);

        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createRecipe: createRecipe(); break;
        }
    }

    private void createRecipe() {

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
        Double tiempo = 0.0;

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
        return recipeList;
    }
}