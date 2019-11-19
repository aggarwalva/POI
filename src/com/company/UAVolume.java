package com.company;

import java.util.ArrayList;

public class UAVolume {
    private int number;
    private ArrayList<UAContributor> contributors;

    public UAVolume(int number){
        this.number = number;
        contributors = new ArrayList<>();
    }

    public void addContributor(UAContributor contributor){
        if(!contributors.contains(contributor)){
            contributors.add(contributor);
        }
    }

    public ArrayList<UAContributor> getContributors(){
        return contributors;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        else if(other instanceof UAVolume) {
            UAVolume u = (UAVolume) other;
            if(u.number == this.number) return true;
        }
        return false;
    }
}
