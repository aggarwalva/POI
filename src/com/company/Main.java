package com.company;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        writeCollabSheet();
    }

    public static void printContributors() {
        ArrayList<UAContributor> contributors;
        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        contributors = reader.getUAContributors();
        for(UAContributor c : contributors) {
            System.out.print(c.getName() + ": Volumes ");
            ArrayList<Integer> volumes = c.getVolumes();
            for(Integer i : volumes) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void printVolumes() {
        ArrayList<UAVolume> volumes;
        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        volumes = reader.getUAVolumes();
        ArrayList<UAContributor> contributors;
        for(UAVolume v : volumes) {
            System.out.print(v.getNumber() + " ");
            contributors = v.getContributors();
            for(UAContributor c : contributors) {
                System.out.print(c.getName() + " ");
            }
            System.out.println();
        }
    }

    public static void writeVolumesSpreadsheet() {
        XLSXWriter writer = new XLSXWriter("VolumesSheet.xlsx");
        writer.writeVolumesSheet();
    }

    public static void writeCollabSheet() {
        XLSXWriter writer = new XLSXWriter("CollabSheet.xlsx");
        writer.writeCollabSheet();
    }
}
