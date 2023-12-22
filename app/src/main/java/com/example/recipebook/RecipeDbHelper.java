package com.example.recipebook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                    RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_NOMBRE + " TEXT," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTES + " TEXT," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_PASOS + " TEXT," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_TIEMPO + " TIME" +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME;

    public RecipeDbHelper(Context context, String database_name) {
        super(context, database_name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insertRecipe(String nombre, String ingredientes, String pasos, Double tiempo) {
        // ContentValues sirve para pasar datos entre actividades y para operaciones con bases de datos para guardar pares column-value
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_NOMBRE, nombre);
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTES, ingredientes);
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_PASOS, pasos);
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TIEMPO, tiempo);

        // Devuelvo la fila para comprobar comparando con -1 si se ha insertado bien o no
        long fila = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
        db.close();
        return fila;
    }

    public void deleteRecipe(long recipeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RecipeContract.RecipeEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(recipeID)});
        db.close();
    }

    @SuppressLint("Range")
    public List<Recipe> getAllRecipes(){
        List<Recipe> recipeList = new ArrayList<>();
        String[] columns = {
                RecipeContract.RecipeEntry._ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_NOMBRE,
                RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTES,
                RecipeContract.RecipeEntry.COLUMN_NAME_PASOS,
                RecipeContract.RecipeEntry.COLUMN_NAME_TIEMPO
        };

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME, null, null, null, null, null, null); //Los parametros nulos en columnas y selección cogerá todos por defecto.
        Long id = null;
        String nombre = "";
        String ingredientes = "";
        String pasos = "";
        Double tiempo = 0.0;

        try {
            while (cursor.moveToNext()) {
                id = Long.parseLong(cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry._ID)));
                nombre = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_NOMBRE));
                ingredientes = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTES));
                pasos = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_PASOS));
                tiempo = Double.parseDouble(cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_TIEMPO)));

                Recipe receta = new Recipe(id, nombre, ingredientes, pasos, tiempo);
                recipeList.add(receta);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return recipeList;
    }

}

