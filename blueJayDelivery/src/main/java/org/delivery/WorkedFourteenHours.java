package org.delivery;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorkedFourteenHours {

    /**
     *
     * @param excelFilePathString
     * @return array list of people who worked more than 14 hrs
     * @throws Exception
     */
    public ArrayList<String> workedMoreThanFourteenHrs(String excelFilePathString) throws Exception {
        FileInputStream inputStream = new FileInputStream(excelFilePathString);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(0).getLastCellNum();

        ArrayList<String> personIds = new ArrayList<>();//for storing the persons who has worked more than 14 hrs

        String id = null;
        for (int r = 2; r <= rows; r++) {
            XSSFRow row = sheet.getRow(r);

            for (int c = 0; c < cols; c++) {
                XSSFCell cell = row.getCell(c);
                if (c == 0) {
                    id = cell.getStringCellValue(); // Get the person ID
                }
                //Get the shift time and check it is more than 14 hours
                if (c == 4 && !cell.getStringCellValue().isEmpty() && cell.getCellType() == CellType.STRING) {
                    try {
                        String cellValue = cell.getStringCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date dt = sdf.parse(cellValue);
                        sdf = new SimpleDateFormat("HH");
                        String hour = sdf.format(dt);
                        if (Integer.parseInt(hour) >= 14) {
                            personIds.add(id);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(cell.getStringCellValue());
                    }

                }
            }
        }
        return personIds;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\DELL\\IdeaProjects\\blueJayDelivery\\src\\main\\resources\\Assignment_Timecard.xlsx";
        WorkedFourteenHours workedFourteenHours = new WorkedFourteenHours();
        System.out.println(workedFourteenHours.workedMoreThanFourteenHrs(filePath));
    }
}
