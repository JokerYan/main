package seedu.duke.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import seedu.duke.Duke;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    private static Sheet sheet;

    private static String getExcelDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "email source.xlsx";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "email source.xlsx";
        } else {
            dir = "." + File.separator + "email source.xlsx";
        }
        return dir;
    }

    //this function is adapted from https://www.baeldung.com/java-microsoft-excel
    public static Sheet getSheet() throws ExcelException {
        if (sheet != null) {
            return sheet;
        }
        FileInputStream file = null;
        try {
            file = new FileInputStream(new File(getExcelDir()));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet newSheet = workbook.getSheetAt(0);
            sheet = newSheet;
            return sheet;
        } catch (FileNotFoundException e) {
            throw new ExcelException("Excel source not found...");
        } catch (IOException e) {
            throw new ExcelException("Open workbook failed...");
        }
    }

    public static void printTitles() {
        Row row = null;
        try {
            row = getSheet().getRow(0);
            Iterator<Cell> iterator = row.iterator();
            while (iterator.hasNext()) {
                Duke.getUI().showMessage(iterator.next().getStringCellValue());
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }


    }

    public static class ExcelException extends Exception {
        private String msg;

        public ExcelException(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

}
