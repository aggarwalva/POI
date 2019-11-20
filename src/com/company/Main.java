package com.company;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        printRanks();
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

    public static void printPoems() {
        ArrayList<UAPoem> poems;
        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        poems = reader.getUAPoems();

        for(UAPoem poem : poems) {
            System.out.println(poem.getName() + " " + poem.getVolume() + " " + poem.getAuthor());
        }
    }

    public static void printMatrix() {
        XLSXReader reader = new XLSXReader("PoemMatrix.xlsx");
        Matrix m = reader.getMatrix();
        m.printMatrix();
    }

    public static void printEigenvector() {
        XLSXReader reader = new XLSXReader("PoemMatrix.xlsx");
        Matrix m = reader.getMatrix();
        Matrix e = m.getEigenvector(1000);
        e.printMatrix();
    }

    public static void printRanks() {
        XLSXReader reader = new XLSXReader("PoemMatrix.xlsx");

        Matrix m = reader.getMatrix();
        Matrix e = m.getEigenvector(1000);
        double[][] data = e.getData();
        double[][] sorted = e.sortEigenvector().getData();

        reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        ArrayList<UAPoem> poems = reader.getUAPoems();

        int rank;
        for(int i = 0; i < poems.size(); i++) {
            rank = Arrays.asList(sorted).indexOf(data[i]) + 1;
            poems.get(i).setRank(rank);
        }

        Collections.sort(poems, new Comparator<UAPoem>() {
            @Override
            public int compare(UAPoem o1, UAPoem o2) {
                return o1.getRank() - o2.getRank();
            }
        });

        for(UAPoem p : poems){
            System.out.println("Name: " + p.getName() + ", Rank: " + p.getRank());
        }

        try {
            FileWriter out = new FileWriter(new File("Ranks.txt"));
            for(UAPoem p : poems){
                out.write("Name: " + p.getName() + ", Rank: " + p.getRank() + "\n");
            }
            out.close();
        } catch (Exception ex){
            //do nothing
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

    public static void writePoemsMatrix() {
        XLSXWriter writer = new XLSXWriter("PoemMatrix.xlsx");
        writer.writePoemsMatrix();
    }

    public static void writePoemsSheet() {
        XLSXWriter writer = new XLSXWriter("PoemsSheet.xlsx");
        writer.writePoemsSheet();
    }

    public static void matrixTest() {
        double[][] aData = {{1,2}, {3,1}};
        double[][] bData = {{2}, {3}};

        Matrix a = new Matrix(aData);
        Matrix b = new Matrix(bData);
        Matrix c = a.multiply(b);
        c.printMatrix();
    }
}
