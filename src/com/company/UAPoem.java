package com.company;

import java.util.ArrayList;

public class UAPoem {
    private String name;
    private String author;
    private int volume;

    public UAPoem(String name, String author, int volume) {
        this.name = name;
        this.author = author;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getVolume() {
        return volume;
    }
}
