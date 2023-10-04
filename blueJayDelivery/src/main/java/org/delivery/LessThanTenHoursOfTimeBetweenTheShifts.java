package org.delivery;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LessThanTenHoursOfTimeBetweenTheShifts {
    public static void main(String[] args) throws IOException, ParseException {

        HashMap<String, HashMap<Integer, Integer>> h = new HashMap<>();
        HashMap<Integer, Integer> h1 = null;
        HashMap<Integer, Integer> h2 = null;
        HashMap<String,String> h3 =new HashMap<>();
        HashMap<String,String> h4 =new HashMap<>();
        ArrayList<String> a2 = new ArrayList<>();
        String excelFilePathString = "D:\\java\\bluejoy_project\\src\\main\\resources\\data\\Assignment_Timecard.xlsx";
        FileInputStream inputStream = new FileInputStream(excelFilePathString);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        String position_id = "";
        String name = "";
        int date = 0;
        int i = 0;
        int k = 0;
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(0).getLastCellNum();
        for (int r = 0; r <= rows; r++) {
            XSSFRow row = sheet.getRow(r);

            for (int c = 0; c < cols; c++) {
                XSSFCell cell = row.getCell(c);
                switch (cell.getCellType()) {
                    case NUMERIC:
                        Date javaDate = DateUtil.getJavaDate((double) cell.getNumericCellValue());
                        String a = new SimpleDateFormat("dd").format(javaDate);
                        if (c == 2) {
                            date = Integer.parseInt(a);
                        }
                        break;
                    case STRING:
                        if (c == 0) {
                            position_id = cell.getStringCellValue();
//                            System.out.println(position_id);

                        }
                        if(c==7){
                            name=cell.getStringCellValue();
                            h3.put(position_id,name);
                        }

                        if (c == 4) {

//                            System.out.println(cell.getStringCellValue());
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            //SimpleDateFormat sdf1 = new SimpleDateFormat("mm");
                            try {
                                Date dt = sdf.parse(cell.getStringCellValue());
//                                System.out.println(dt);
                                sdf = new SimpleDateFormat("hh");
//                                sdf1 = new SimpleDateFormat("mm");
                                String s1 = sdf.format(dt);
                                sdf = new SimpleDateFormat("mm");
                                String s2 = sdf.format(dt);
                                int i1 = Integer.parseInt(s1);
//                                System.out.println(position_id+"   "+i1);
                                int i2 = Integer.parseInt(s2);
                                int i5 = 0;
                                if (i1 == 12) {
                                    i5 = 0;
                                } else {
                                    i5 = i1 * 60;
                                }
                                int i3 = i5 + i2;
//                                System.out.println("Time="+i3);

                                if (h.get(position_id) == null) {

                                    h1 = new HashMap<Integer, Integer>();
                                    if (h1.get(date) == null) {
                                        h1.put(date, i3);

                                        h.put(position_id, h1);
                                    }
                                } else {
                                    if (h1.get(date) == null) {
                                        h1.put(date, i3);
                                    } else {
                                        int i4 = h1.get(date);
                                        h1.put(date, i4 + i3);
                                    }


                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

//                        System.out.print(cell.getStringCellValue()+" \t ");
                        break;


                }

            }

        }
//        System.out.println(h);
        Set<String> keys = h.keySet();
        Iterator<String> Keyitr = keys.iterator();
//        System.out.println(h.get("WFS000065"));
        while (Keyitr.hasNext()) {
            String key = Keyitr.next();
            h2 = h.get(key);
            Set<Integer> keys1 = h2.keySet();
            Iterator<Integer> Key2itr = keys1.iterator();
            int f = 1;
            while (Key2itr.hasNext()) {
                int m = Key2itr.next();
                if ((h2.get(m) / 60 <10)&&((h2.get(m))/60>1)) {
                    f = 0;
                }
            }
            if (f == 0) {
                h4.put(key,h3.get(key));
            }


        }

//        System.out.println("      dddddddddddddddddd          ");
//        System.out.println(h.get("WFS000578"));
        System.out.println(h4);
    }
}


