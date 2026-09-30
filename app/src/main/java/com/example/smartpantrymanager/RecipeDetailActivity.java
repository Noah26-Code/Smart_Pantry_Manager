package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView tvRecipeDetailName;
    private TextView tvRecipeIngredients;
    private TextView tvPreparationInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvRecipeDetailName = findViewById(R.id.tvRecipeDetailName);
        tvRecipeIngredients = findViewById(R.id.tvRecipeIngredients);
        tvPreparationInstructions = findViewById(R.id.tvPreparationInstructions);

        dbHelper = new DatabaseHelper(this);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        String recipeName = getIntent().getStringExtra("recipe_name");

        String preparationInstructions = getIntent().getStringExtra("recipe_instructions");

        tvRecipeDetailName.setText(recipeName);
        tvPreparationInstructions.setText(formatInstructions(preparationInstructions)
        );

        loadRecipeIngredients(recipeId);
    }

    private String formatInstructions(String instructions) {

        if (instructions == null || instructions.trim().isEmpty()) {
            return "No preparation instructions available.";
        }

        String[] steps = instructions.trim().split("(?<=[.!?])\\s*");

        StringBuilder formattedInstructions = new StringBuilder();

        int stepNumber = 1;

        for (String step : steps) {

            if (!step.trim().isEmpty()) {
                formattedInstructions
                        .append(stepNumber)
                        .append(". ")
                        .append(step.trim())
                        .append("\n\n");

                stepNumber++;
            }
        }

        return formattedInstructions.toString().trim();
    }

    private String formatQuantity(double quantity) {

        if (quantity == Math.floor(quantity)) {
            return String.valueOf((int)  quantity);
        }

        return String.valueOf(quantity);
    }

    private void loadRecipeIngredients(int recipeId) {

        List<RecipeIngredient> ingredients = dbHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredientText = new StringBuilder();

        for (RecipeIngredient ingredient : ingredients) {

            ingredientText
                    .append(". ")
                    .append(ingredient.getIngredientName())
                    .append(" - ")
                    .append(formatQuantity(ingredient.getQuantity()))
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        tvRecipeIngredients.setText(ingredientText.toString());
    }
}