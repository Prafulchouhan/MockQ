package com.server.mock.helper;

import com.server.mock.model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class UserExclHelper {

    public static String[] HEADER = {
            "id",
            "User_Name",
            "Email",
            "Phone No."
    };

    public static String SHEET_NAME = "userData";


    public static ByteArrayInputStream dataToExcel(List<User> user) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            Sheet sheet = workbook.createSheet(SHEET_NAME);

            Row row = sheet.createRow(0);

            for(int i=0;i<HEADER.length;i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }
            
            int rowIndex = 1;

            for (User u: user
                 ) {
                Row dataRow = sheet.createRow(rowIndex++);

                dataRow.createCell(0).setCellValue(u.getId());
                dataRow.createCell(1).setCellValue(u.getUserName());
                dataRow.createCell(2).setCellValue(u.getEmail());
                dataRow.createCell(3).setCellValue(u.getPhone());
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        finally {
            out.close();
            workbook.close();
        }
    }
}
