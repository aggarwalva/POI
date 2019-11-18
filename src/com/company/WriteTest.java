package com.company;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class WriteTest {
    public static void main(String[] args) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test");

        for(int i = 0; i < 100; i++) {
            Row row = sheet.createRow(i);
            for(int j = 0; j < 100; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(i + j);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("POITest.xlsx"));
            workbook.write(out);
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
