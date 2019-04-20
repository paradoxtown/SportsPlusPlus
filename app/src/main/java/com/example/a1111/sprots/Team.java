package com.example.a1111.sprots;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Team implements Serializable {
    private String name;
    private String image;
    public Team(String name,String image)
    {
        this.name = name;
        this.image = image;
    }
    public String getName()
    {
        return name;
    }
    public String getImage()
    {
        return image;
    }
}
