package com.sangram.qa.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtils {
    private ExcelUtils() {
    }

    public static List<Map<String, String>> readSheet(String workbookResource, String sheetName) {
        try (Workbook workbook = new XSSFWorkbook(ResourceUtils.openResource(workbookResource))) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalStateException("Sheet not found: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
                headers.add(formatter.formatCellValue(headerRow.getCell(cellIndex)));
            }

            List<Map<String, String>> rows = new ArrayList<>();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                Map<String, String> rowData = new LinkedHashMap<>();
                boolean hasValue = false;

                for (int cellIndex = 0; cellIndex < headers.size(); cellIndex++) {
                    String value = formatter.formatCellValue(row.getCell(cellIndex));
                    if (!value.isBlank()) {
                        hasValue = true;
                    }
                    rowData.put(headers.get(cellIndex), value);
                }

                if (hasValue) {
                    rows.add(rowData);
                }
            }

            return rows;
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read Excel resource: " + workbookResource, exception);
        }
    }
}
