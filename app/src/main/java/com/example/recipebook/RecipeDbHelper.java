package com.example.recipebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
