package com.example.smartpantrymanager;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.database.DatabaseHelper;

public class AddEditIngredientActivity extends AppCompatActivity {

    private int ingredientId = -1;
    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etunit;
    private EditText etExpiryDate;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_ingredient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etunit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        if (ingredientId != -1) {

            etIngredientName.setText(
                    getIntent().getStringExtra("ingredient_name")
            );

            etQuantity.setText(
                    String.valueOf(
                            getIntent().getDoubleExtra("ingredient_quantity", 0)
                    )
            );

            etunit.setText(
                    getIntent().getStringExtra("ingredient_unit")
            );

            etExpiryDate.setText(
                    getIntent().getStringExtra("ingredient_expiry")
            );
        }



        Button btnSaveIngredient = findViewById(R.id.btnSaveingredient);
        Button btnDeleteIngredient = findViewById(R.id.btnDeleteIngredient);

        dbHelper = new DatabaseHelper(this);

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        if (ingredientId != -1) {
            btnDeleteIngredient.setVisibility(Button.VISIBLE);

            btnDeleteIngredient.setOnClickListener(v -> deleteIngredient());
        }

        if (ingredientId != -1) {
            btnDeleteIngredient.setVisibility(Button.VISIBLE);

            btnDeleteIngredient.setOnClickListener(v -> confirmDelete());
        }
    }

    private void saveIngredient() {

        String name = etIngredientName.getText().toString().trim();
        String quantityText = etQuantity.getText().toString().trim();
        String unit = etunit.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            etIngredientName.setError("Please enter an ingredient name");
            return;
        }

        if (quantityText.isEmpty()) {
            etQuantity.setError("Please enter a quantity");
            return;
        }

        if (unit.isEmpty()) {
            etunit.setError("Please enter a unit");
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            etQuantity.setError("Please enter a valid number");
            return;
        }

        if (quantity <=0) {
            etQuantity.setError("Quantity must be greater than 0");
            return;
        }

        long result;

        if (ingredientId == -1) {

            result = dbHelper.insertPantryItem(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

        } else {

            result = dbHelper.updatePantryItem(
                    ingredientId,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );
        }

        if (result != -1) {
            Toast.makeText(
                    this,
                    "Ingredients saved successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {
            Toast.makeText(
                    this,
                    "Failed to save ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void deleteIngredient() {

        int result = dbHelper.deletePantryItem(ingredientId);

        if (result > 0) {
            Toast.makeText(
                    this,
                    "Ingredient deleted successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {
            Toast.makeText(
                    this,
                    "Failed to delete ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void confirmDelete() {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete this ingredient?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialog, which) -> deleteIngredient())
                .show();
    }
}