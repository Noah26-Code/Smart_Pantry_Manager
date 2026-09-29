package com.example.smartpantrymanager.model;

public class Recipe {

    private int id;
    private String name;
    private String preparationInstructions;

    public Recipe(int id, String name, String preparationInstructions) {
        this.id = id;
        this.name = name;
        this.preparationInstructions = preparationInstructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreparationInstructions() {
        return preparationInstructions;
    }
}
