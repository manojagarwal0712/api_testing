package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider (name = "Data")
    public Object[][] getALlData(){
        String path = System.getProperty("user.dir")+"//UserData.xls";
        ExcelUtilities excelUtilities = new ExcelUtilities(path, "Sheet1");
        int rowCount = excelUtilities.getRowCount();
        int colCount = excelUtilities.getColumnCount();
        String[][] apiData = new String[rowCount][colCount];
        for (int i = 0;i <rowCount; i++){
            for (int j = 0; j<colCount; j++){
                apiData[i][j] = excelUtilities.getCellData(i, j);
            }
        }
        return apiData;
    }


}

