package com.example.pmbakanov.configurations;

import com.example.pmbakanov.models.ElectricityRecord;
import com.example.pmbakanov.models.MeterReading;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ExcelExportUtils {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheetCurrentMonth;
    private XSSFSheet sheet;
    private XSSFSheet sheetElectricity;
    private final List<MeterReading> meterReadingList;
    private final List<ElectricityRecord> electricityRecordList;

    public ExcelExportUtils(List<MeterReading> meterReadingList, List<ElectricityRecord> electricityRecordList) {
        this.meterReadingList = meterReadingList;
        this.electricityRecordList = electricityRecordList;
        workbook = new XSSFWorkbook();
    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        sheetCurrentMonth.autoSizeColumn(columnCount);
        sheetElectricity.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue(Math.round((Double) value * 100.0) / 100.0);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Float) {
            cell.setCellValue(Math.round((Float) value * 100.0) / 100.0);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow() {
        sheetCurrentMonth = workbook.createSheet(LocalDateTime.now().getMonth().name() + " " + LocalDateTime.now().getYear());
        sheet = workbook.createSheet("Все показания");
        sheetElectricity = workbook.createSheet("Электричество");
        Row row = sheet.createRow(0);
        Row rowCurrentMonth = sheetCurrentMonth.createRow(0);
        Row rowElectricity = sheetElectricity.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "Показания индвидуальных приборов учета", style);
        createCell(rowCurrentMonth, 0, "Показания индвидуальных приборов учета", style);
        createCell(rowElectricity, 0, "Показания индвидуальных приборов учета", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        sheetCurrentMonth.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        sheetElectricity.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Дата создания", style);
        createCell(row, 1, "Имя", style);
        createCell(row, 2, "Адрес", style);
        createCell(row, 3, "Кухня (ход. вода)", style);
        createCell(row, 4, "Кухня (гор. вода)", style);
        createCell(row, 5, "Ванная (ход. вода)", style);
        createCell(row, 6, "Ванная (гор. вода)", style);
        createCell(row, 7, "Сосед (кухня хол. вода)", style);
        createCell(row, 8, "Сосед (кухня гор. вода)", style);

        rowCurrentMonth = sheetCurrentMonth.createRow(1);
        createCell(rowCurrentMonth, 0, "Дата создания", style);
        createCell(rowCurrentMonth, 1, "Имя", style);
        createCell(rowCurrentMonth, 2, "Адрес", style);
        createCell(rowCurrentMonth, 3, "Кухня (ход. вода)", style);
        createCell(rowCurrentMonth, 4, "Кухня (гор. вода)", style);
        createCell(rowCurrentMonth, 5, "Ванная (ход. вода)", style);
        createCell(rowCurrentMonth, 6, "Ванная (гор. вода)", style);
        createCell(rowCurrentMonth, 7, "Сосед (кухня хол. вода)", style);
        createCell(rowCurrentMonth, 8, "Сосед (кухня гор. вода)", style);

        rowElectricity = sheetElectricity.createRow(1);
        createCell(rowElectricity, 0, "Дата создания", style);
        createCell(rowElectricity, 1, "Имя", style);
        createCell(rowElectricity, 2, "Адрес", style);
        createCell(rowElectricity, 3, "Электричество", style);
        createCell(rowElectricity, 4, "Данные внес", style);
    }

    private void writeCustomerData() {
        int rowCount = 2;
        int rowCountCurrent = 2;
        int rowCountElectricity = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (MeterReading meterReading : meterReadingList) {
            if (meterReading.getDateOfCreated().getMonth() == LocalDateTime.now().getMonth()) {
                Row row = sheetCurrentMonth.createRow(rowCountCurrent++);
                int columnCount = 0;
                createCell(row, columnCount++, meterReading.getDateOfCreatedString(), style);
                createCell(row, columnCount++, meterReading.getUser().getName(), style);
                createCell(row, columnCount++, meterReading.getUser().getAddress(), style);
                createCell(row, columnCount++, meterReading.getKitchenCold(), style);
                createCell(row, columnCount++, meterReading.getKitchenHot(), style);
                createCell(row, columnCount++, meterReading.getToiletCold(), style);
                createCell(row, columnCount++, meterReading.getToiletHot(), style);
                createCell(row, columnCount++, meterReading.getNeighborCold(), style);
                createCell(row, columnCount, meterReading.getNeighborHot(), style);
            }
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, meterReading.getDateOfCreatedString(), style);
            createCell(row, columnCount++, meterReading.getUser().getName(), style);
            createCell(row, columnCount++, meterReading.getUser().getAddress(), style);
            createCell(row, columnCount++, meterReading.getKitchenCold(), style);
            createCell(row, columnCount++, meterReading.getKitchenHot(), style);
            createCell(row, columnCount++, meterReading.getToiletCold(), style);
            createCell(row, columnCount++, meterReading.getToiletHot(), style);
            createCell(row, columnCount++, meterReading.getNeighborCold(), style);
            createCell(row, columnCount, meterReading.getNeighborHot(), style);
        }

        for (ElectricityRecord electricityRecord : electricityRecordList) {
            if (electricityRecord.getElectricity() != null) {
                Row row = sheetElectricity.createRow(rowCountElectricity++);
                int columnCount = 0;
                createCell(row, columnCount++, electricityRecord.getDateOfCreatedString(), style);
                createCell(row, columnCount++, electricityRecord.getUser().getName(), style);
                createCell(row, columnCount++, electricityRecord.getUser().getAddress(), style);
                createCell(row, columnCount++, electricityRecord.getElectricity(), style);
                createCell(row, columnCount, electricityRecord.getDataProviderName(), style);
            }
        }

    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeCustomerData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
