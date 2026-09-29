package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 21;

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

        String[][] chickenFriedRiceIngredients = { //1
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

        String [][] tomatoChutneyPastaIngredients = { //2
                {"Pasta", "2", "cups"},
                {"Tomatoes", "4", "whole"},
                {"Onion", "1", "whole"},
                {"Garlic", "2", "cloves"}
        };

        insertRecipe(
                db,
                "Tomato Chutney Pasta",
                "Boil pasta in salted water until soft (+- 25 minutes)." +
                        "Dice onion and garlic and fry until soft." +
                        "Grate or finely chop tomatoes." +
                        "Add in tomatoes and cook until softened with the onions and garlic." +
                        "Add in boiled pasta and mix everything together.",
                tomatoChutneyPastaIngredients
        );

        String [][] spicyCreamyNoodlesIngredients = { //3
                {"2-minute Noodles", "2", "packets"},
                {"Milk", "1", "cup"},
                {"Hot Sauce", "5", "tablespoons"},
                {"Cheese", "1", "cup"},
        };

        insertRecipe(
                db,
                        "Spicy Creamy Noodles",
                "Boil approximately 500ml of water in a pot." +
                        "Put the noodles into boiling water for 2 - 4 minutes." +
                        "When the noodles are soft, remove from the pot and drain the water." +
                        "In the same pot, pour in the milk and bring it to a boil." +
                        "Gradually add in the cheese and stir until smooth." +
                        "Add in the hot sauce and mix until evenly distributed." +
                        "Ad noodles back into the sauce mixture and stir until noodles are fully coated.",
                spicyCreamyNoodlesIngredients
        );

        String [][] pastaAndMinceIngredients = { //4
                {"Pasta", "2", "cups"},
                {"Mince", "2", "cups"},
                {"Ginger & Garlic paste", "1", "teaspoon"},
                {"Salt", "1", "teaspoon"},
                {"Black Pepper", "1", "teaspoon"},
                {"Onion", "1", "whole"},
                {"Chilli Powder", "3", "tablespoons"}
        };

        insertRecipe(
                db,
                "Pasta and Mince",
                "Set a pot of salted water to boil and add in pasta when water starts boiling." +
                        "Dice up an onion and add it with oil into another pot." +
                        "When onions are fried, add in mince, salt, pepper, ginger and garlic paste and chilli powder." +
                        "Leave mince to cook for approximately 15 - 20 minutes." +
                        "Drain water from the pasta when it is cooked." +
                        "When the mince is done cooking, add it over the top of the pasta or mix it in.",
                pastaAndMinceIngredients

        );

        String [][] baconAndCheeseOmeletteIngredients = { //5
                {"Eggs", "3", "whole"},
                {"Bacon", "3", "strips"},
                {"Cheese", "1", "cup"},
                {"Salt", "1", "teaspoon"},
                {"Black Pepper", "1", "teaspoon"},
        };

        insertRecipe(
                db,
                "Bacon and Cheese Omelette",
                "Fry 3 strips of bacon in oil until cooked." +
                        "In a bowl, add in 3 eggs, salt and pepper and mix until everything is well incorporated." +
                        "Add this mixture to a hot pan." +
                        "Gradually add in cheese to avoid clumping." +
                        "Add in cooked bacon and fold omelette.",
                baconAndCheeseOmeletteIngredients

        );

        String [][] eggFriedRiceIngredients = { //6
                {"Eggs", "3", "whole"},
                {"Rice", "2", "cups"},
                {"Onion", "1", "whole"},
                {"Mixed Vegetables", "1", "cup"}
        };

        insertRecipe(
                db,
                "Egg Fried Rice",
                "Add rice to a pot of boiling water and cook until soft." +
                        "Thinly dice oneion and fry until translucent." +
                        "Add in mixed vegetables and allow to cook for 5 minutes." +
                        "Add in eggs and mix briskly to scramble eggs and mix in other ingredients." +
                        "Add in the cooked rice and mix everything together.",
                eggFriedRiceIngredients
        );

        String [][] chickenSubIngredients = { //7
                {"Bread Roll", "1", "whole"},
                {"Chicken", "1", "cup"},
                {"Mayonnaise", "4", "tablespoons"},
                {"Lettuce", "1", "cup"}
        };

        insertRecipe(
                db,
                "Chicken Sub",
                "Slice roll down the middle." +
                        "In a bowl, shred chicken and mix in mayonnaise." +
                        "Add in lettuce and mix well." +
                        "Fill the bread roll with the mixture.",
                chickenSubIngredients
        );

        String [][] braaiChopsChutneyIngredients = { //8
                {"Chops", "5", "pieces"},
                {"Tomatoes", "5", "whole"},
                {"Chillies", "6", "whole"},
                {"Onion", "1", "whole"},
                {"Sugar", "1", "teaspoon"}
        };

        insertRecipe(
                db,
                "Braai-Chops Chutney",
                "Chop onion and chillies and add to hot oil." +
                        "Grate 5 tomatoes and add to the pot." +
                        "Add in sugar to balace acidity." +
                        "Add in chops and let the dish simmer for approximately 10 - 15 minutes.",
                braaiChopsChutneyIngredients
        );

        String [][] steakStirFryIngredients = { //9
                {"Steak strips", "2", "cups"},
                {"Onion", "1", "whole"},
                {"Mixed Vegetables", "1", "cup"},
                {"Mixed Spices", "2", "tablespoons"},
                {"Soy Sauce", "0.5", "cups"}
        };

        insertRecipe(
                db,
                "Steak Stir Fry",
                "Add steak strips with mixed spices to a pan and fry until lightly browned." +
                        "Add in chopped onion and mixed vegetables." +
                        "Add in Soy sauce and mix well.",
                steakStirFryIngredients
        );

        String [][] chickenSaladWrapIngredients = { //10
                {"Tortilla Wrap", "1", "whole"},
                {"Chicken", "1", "cup"},
                {"Lettuce", "1", "cup"},
                {"Tomato", "1", "whole"},
                {"Sauce", "3", "tablespoons"}
        };

        insertRecipe(
                db,
                "Chicken Salad Wrap",
                "Finely chop or shred chicken." +
                        "Sperad sauce over tortilla." +
                        "Add chicken." +
                        "Add in shredded lettuce and sliced tomato." +
                        "Fold and toast wrap.",
                chickenSaladWrapIngredients
        );

        String [] [] cheeseToastIngredients = { //11
                {"Bread", "2", "slices"},
                {"Butter", "1", "tablespoon"},
                {"Cheese", "1", "cup"},
                {"Chillies", "2", "whole"}
        };

        insertRecipe(
                db,
                "Cheese Toast",
                "Spread butter over the bread." +
                        "Great cheese and spread over the bread." +
                        "Chop up chillies and add to the bread." +
                        "Toast bread until golden brown.",
                cheeseToastIngredients
        );

        String [] [] macAndCheeseIngredients = { //12
                {"Macaroni", "2", "cups"},
                {"milk", "1", "cup"},
                {"Cheese", "1", "cup"},
                {"Black Pepper", "1", "tablespoon"}
        };

        insertRecipe(
                db,
                "Mac and Cheese",
                "Add macaroni into boiling water anc cook until soft." +
                        "Bring milk to a boil and slowly add in cheese." +
                        "Mix until cheese is well incorporated and add in lack pepper." +
                        "Add macaroni to cheese sauce and mix well.",
                macAndCheeseIngredients
        );

        String[][] chickenOmeletteIngredients = { //13
                {"Eggs", "3", "whole"},
                {"Chicken", "1", "cup"},
                {"Onion", "1", "whole"},
                {"Black Pepper", "1", "tablespoon"}
        };

        insertRecipe(
                db,
                "Chicken Omelette",
                "Finely cut up chicken and add to a pan until lightly brown." +
                        "Add onions to a pan until brown." +
                        "Add in eggs and black pepper and mix lightly." +
                        "Add chicken to the eggs and fold omelette",
                chickenOmeletteIngredients
        );

        String [][] eggsOnToastIngredients = { //14
                {"Bread", "2", "slices"},
                {"Eggs", "2", "whole"},
                {"Black Pepper", "1", "tablespoon"}
        };

        insertRecipe(
                db,
                "Eggs On Toast",
                "Put 2 slices of bread in a toaster." +
                        "Add eggs and black pepper to a pan and scramble." +
                        "Fry until cooked." +
                        "Spread eggs over toast.",
                eggsOnToastIngredients
        );

        String [][] bakedBeansChutneyIngredients = { //15
                {"Baked Beans", "1", "can"},
                {"Chillies", "5", "whole"},
                {"Onion", "1", "whole"},
                {"Salt", "1", "teaspoon"}
        };

        insertRecipe(
                db,
                "Baked Beans Chutney",
                "Dice onion and add to a pot of hot oil." +
                        "Add in sliced chillies and baked beans." +
                        "Add in salt to taste." +
                        "Let simmer for approximately 15 minutes.",
                bakedBeansChutneyIngredients

        );

        String [][] loadedPotatoIngredients = { //16
                {"Potato", "1", "whole"},
                {"Mince", "1", "cup"},
                {"Cheese", "1", "cup"},
                {"Curry Powder", "2", "tablespoons"}
        };

        insertRecipe(
                db,
                "Loaded Potato",
                "Place potato in oven at 180 degrees for approximately 1 hour." +
                        "Mix mince and curry powder in a pan and cook until brown." +
                        "Slice the potato down the middle and plac cooked mince inside." +
                        "Top with grated cheese.",
                loadedPotatoIngredients

        );

        String [][] veggieOmeletteIngredients = { //17
                {"Eggs", "3", "whole"},
                {"Mushrooms", "3", "whole"},
                {"Cheese", "1", "cup"},
                {"Black Pepper", "1", "teaspoon"}
        };

        insertRecipe(
                db,
                "Veggie Omelette",
                "Add mushrooms to pan and fry until almost done." +
                        "Add eggs and black pepper and mix in." +
                        "Add in cheese and fold omelette.",
                veggieOmeletteIngredients
        );

        String [][] chickenChipBowlIngredients = { //18
                {"Potato Chips", "2", "cups"},
                {"Chicken Strips", "2", "cups"},
                {"Cheese", "1", "cup"},
        };

        insertRecipe(
                db,
                "Chicken Chip Bowl",
                "Add chicken strips to pan and fry until cooked." +
                        "Fry chips in oil until lighly brown and crispy." +
                        "Place chips and chicken strips in a bowl." +
                        "Sprinkle cheese over the top.",
                chickenChipBowlIngredients
        );

        String [][] frenchToastIngredients = { //19
                {"Eggs", "5", "whole"},
                {"Bread", "3", "slices"},
                {"Black Pepper", "1", "teaspoon"}
        };

        insertRecipe(
                db,
                "French Toast",
                "Mix eggs and pepper in a bowl until beaten." +
                        "Dip bread in egg mixture and place in a frying pan." +
                        "Fry until toasted.",
                frenchToastIngredients
        );

        String [][] steakAndMashIngredients = { //20
                {"Steak Strips", "2", "cups"},
                {"Potatoes", "4", "whole"},
                {"Black Pepper", "1", "teaspoon"},
                {"Butter", "1", "tablespoon"}
        };

        insertRecipe(
                db,
                "Steak and Mash",
                "Cut steak into strips and fry with black pepper." +
                        "Boil potatoes until soft then mash potatoes." +
                        "Add butter to potatoes and mix thoroughly." +
                        "Place mash on a plate and place steak strips on the bed of mash.",
                steakAndMashIngredients
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

    public List<PantryItem> getAllPantryItems() {

            List<PantryItem> pantryItems = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, name, quantity,unit, expiry_date FROM pantry_items", null
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
            String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));

            PantryItem item = new PantryItem(
                    id,
                    name,
                    quantity,
                    unit,
                    expiryDate
                    );

            pantryItems.add(item);
        }
        cursor.close();

        return pantryItems;
    }

    public long insertPantryItem(String name, double quantity, String unit,String expiryDate) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("name", name);
            values.put("quantity", quantity);
            values.put("unit", unit);

            if (expiryDate == null || expiryDate.isEmpty()) {
                values.putNull("expiry_date");
            } else {
                values.put("expiry_date", expiryDate);
            }

            return db.insert("pantry_items", null, values);
    }

    public int updatePantryItem(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);

        if (expiryDate == null || expiryDate.isEmpty()) {
            values.putNull("expiry_date");
        } else {
            values.put("expiry_date", expiryDate);
        }

        return db.update(
                "pantry_items",
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public int deletePantryItem(int id) {
            SQLiteDatabase db = this.getWritableDatabase();

            return db.delete(
                    "pantry_items",
                    "id = ?",
                    new String[]{String.valueOf(id)}
            );
    }

    public List<Recipe> getAllRecipes() {

            List<Recipe> recipes = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT id, name, preparation_instructions FROM recipes", null
            );

            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String preparationInstructions = cursor.getString(
                        cursor.getColumnIndexOrThrow("preparation_instructions")
                );

                Recipe recipe = new Recipe(
                        id,
                        name,
                        preparationInstructions
                );

                recipes.add(recipe);
            }

            cursor.close();

            return recipes;
    }

    public List<RecipeIngredient> getRecipeIngredients(int recipeId) {

            List<RecipeIngredient> ingredients = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT id, recipe_id, ingredient_name, quantity, unit " +
                            "FROM recipe_ingredients WHERE recipe_id = ?",
                    new String[]{String.valueOf(recipeId)}
            );

            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                int recipeIdFromDatabase = cursor.getInt(
                        cursor.getColumnIndexOrThrow("recipe_id")
                );

                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                RecipeIngredient ingredient = new RecipeIngredient(
                        id,
                        recipeIdFromDatabase,
                        ingredientName,
                        quantity,
                        unit
                );

                ingredients.add(ingredient);
            }

            cursor.close();

            return ingredients;
    }
}