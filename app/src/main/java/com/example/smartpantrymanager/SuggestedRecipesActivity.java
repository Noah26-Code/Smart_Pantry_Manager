package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.RecipeAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;
import com.example.smartpantrymanager.utils.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerRecipes;
    private TextView tvNoRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);

        dbHelper = new DatabaseHelper(this);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();
        List<Recipe> allRecipes = dbHelper.getAllRecipes();

        List<Recipe> suggestedRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            List<RecipeIngredient> ingredients = dbHelper.getRecipeIngredients(recipe.getId());

            if (RecipeMatcher.canMakeRecipe(pantryItems, ingredients)) {
                suggestedRecipes.add(recipe);
            }
        }

        RecipeAdapter adapter = new RecipeAdapter(
                suggestedRecipes,
                recipe -> {

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra("recipe_id", recipe.getId());
                    intent.putExtra("recipe_name", recipe.getName());
                    intent.putExtra("recipe_instructions", recipe.getPreparationInstructions());

                    startActivity(intent);
                }
        );

        recyclerRecipes.setAdapter(adapter);

        if (suggestedRecipes.isEmpty()) {
            recyclerRecipes.setVisibility(View.GONE);
            tvNoRecipes.setVisibility(View.VISIBLE);

        } else {
            recyclerRecipes.setVisibility(View.VISIBLE);
            tvNoRecipes.setVisibility(View.GONE);
        }
    }
}