package api.utilities;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
    public class ExcelUtilities {
        private Workbook workbook;
        private Sheet sheet;
        public ExcelUtilities(String filePath, String sheetName) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(filePath));
                workbook = WorkbookFactory.create(fileInputStream);
                sheet = workbook.getSheet(sheetName);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public int getRowCount() {
            return sheet.getPhysicalNumberOfRows();
        }
        public int getColumnCount() {
            int rowCount = getRowCount();
            int maxColumnCount = 0;
            for (int i = 0; i < rowCount; i++) {
                int currentColumnCount = sheet.getRow(i).getPhysicalNumberOfCells();
                if (currentColumnCount > maxColumnCount) {
                    maxColumnCount = currentColumnCount;
                }
            }
            return maxColumnCount;
        }
        public String getCellData(int rowNum, int colNum) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                Cell cell = row.getCell(colNum);
                if (cell != null) {
                    return cell.toString();
                }
            }
            return "";
        }
        public void writeDataInCell(int rowNum, int colNum, String data) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            Cell cell = row.createCell(colNum);
            cell.setCellValue(data);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File("output.xlsx"));
                workbook.write(fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Close the workbook to release resources
        public void closeWorkbook() {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
