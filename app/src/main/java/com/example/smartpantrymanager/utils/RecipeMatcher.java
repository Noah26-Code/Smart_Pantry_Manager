package com.example.smartpantrymanager.utils;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;
public class RecipeMatcher {

    public static boolean canMakeRecipe(
            List<PantryItem> pantryItems,
            List<RecipeIngredient> recipeIngredients) {

        for (RecipeIngredient requiredIngredient : recipeIngredients) {

            boolean ingredientFound = false;

            for (PantryItem pantryItem : pantryItems) {

                String pantryName = normalizeIngredientName(
                        pantryItem.getName()
                );

                String requiredName = normalizeIngredientName(
                        requiredIngredient.getIngredientName()
                );

                if (pantryName.equals(requiredName)) {

                    String pantryunit = normalizeUnit(
                            pantryItem.getUnit()
                    );

                    String requiredUnit = normalizeUnit(
                            requiredIngredient.getUnit()
                    );

                    if (pantryunit.equals(requiredUnit)
                    && pantryItem.getQuantity() >= requiredIngredient.getQuantity()) {

                        ingredientFound = true;
                        break;
                    }

                }
            }

            if (!ingredientFound) {
                return false;
            }
        }

        return true;
    }

    private static String normalizeIngredientName(String name) {

        String normalized = name.trim().toLowerCase();

        if (normalized.equals("tomatoes")) {
            return "tomato";
        }

        if (normalized.equals("eggs")) {
            return "egg";
        }

        if (normalized.equals("chillies")) {
            return "chilli";
        }

        return normalized;
    }

    private static String normalizeUnit(String unit) {

        String normalized = unit.trim().toLowerCase();

        switch (normalized) {

            case "cups":
                return "cup";

            case "tablespoons":
            case "tablespoon":
            case "tbsp":
                return "tbsp";

            case "teaspoons":
            case "teaspoon":
            case "tsp":
                return "tsp";

            case "grams":
            case "gram":
            case "g":
                return "g";

            case "kilograms":
            case "kilogram":
            case "kg":
                return "kg";

            case "whole":
            case "each":
                return "whole";

            case "slices":
            case "slice":
                return "slice";

            case "cloves":
            case "clove":
                return "clove";

            case "packets":
            case "packet":
                return "packet";

            case "strips":
            case "strip":
                return "strip";

            case "pieces":
            case "piece":
                return "piece";

            case "cans":
            case "can":
                return "can";

            case "millilitres":
            case "milliliters":
            case "millilitre":
            case "milliliter":
            case "ml":
                return "ml";

            case "litres":
            case "liters":
            case "litre":
            case "liter":
            case "l":
                return "l";

            default:
                return normalized;
        }
    }
}
