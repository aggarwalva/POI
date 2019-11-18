package com.company;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ReadTest {
    public static void main(String[] args) {
        try {
            FileInputStream in = new FileInputStream(new File("POITest.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for(int i = 0; i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                for(int j = 0; j < row.getLastCellNum(); j++) {
                    System.out.print(row.getCell(j).getNumericCellValue() + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
