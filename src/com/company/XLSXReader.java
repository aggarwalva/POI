package com.company;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class XLSXReader {
    String path;
    FileInputStream in = null;
    XSSFWorkbook workbook = null;

    public XLSXReader(String path) {
        this.path = path;
        try {
            in = new FileInputStream(new File(path));
            workbook = new XSSFWorkbook(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UAContributor> getUAContributors() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        ArrayList<UAContributor> contributors = new ArrayList<>();
        UAContributor temp;
        Row r;
        for(int i = 1; i <= sheet.getLastRowNum(); i++) {
            r = sheet.getRow(i);
            temp = new UAContributor(r.getCell(2).getStringCellValue());
            if(contributors.contains(temp)){
                temp = contributors.get(contributors.indexOf(temp));
                temp.addVolume((int) r.getCell(0).getNumericCellValue());
            } else {
                temp.addVolume((int) r.getCell(0).getNumericCellValue());
                contributors.add(temp);
            }

            try {
                temp = new UAContributor(r.getCell(3).getStringCellValue());

                if(contributors.contains(temp)){
                    temp = contributors.get(contributors.indexOf(temp));
                    temp.addVolume((int) r.getCell(0).getNumericCellValue());
                } else {
                    temp.addVolume((int) r.getCell(0).getNumericCellValue());
                    contributors.add(temp);
                }
            } catch (NullPointerException e) {
                //System.out.println("Caught null");
            }
        }
        return contributors;
    }

    public ArrayList<UAVolume> getUAVolumes() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        ArrayList<UAVolume> volumes = new ArrayList<>();
        UAVolume temp;
        Row r;

        for(int i = 1; i < sheet.getLastRowNum(); i++){
            r = sheet.getRow(i);
            temp = new UAVolume((int) r.getCell(0).getNumericCellValue());
            if(volumes.contains(temp)) {
                temp = volumes.get(volumes.indexOf(temp));
                temp.addContributor(new UAContributor(r.getCell(2).getStringCellValue()));

                try {
                    temp.addContributor(new UAContributor(r.getCell(3).getStringCellValue()));
                } catch (NullPointerException e) {
                    //No second author
                }
            } else {
                temp.addContributor(new UAContributor(r.getCell(2).getStringCellValue()));
                try {
                    temp.addContributor(new UAContributor(r.getCell(3).getStringCellValue()));
                } catch (NullPointerException e) {
                    //No second author
                }
                volumes.add(temp);
            }
        }
        return volumes;
    }

    public ArrayList<UAPoem> getUAPoems() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        ArrayList<UAPoem> poems = new ArrayList<>();
        UAPoem temp;
        Row r;

        for(int i = 1; i <= sheet.getLastRowNum(); i++) {
            r = sheet.getRow(i);
            temp = new UAPoem(r.getCell(1).getStringCellValue(), r.getCell(2).getStringCellValue(), (int) r.getCell(0).getNumericCellValue());
            poems.add(temp);
        }

        return poems;
    }

    public Matrix getMatrix() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        int height = sheet.getLastRowNum() + 1;
        int width = sheet.getRow(0).getPhysicalNumberOfCells();
        double[][] data = new double[height][width];
        Row r;

        for(int i = 0; i < height; i++) {
            r = sheet.getRow(i);
            for(int j = 0; j < width; j++) {
                data[i][j] = r.getCell(j).getNumericCellValue();
            }
        }

        return new Matrix(data);
    }

}
