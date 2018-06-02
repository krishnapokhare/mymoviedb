package com.pokhare.mymoviedb.models;

public class FeaturedCast {
    private String name;
    private String character;
    private String imageUrl;

    public FeaturedCast(String name, String character, String imageUrl) {
        this.name = name;
        this.character = character;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
