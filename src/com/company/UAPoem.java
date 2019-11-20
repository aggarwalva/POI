package com.company;

import java.util.ArrayList;

public class UAPoem {
    private String name;
    private String author;
    private int volume;
    private int rank;

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

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
