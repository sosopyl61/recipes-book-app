package com.rymtsou.recipes_book.dto.response;

public class RecipeResponseDto {
    private Long id;
    private String title;
    private String instructions;
    private String authorName;

    public RecipeResponseDto(Long id, String title, String instructions, String authorName) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.authorName = authorName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
