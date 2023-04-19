package com.example.pmbakanov.configurations;


import com.example.pmbakanov.models.Record;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExportUtils {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Record> recordList;

    public ExcelExportUtils(List<Record> recordList) {
        this.recordList = recordList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if (value instanceof Double){
            cell.setCellValue((Double) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long){
            cell.setCellValue((Long) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow(){
        sheet   = workbook.createSheet("Все показания");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "Показания индвидуальных приборов учета", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
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
    }

    private void writeCustomerData(){
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Record record : recordList){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getDateOfCreatedString(), style);
            createCell(row, columnCount++, record.getUser().getName(), style);
            createCell(row, columnCount++, record.getUser().getAddress(), style);
            createCell(row, columnCount++, record.getKitchenCold(), style);
            createCell(row, columnCount++, record.getKitchenHot(), style);
            createCell(row, columnCount++, record.getToiletCold(), style);
            createCell(row, columnCount++, record.getToiletHot(), style);
            createCell(row, columnCount++, record.getNeighborCold(), style);
            createCell(row, columnCount, record.getNeighborHot(), style);
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
