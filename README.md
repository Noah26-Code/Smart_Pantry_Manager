# Smart Pantry Manager

Smart Pantry Manager is an Android application developed in Java that helps users reduce food wastage by suggesting quick and easy recipes based only on what the user has in their home pantry.

The application stores pantry items details, such as ingredient name, quantity, unit, and optional expiry date. It then compares the user's available ingredients against a collection of preloaded recipes and only suggests recipes where all required ingredients and quantities are available.

## Features
Smart Pantry Manager is able to:

- Add pantry ingredients.
- View all pantry ingredients.
- Edit existing ingredients - quantities and units.
- Delete ingredients.
- Store ingredient quantities and measurement units.
- Select optional expiry dates using a date picker.
- View preloaded recipes.
- Suggest recipes based on pantry contents.
- Strict ingredient and quantity matching.
- Basic ingredient and unit normalization.
- View recipe ingredients and preparation instructions in neat and user-friendly format.
- Fixed bottom navigation menu between Pantry, Recipes, and Settings.
- Persistent application settings and data.

## Recipe Matching

The application uses strict recipe matching rule.

A recipe is only suggested when:

1. Every required ingredient exists in the user's pantry.
2. The pantry contains at least the quantity required by the recipe.
3. The ingredient measurement units are compatible after normalization.

For example, if a recipe requires:

- 500 g rice
- 300 g chicken
- 2 whole eggs

the recipe will only be suggested if the pantry contains all of these ingredients in the required quantities or greater.

The application also normalizes common variations such as:

- gram / grams / g
- cup / cups
- tablespoon / tablespoons / tbsp
- teaspoon / teaspoons / tsp
- slice / slices

Normalization was implemented to account for different spellings and abbreviations. Users may enter "grams" or "g" and achieve the same result. 

## Database

The application uses SQLite through Android's `SQLiteOpenHelper`.

I elected to go with SQLite because it provides local persistent storage directly on the Android device and does not require an internet connection or external database server.

The database contains three main tables:

### pantry_items

This table is responsible for storing ingredients currently available to the user.

Main fields include:

- id
- name
- quantity
- unit
- expiry_date

### recipes

Responsible for storing the preloaded recipes, which includes the ingredients and step-by-step preparation instructions.

Main fields include:

- id
- name
- preparation_instructions

### recipe_ingredients

Responsible for storing the ingredients required by each recipe and links them to the appropriate recipe.

Main fields include:

- id
- recipe_id
- ingredient_name
- quantity
- unit

## Technologies Used

- Java
- Android Studio
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Material Design Components
- SharedPreferences
- Git
- GitHub

## Application Screens

### Pantry

Displays all ingredients currently stored in the user's pantry.

Users are able to add new ingredients or select an existing ingredient to edit or delete it.

### Add/Edit Ingredient

This screen allows the user to enter:

- Ingredient name
- Quantity
- Measurement unit
- Optional expiry date

The screen also provides input validation to prevent invalid pantry data. Each text area accepts a specific type of input to maintain consistency.

### Suggested Recipes

Displays recipes that can be fully prepared using the ingredients and quantities currently available in the pantry.

Recipes with missing ingredients or insufficient quantities are not displayed.

### Recipe Detail

Displays the selected recipe's:

- Recipe name
- Required ingredients and quantities
- Preparation instructions

### Settings

Provides application preferences and information about the Smart Pantry Manager application.

## Project Structure

The project separates application responsibilities into several packages and classes:

- `database` - SQLite database creation and CRUD operations
- `model` - Data models for pantry items, recipes, and recipe ingredients
- `adapter` - RecyclerView adapters
- `utils` - Recipe matching logic
- `Activities` - Application screens and navigation

## Version Control

The project was developed using Git and GitHub with incremental commits throughout development.

Commits document the implementation of major features including database development, pantry CRUD functionality, recipe matching, recipe details, navigation, settings, and UI improvements.


