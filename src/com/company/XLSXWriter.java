package com.company;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
}
