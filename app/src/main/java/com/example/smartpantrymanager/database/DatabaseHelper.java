package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 7;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE pantry_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry_date TEXT" +
                ")";

        db.execSQL(createPantryTable);

        String createRecipesTable = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "preparation_instructions TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable = "CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, " +
                "ingredient_name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "FOREIGN KEY(recipe_id) REFERENCES recipes(id)" +
                ")";

        db.execSQL(createRecipeIngredientsTable);

        String[][] chickenFriedRiceIngredients = {
                {"Rice", "2", "cups"},
                {"Chicken", "1", "cup"},
                {"Eggs", "2", "whole"},
                {"Mixed Vegetables", "1", "cup"}
        };

        insertRecipe(
                db,
                "Chicken Fried Rice",
                "Boil rice for approximately 20 - 30 minutes." +
                        "After adding spices to chicken, fry chicken for approximately 15 minutes on medium heat." +
                        "Add in mixed vegetables and fry for an additional 10 minutes." +
                        "Add in cooked rice and eggs." +
                        "Stir until everything is cooked.",
                chickenFriedRiceIngredients
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS pantry_items");

        onCreate(db);

    }

   /* private void insertChickenFriedRice(SQLiteDatabase db) { //add duration and temps

        ContentValues recipeValues = new ContentValues();

        recipeValues.put("name", "Chicken Fried Rice");
        recipeValues.put("preparation_instructions",
                "Boil rice for approximately 20 - 30 minutes." +
                "After adding spices to chicken, fry chicken for approximately 15 minutes on medium heat." +
                "Add in mixed vegetables and fry for an additional 10 minutes." +
                "Add in cooked rice and eggs." +
                "Stir until everything is cooked.");

        long recipeId = db.insert("recipes", null, recipeValues);

        ContentValues ingredientsValues = new ContentValues();

        ingredientsValues.put("recipe_id", recipeId);
        ingredientsValues.put("ingredient_name", "Rice");
        ingredientsValues.put("quantity", 2);
        ingredientsValues.put("unit", "cups");

        db.insert("recipe_ingredients", null, ingredientsValues);

        ingredientsValues = new ContentValues();

        ingredientsValues.put("recipe_id", recipeId);
        ingredientsValues.put("ingredient_name", "Chicken");
        ingredientsValues.put("quantity", 1);
        ingredientsValues.put("unit", "cup");

        db.insert("recipe_ingredients", null, ingredientsValues);

        ingredientsValues = new ContentValues();

        ingredientsValues.put("recipe_id", recipeId);
        ingredientsValues.put("ingredient_name", "Eggs");
        ingredientsValues.put("quantity", 2);
        ingredientsValues.put("unit", "whole");

        db.insert("recipe_ingredients", null, ingredientsValues);

        ingredientsValues = new ContentValues();

        ingredientsValues.put("recipe_id", recipeId);
        ingredientsValues.put("ingredient_name", "Mixed Vegetables");
        ingredientsValues.put("quantity", 1);
        ingredientsValues.put("unit", "cup");

        db.insert("recipe_ingredients", null, ingredientsValues); */

        private void insertRecipe(SQLiteDatabase db,
                    String recipeName,
                    String instrctions,
                    String[][] ingredients){

            ContentValues recipeValues = new ContentValues();

            recipeValues.put("name", recipeName);
            recipeValues.put("preparation_instructions", instrctions);

            long recipeId = db.insert("recipes", null, recipeValues);

            for (String[] ingredient : ingredients) {

                ContentValues ingredientValues = new ContentValues();

                ingredientValues.put("recipe_id", recipeId);
                ingredientValues.put("ingredient_name", ingredient[0]);
                ingredientValues.put("quantity", Double.parseDouble(ingredient[1]));
                ingredientValues.put("unit", ingredient[2]);

                db.insert("recipe_ingredients", null, ingredientValues);
            }


    }
}