package com.example.pmbakanov.configurations;

import com.example.pmbakanov.models.ElectricityMeterReading;
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

/**
 * Класс конфигурации для экспорта данных в Excel.
 */
public class ExcelExportUtils {
    // Класс для создания документа
    private final XSSFWorkbook workbook;
    // Лист для показаний текущего месяца
    private XSSFSheet sheetCurrentMonth;
    // Лист для всех показаний
    private XSSFSheet sheet;
    // Лист для электричества
    private XSSFSheet sheetElectricity;
    // Список показаний
    private final List<MeterReading> meterReadingList;
    // Список показаний электричества
    private final List<ElectricityMeterReading> electricityMeterReadingList;

    /**
     * Конструктор класса.
     *
     * @param meterReadingList         список показаний
     * @param electricityMeterReadingList список показаний электричества
     */
    public ExcelExportUtils(List<MeterReading> meterReadingList, List<ElectricityMeterReading> electricityMeterReadingList) {

        // Инициализация списка показаний
        this.meterReadingList = meterReadingList;

        // Инициализация списка показаний электричества
        this.electricityMeterReadingList = electricityMeterReadingList;

        // Создание нового документа Excel
        workbook = new XSSFWorkbook();
    }

    /**
     * Создает ячейку в указанной строке и столбце с заданным значением и стилем.
     *
     * @param row         строка, в которой создается ячейка
     * @param columnCount номер столбца, в котором создается ячейка
     * @param value       значение, которое необходимо установить в ячейку
     * @param style       стиль ячейки
     */
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {

        // Установка размера колонок
        sheet.autoSizeColumn(columnCount);
        sheetCurrentMonth.autoSizeColumn(columnCount);
        sheetElectricity.autoSizeColumn(columnCount);

        // Создание ячейки
        Cell cell = row.createCell(columnCount);

        // Установка значения в ячейку
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
        // Установка стиля ячейки
        cell.setCellStyle(style);
    }

    /**
     * Создает заголовочную строку для каждого листа документа.
     */
    private void createHeaderRow() {

        // Создание листов для показаний
        sheetCurrentMonth = workbook.createSheet(LocalDateTime.now().getMonth().name() + " " + LocalDateTime.now().getYear());
        sheet = workbook.createSheet("Все показания");
        sheetElectricity = workbook.createSheet("Электричество");

        // Создание строки для заголовка
        Row row = sheet.createRow(0);
        Row rowCurrentMonth = sheetCurrentMonth.createRow(0);
        Row rowElectricity = sheetElectricity.createRow(0);

        // Создание стиля для заголовка
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        // Создание заголовка
        createCell(row, 0, "Показания индвидуальных приборов учета", style);
        createCell(rowCurrentMonth, 0, "Показания индвидуальных приборов учета", style);
        createCell(rowElectricity, 0, "Показания индвидуальных приборов учета", style);

        // Слияние ячеек для заголовка
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        sheetCurrentMonth.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        sheetElectricity.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        font.setFontHeightInPoints((short) 10);

        // Создание второй строки для заголовка
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        // Создание ячеек для второго заголовка
        createCell(row, 0, "Дата создания", style);
        createCell(row, 1, "Имя", style);
        createCell(row, 2, "Адрес", style);
        createCell(row, 3, "Кухня (ход. вода)", style);
        createCell(row, 4, "Кухня (гор. вода)", style);
        createCell(row, 5, "Ванная (ход. вода)", style);
        createCell(row, 6, "Ванная (гор. вода)", style);
        createCell(row, 7, "Сосед (кухня хол. вода)", style);
        createCell(row, 8, "Сосед (кухня гор. вода)", style);

        // Создание второй строки для заголовка листа текущего месяца
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

        // Создание второй строки для заголовка листа электричества
        rowElectricity = sheetElectricity.createRow(1);
        createCell(rowElectricity, 0, "Дата создания", style);
        createCell(rowElectricity, 1, "Имя", style);
        createCell(rowElectricity, 2, "Адрес", style);
        createCell(rowElectricity, 3, "Электричество", style);
        createCell(rowElectricity, 4, "Данные внес", style);
    }

    /**
     * Заполняет листы документа данными из списков показаний и показаний электричества.
     */
    private void writeCustomerData() {
        int rowCount = 2;
        int rowCountCurrent = 2;
        int rowCountElectricity = 2;

        // Создание стиля для данных
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        // Заполнение данными списка показаний
        for (MeterReading meterReading : meterReadingList) {
            if (meterReading.getDateOfCreated().getMonth() == LocalDateTime.now().getMonth() && meterReading.getDateOfCreated().getYear() == LocalDateTime.now().getYear()) {
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

        // Заполнение данными списка показаний электричества
        for (ElectricityMeterReading electricityMeterReading : electricityMeterReadingList) {
            if (electricityMeterReading.getElectricity() != null) {
                Row row = sheetElectricity.createRow(rowCountElectricity++);
                int columnCount = 0;
                createCell(row, columnCount++, electricityMeterReading.getDateOfCreatedString(), style);
                createCell(row, columnCount++, electricityMeterReading.getUser().getName(), style);
                createCell(row, columnCount++, electricityMeterReading.getUser().getAddress(), style);
                createCell(row, columnCount++, electricityMeterReading.getElectricity(), style);
                createCell(row, columnCount, electricityMeterReading.getDataProviderName(), style);
            }
        }

    }

    /**
     * Экспортирует данные в Excel и отправляет документ в ответ на запрос HTTP.
     *
     * @param response объект HttpServletResponse для отправки данных
     * @throws IOException если возникает ошибка ввода-вывода при отправке данных
     */
    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow(); // Создание заголовка таблицы
        writeCustomerData(); // Запись данных
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream); // Закрытие документа
        workbook.close(); // Закрытие выходного потока
        outputStream.close();
    }
}
