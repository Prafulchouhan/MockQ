package com.server.mock.helper;

import com.server.mock.model.User;
import com.server.mock.model.exam.QuizAttempt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class QuizAttemptExclHelper {


    public static String[] USER_HEADER = {
            "id",
            "User_Name",
            "Email",
            "Phone No."
    };
    public static String[] ATTEMPT_HEADER = {
            "id",
            "DATE_OF_ATTEMPT",
            "START_TIME",
            "END_TIME",
            "TOTAL_QUESTIONS",
            "NO_OF_QUESTIONS_ATTEMPTED",
            "MARKS_GOT",
            "TOTAL_MARKS",
            "TIME_TAKEN_IN_MINUTES",
            "QUIZ_TITLE"
    };

    public static String SHEET_NAME = "userData";


    public static ByteArrayInputStream dataToExcel(List<QuizAttempt> quizAttempts,User user) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            Sheet sheet = workbook.createSheet(SHEET_NAME);

            Row row = sheet.createRow(0);

            for(int i=0;i<USER_HEADER.length;i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(USER_HEADER[i]);
            }

            Row dataRow = sheet.createRow(1);

            dataRow.createCell(0).setCellValue(user.getId());
            dataRow.createCell(1).setCellValue(user.getUserName());
            dataRow.createCell(2).setCellValue(user.getEmail());
            dataRow.createCell(3).setCellValue(user.getPhone());

            sheet.createRow(2);
            sheet.createRow(3);

            Row attemptHeader = sheet.createRow(4);

            for(int i=0;i<ATTEMPT_HEADER.length;i++){
                Cell cell = attemptHeader.createCell(i);
                cell.setCellValue(ATTEMPT_HEADER[i]);
            }

            int rowNum = 5;

            for (QuizAttempt q: quizAttempts
                 ) {

                Row AttemptDataRow = sheet.createRow(rowNum++);

                AttemptDataRow.createCell(0).setCellValue(q.getId());
                AttemptDataRow.createCell(1).setCellValue(q.getDateOfAttempt());
                AttemptDataRow.createCell(2).setCellValue(q.getStartTime());
                AttemptDataRow.createCell(3).setCellValue(q.getEndTime());
                AttemptDataRow.createCell(4).setCellValue(q.getTotalQuestions());
                AttemptDataRow.createCell(5).setCellValue(q.getNoOfQuestionsAttempted());
                AttemptDataRow.createCell(6).setCellValue(q.getMarksGot());
                AttemptDataRow.createCell(9).setCellValue(q.getTotalMarks());
                AttemptDataRow.createCell(7).setCellValue(q.getTimeTakenInMinutes());
                AttemptDataRow.createCell(8).setCellValue(q.getQuizTitle());

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
