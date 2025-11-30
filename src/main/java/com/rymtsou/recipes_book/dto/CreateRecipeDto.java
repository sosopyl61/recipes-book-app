package com.rymtsou.recipes_book.dto;

public class CreateRecipeDto {

    private String title;
    private String instructions;

    public CreateRecipeDto() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
