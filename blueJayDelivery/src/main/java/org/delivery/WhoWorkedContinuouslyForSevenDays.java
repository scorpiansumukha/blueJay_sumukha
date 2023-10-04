package org.delivery;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class WhoWorkedContinuouslyForSevenDays {

    /**
     *
     * @param filePath excel filepath
     * @return Hash map with key as position id and value as name
     * @throws Exception
     */
    public HashMap<String, String> whoWorkedContinuouslyForSevenDays(String filePath) throws Exception {
        String excelFilePathString = filePath;
        FileInputStream inputStream = new FileInputStream(excelFilePathString);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        HashMap<String, TreeSet<Integer>> empWorkDay = new HashMap<>();
        HashMap<String, String> allEmpIdEmpName = new HashMap<>();

        HashMap<String, String> empIdEmpNameWithSevenWorkDays = new HashMap<>();

        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(2).getLastCellNum();

        //here we iterate over the each row  and store the empname and days he worked.
        for (int r = 1; r <= rows; r++) {
            XSSFRow row = sheet.getRow(r);
            String empId = "";//personId is the column value in excel
            String name = "";
            int date = 0;

            for (int c = 0; c < cols; c++) {
                XSSFCell cell = row.getCell(c);
                //get the empid
                if (c == 0) {
                    empId = cell.getStringCellValue();
                }
                //get the personId and store empId and empname
                if (c == 7) {
                    name = cell.getStringCellValue();
                    allEmpIdEmpName.put(empId, name);
                }

                //Get the value of 2nd column, which has time in data
                if (c == 2 && cell.getCellType() == NUMERIC) {
                    Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                    String a = new SimpleDateFormat("dd").format(javaDate);//extract only date from timestamp

                    date = Integer.parseInt(a);
                    if (empWorkDay.get(empId) == null) {
                        TreeSet t = new TreeSet<>();
                        t.add(date);
                        empWorkDay.put(empId, t);
                    } else {
                        TreeSet t = empWorkDay.get(empId);
                        t.add(date);
                    }
                }
            }
        }

        Set<String> keys = empWorkDay.keySet();
        Iterator<String> keyItr = keys.iterator();
        //Calculate who has worked continuously for more than 7 days
        while (keyItr.hasNext()) {
            String empId = keyItr.next();
            ArrayList<Integer> daysList = new ArrayList<>(empWorkDay.get(empId));
            int n = 0;
            if (daysList.size() >= 7) {
                for (int i = 0; i < daysList.size() - 1; i++) {
                    if (daysList.get(i + 1) - daysList.get(i) == 1) {
                        n++;
                    } else {
                        n = 0;
                    }
                }
            }
            if (n >= 7) {
                String empName = allEmpIdEmpName.get(empId);
                empIdEmpNameWithSevenWorkDays.put(empId, empName);
            }
        }
        return empIdEmpNameWithSevenWorkDays;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\DELL\\IdeaProjects\\blueJayDelivery\\src\\main\\resources\\Assignment_Timecard.xlsx";
        WhoWorkedContinuouslyForSevenDays whoWorkedContinuouslyForSevenDays = new WhoWorkedContinuouslyForSevenDays();
        System.out.println(whoWorkedContinuouslyForSevenDays.whoWorkedContinuouslyForSevenDays(filePath));
    }

}