package com.example.recipebook;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecipeDbHelper dbHelper;
    private SQLiteDatabase db;
    private EditText editTextNombre;
    private EditText editTextIngredientes;
    private EditText editTextPasos;
    private EditText editTextTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextIngredientes = findViewById(R.id.editTextIngredientes);
        editTextPasos = findViewById(R.id.editTextPasos);
        editTextTiempo = findViewById(R.id.editTextTiempo);

        dbHelper = new RecipeDbHelper(getApplicationContext(), "recipe.db");
        db = dbHelper.getWritableDatabase();
    }

    // Create
    private void addRecipe() {
        // Valores de la receta
        String nombre = editTextNombre.getText().toString();
        String ingredientes = editTextIngredientes.getText().toString();
        String pasos = editTextPasos.getText().toString();
        String tiempoEnTexto = editTextTiempo.getText().toString();

        if (nombre.isEmpty() || ingredientes.isEmpty() || pasos.isEmpty() || tiempoEnTexto.isEmpty()) {
            Toast.makeText(this, "¡Rellene todos los campos!", Toast.LENGTH_LONG).show();
            return;
        }

        // El tiempo hay que pasarlo a double
        Double tiempo;
        try {
            tiempo = Double.parseDouble((tiempoEnTexto));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El tiempo debe ser un valor númerico", Toast.LENGTH_LONG).show();
            return;
        }

        // Agregar receta, tengo que crear en dbHelper un método
        long newRowId = dbHelper.insertRecipe(nombre, ingredientes, pasos, tiempo);

        // Si hay éxito muestra mensaje y cierra actividad, sino muestra mensaje de error
        if (newRowId != -1) {
            Toast.makeText(this, "¡Receta agregada con éxito!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            // Error al agregar la receta
            Toast.makeText(this, "Error al agregar la receta", Toast.LENGTH_LONG).show();
        }
    }

    // Read

    // Update
    private void updateRecipe(int recipeID, String nombre, List<String> ingredientes, String pasos, Double tiempo) {

    }

    // Delete
    private void deleteRecipe(int recipeID) {

    }
}
