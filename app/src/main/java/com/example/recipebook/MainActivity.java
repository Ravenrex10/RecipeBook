package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeDbHelper dbHelper;
    // private SQLiteDatabase db;
    private LinearLayout recipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeContainer = findViewById(R.id.recipeContainer);

        dbHelper = new RecipeDbHelper(getApplicationContext(), "recipe.db");
        // db = dbHelper.getWritableDatabase();

        listRecipes();  // Listar todas las recetas.
    }

    public void listRecipes() {
        dbHelper = new RecipeDbHelper(getApplicationContext(), "recipe.db");
        List<Recipe> recipeList = dbHelper.getAllRecipes();

        for(Recipe recipe : recipeList){

            View recipeCardView = LayoutInflater.from(this).inflate(R.layout.recipe_card, null);

            // Views para el "card" del frontend
            TextView textRecipeName = recipeCardView.findViewById(R.id.textRecipeName);
            TextView textIngredients = recipeCardView.findViewById(R.id.textIngredients);
            TextView textSteps = recipeCardView.findViewById(R.id.textSteps);
            TextView textTime = recipeCardView.findViewById(R.id.textTime);
            Button eliminateButton = recipeCardView.findViewById(R.id.buttonEliminate);

            textRecipeName.setText(getString(R.string.recipeName) + ": " + recipe.getNombre());
            textIngredients.setText(getString(R.string.ingredients) + ": " + recipe.getIngredientes());
            textSteps.setText(getString(R.string.steps) + ": " + recipe.getPasos());
            textTime.setText(getString(R.string.time) + ": " + recipe.getTiempo() + " " + getString(R.string.minutes));

            eliminateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRecipe(recipe.getId(), recipeCardView);
                }
            });

            // Add the card to the container
            recipeContainer.addView(recipeCardView);

        }
    }

    private void deleteRecipe(Long id, View recipeCardView){
        dbHelper.deleteRecipe(id);
        recipeContainer.removeView(recipeCardView);
        Toast.makeText(this, R.string.recipeDelete, Toast.LENGTH_LONG).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createRecipe: createRecipe(); break;
        }
    }

    private void createRecipe() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}