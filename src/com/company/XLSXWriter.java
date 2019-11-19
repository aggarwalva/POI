package com.company;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class XLSXWriter {
    String path;
    FileOutputStream out = null;
    XSSFWorkbook workbook = null;

    public XLSXWriter(String path) {
        this.path = path;
        try {
            out = new FileOutputStream(new File(path));
            workbook = new XSSFWorkbook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish() {
        try {
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupOnodoSheet() {
        XSSFSheet nodes = workbook.createSheet("Nodes");
        XSSFSheet relations = workbook.createSheet("Relations");
        Row temp = nodes.createRow(0);
        temp.createCell(0).setCellValue("Name");
        temp.createCell(1).setCellValue("Type");
        temp.createCell(2).setCellValue("Description");
        temp = relations.createRow(0);
        temp.createCell(0).setCellValue("Source");
        temp.createCell(1).setCellValue("Type");
        temp.createCell(2).setCellValue("Target");
        temp.createCell(3).setCellValue("Directed");
    }

    public void writeVolumesSheet() {
        setupOnodoSheet();

        ArrayList<UAVolume> volumes;
        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        volumes = reader.getUAVolumes();
        XSSFSheet nodes = workbook.getSheetAt(0);
        XSSFSheet relations = workbook.getSheetAt(1);

        Row temp;
        for(int i = 0; i < volumes.size(); i++) {
            temp = nodes.createRow(i+1);
            temp.createCell(0).setCellValue("Volume " + volumes.get(i).getNumber());
            temp.createCell(1).setCellValue("Volume");
        }

        ArrayList<UAContributor> contributors = reader.getUAContributors();

        int end = nodes.getLastRowNum() + 1;
        for(int i = 0; i < contributors.size(); i++) {
            temp = nodes.createRow(i + end);
            temp.createCell(0).setCellValue(contributors.get(i).getName());
            temp.createCell(1).setCellValue("Contributor");
        }

        ArrayList<Integer> volumeNumbers;
        for(UAContributor c : contributors) {
            volumeNumbers = c.getVolumes();
            for(Integer i : volumeNumbers) {
                temp = relations.createRow(relations.getLastRowNum() + 1);
                temp.createCell(0).setCellValue(c.getName());
                temp.createCell(2).setCellValue("Volume " + i);
                temp.createCell(3).setCellValue(0);
            }
        }

        publish();
    }

    public void writeCollabSheet() {
        setupOnodoSheet();

        ArrayList<UAVolume> volumes;
        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        volumes = reader.getUAVolumes();
        XSSFSheet nodes = workbook.getSheetAt(0);
        XSSFSheet relations = workbook.getSheetAt(1);

        Row temp;
        ArrayList<UAContributor> contributors = reader.getUAContributors();

        for(int i = 0; i < contributors.size(); i++) {
            temp = nodes.createRow(i + 1);
            temp.createCell(0).setCellValue(contributors.get(i).getName());
            temp.createCell(1).setCellValue("Contributor");
        }

        for(UAVolume v : volumes) {
            contributors = v.getContributors();
            for(int i = 0; i < contributors.size(); i++){
                for(int j = i + 1; j < contributors.size(); j++){
                    temp = relations.createRow(relations.getLastRowNum() + 1);
                    temp.createCell(0).setCellValue(contributors.get(i).getName());
                    temp.createCell(2).setCellValue(contributors.get(j).getName());
                    temp.createCell(3).setCellValue(0);
                }
            }
        }

        publish();
    }

    public void writePoemsMatrix() {
        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        ArrayList<UAPoem> poems = reader.getUAPoems();
        UAPoem a,b;
        XSSFSheet sheet = workbook.createSheet();
        Row r;

        for(int i = 0; i < poems.size(); i++){
            r = sheet.createRow(i);
            for(int j = 0; j < poems.size(); j++) {
                r.createCell(j);
            }
        }

        int counter;
        double entry;
        double beta = (0.15 * (1.0/170));
        for(int i = 0; i < poems.size(); i++) {
            a = poems.get(i);
            counter = 0;
            for(int j = 0; j < poems.size(); j++) {
                b = poems.get(j);
                if(a.getAuthor().equals(b.getAuthor()) || a.getVolume() == b.getVolume()) {
                    counter++;
                }
            }
            entry = (0.85 * (1.0/counter)) + beta;
            System.out.println("Entry: " + entry + " Counter: " + counter);

            for(int j = 0; j < poems.size(); j++) {
                b = poems.get(j);
                if(a.getAuthor().equals(b.getAuthor()) || a.getVolume() == b.getVolume()) {
                    r = sheet.getRow(j);
                    r.getCell(i).setCellValue(entry);
                } else {
                    r = sheet.getRow(j);
                    r.getCell(i).setCellValue(beta);
                }
            }
        }

        publish();
    }

    public void writePoemsSheet() {
        setupOnodoSheet();
        XSSFSheet nodes = workbook.getSheetAt(0);
        XSSFSheet relations = workbook.getSheetAt(1);
        nodes.getRow(0).createCell(3).setCellValue("Poem Rank");
        nodes.getRow(0).createCell(4).setCellValue("Size");

        XLSXReader reader = new XLSXReader("C:\\Users\\Varun\\Desktop\\United Artists.xlsx");
        ArrayList<UAPoem> poems = reader.getUAPoems();
        reader = new XLSXReader("PoemMatrix.xlsx");
        Matrix m = reader.getMatrix().getEigenvector(1000);
        double[][] data = m.getData();
        double[][] sorted = m.sortEigenvector().getData();

        UAPoem a,b;
        Row r;
        int rank;

        for(int i = 0; i < poems.size(); i++) {
            r = nodes.createRow(i + 1);
            a = poems.get(i);
            rank = Arrays.asList(sorted).indexOf(data[i]) + 1;
            System.out.println(rank);
            r.createCell(0).setCellValue(a.getName());
            r.createCell(1).setCellValue("Poem");
            r.createCell(2).setCellValue("Volume: " + a.getVolume() + " Author: " + a.getAuthor() + " Rank: " + rank);
            r.createCell(3).setCellValue(data[i][0]);
            r.createCell(4).setCellValue(data[i][0] * 3000);
        }

        for(int i = 0; i < poems.size(); i++) {
            a = poems.get(i);
            for(int j = 0; j < poems.size(); j++) {
                b = poems.get(j);
                if(a.getAuthor().equals(b.getAuthor()) || a.getVolume() == b.getVolume()) {
                    r = relations.createRow(relations.getLastRowNum() + 1);
                    r.createCell(0).setCellValue(a.getName());
                    r.createCell(2).setCellValue(b.getName());
                    r.createCell(3).setCellValue(0);
                }
            }
        }

        publish();
    }
}
