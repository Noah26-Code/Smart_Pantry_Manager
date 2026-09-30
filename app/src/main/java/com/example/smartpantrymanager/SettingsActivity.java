package com.example.smartpantrymanager;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Switch;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryReminder;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        switchExpiryReminder = findViewById(R.id.switchExpiryReminder);

        preferences = getSharedPreferences(
                "SmartPantryPreferences",
                MODE_PRIVATE
        );

        boolean expiryReminderEnabled = preferences.getBoolean("expiry_reminders", false);

        switchExpiryReminder.setChecked(expiryReminderEnabled);

        switchExpiryReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("expiry_reminders",
                    isChecked);

            editor.apply();
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.navSettings);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navSettings) {
                return true;
            }

            if (itemId == R.id.navPantry) {
                Intent intent = new Intent(
                        SettingsActivity.this,
                        MainActivity.class
                );

                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
                );

                startActivity(intent);
                return true;
            }

            if (itemId == R.id.navRecipes) {
                startActivity(new Intent(SettingsActivity.this,
                        SuggestedRecipesActivity.class
                ));

                return true;
            }

            return false;
        });
    }
}