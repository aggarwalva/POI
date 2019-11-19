package com.company;

import java.util.ArrayList;

public class UAContributor {
    private String name;
    private ArrayList<Integer> volumes;

    public UAContributor(String name) {
        this.name = name;
        volumes = new ArrayList<>();
    }

    public void addVolume(Integer i) {
        if(!volumes.contains(i)) {
            volumes.add(i);
        }
    }

    public ArrayList<Integer> getVolumes() {
        return volumes;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        else if(other instanceof UAContributor) {
            UAContributor u = (UAContributor) other;
            if(u.name.equals(this.name)) return true;
        }

        return false;
    }
}
