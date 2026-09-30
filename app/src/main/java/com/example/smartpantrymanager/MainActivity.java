package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.model.PantryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        FloatingActionButton btnAddIngredient = findViewById(R.id.btnAddIngredient);

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        loadPantryItems();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.navPantry);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navPantry) {
                return true;
            }

            if (itemId == R.id.navRecipes) {

                startActivity(new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                ));

                return true;
            }

            if (itemId == R.id.navSettings) {

                startActivity(new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                ));

                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dbHelper != null) {
            loadPantryItems();
        }
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();

        TextView tvEmptyPantry = findViewById(R.id.tvEmptyPantry);

        RecyclerView recyclerPantry = findViewById(R.id.recyclerPantry);

        if (pantryItems.isEmpty()) {
            recyclerPantry.setVisibility(View.GONE);
            tvEmptyPantry.setVisibility(View.VISIBLE);
        } else {
            recyclerPantry.setVisibility(View.VISIBLE);
            tvEmptyPantry.setVisibility(View.GONE);
        }

        PantryAdapter adapter = new PantryAdapter(pantryItems, item -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            intent.putExtra("ingredient_id", item.getId());
            intent.putExtra("ingredient_name", item.getName());
            intent.putExtra("ingredient_quantity", item.getQuantity());
            intent.putExtra("ingredient_unit", item.getUnit());
            intent.putExtra("ingredient_expiry", item.getExpiryDate());

            startActivity(intent);
        });

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(adapter);
    }
}