package Backend;

import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main() throws IOException {

        String excelFilePath = FileOperations.getExcelFilePath();                                   // Получаем путь к Excel файлу
        String txtFile_1_1_Path = FileOperations.getTxtFile_1_1_Path();                             // Получаем путь к 1_1 файлу
        String txtFile_1_3_Path = FileOperations.getTxtFile_1_3_Path();                             // Получаем путь к 1_3 файлу

        System.out.println(FileOperations.getExcelFilePath());
        System.out.println(FileOperations.getTxtFile_1_1_Path());
        System.out.println(FileOperations.getTxtFile_1_3_Path());



        // Списки, где будут храниться числа из файлов

        ArrayList<Double> numsOfFile_3 = new ArrayList<>(FileOperations.cutToList(txtFile_1_3_Path));                            // Добавляем числа в списки (файл 1_3)
        ArrayList<Double> numsOfFile_1 = new ArrayList<>(FileOperations.cutToList(txtFile_1_1_Path));                            // (файл 1_1)


        Workbook workbook = FileOperations.readWorkbook(excelFilePath);


        Sheet sheet = workbook.getSheet(Constants.MAIN_SHEET_NAME);

        // Записываем числа из файла 1_3

        for (Row row : sheet) {
            if (row.getRowNum() >= 4 && row.getRowNum() <= 55) {                                     // Ячейки с данными на строках 4 .. 55
                Cell cell = row.getCell(4);
                if (cell == null) {                                                                 // Если ячейка не существует
                    int j = 0;
                    for (int i = 4; i < numsOfFile_3.size() + 4; i++) {
                        Cell cellNums = row.createCell(i);                                           // Создаём ячейку
                        cellNums.setCellValue(numsOfFile_3.get(j));                                  // Добавляем значения из файла
                        j++;
                    }

                    // Добавляем PJI и версию в пустую строку:

                    Cell cellPJI = row.createCell(3);
                    cellPJI.setCellValue(FileOperations.getPJI());

                    Cell cellVersion = row.createCell(2);
                    cellVersion.setCellValue(FileOperations.getVersion());

                    // Записываем всё в книгу:

                    FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
                    workbook.write(fileOutputStream);
                    fileOutputStream.close();
                    break;

                } else if (WorkbookOperations.getCellValue(cell).toString().isEmpty()) {             // Если ячейка пуста, но отформатирована (например, есть рамки)

                    int j = 0;
                    for (int i = 4; i < numsOfFile_3.size() + 4; i++) {
                        Cell cellNums = row.getCell(i);                                              // Получаем ячейку
                        cellNums.setCellValue(numsOfFile_3.get(j));                                  // Добавляем значения из файла
                        j++;
                    }

                    // Добавляем PJI и версию в пустую строку:

                    Cell cellPJI = row.getCell(3);
                    cellPJI.setCellValue(FileOperations.getPJI());

                    Cell cellVersion = row.getCell(2);
                    cellVersion.setCellValue(FileOperations.getVersion());

                    // Записываем всё в книгу:

                    FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
                    workbook.write(fileOutputStream);
                    fileOutputStream.close();
                    break;
                }
            }
        }

        // Записываем числа из файла 1_1

        for (Row row : sheet) {
            if (row.getRowNum() >= 4 && row.getRowNum() <= 55) {                                     // Ячейки с данными на строках 4 .. 55
                Cell cell = row.getCell(30);
                if (cell == null) {                                                                 // Если ячейка не существует
                    int j = 0;
                    for (int i = 30; i < numsOfFile_1.size() + 30; i++) {
                        Cell cellNums = row.createCell(i);                                           // Создаём ячейку
                        cellNums.setCellValue(numsOfFile_1.get(j));                                  // Добавляем значения из файла
                        j++;
                    }

                    // Записываем всё в книгу:

                    FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
                    workbook.write(fileOutputStream);
                    fileOutputStream.close();
                    break;
                } else if (WorkbookOperations.getCellValue(cell).toString().isEmpty()) {
                    int j = 0;
                    for (int i = 30; i < numsOfFile_1.size() + 30; i++) {
                        Cell cellNums = row.getCell(i);                                           // Получаем ячейку
                        cellNums.setCellValue(numsOfFile_1.get(j));                                  // Добавляем значения из файла
                        j++;
                    }

                    // Записываем всё в книгу:

                    FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
                    workbook.write(fileOutputStream);
                    fileOutputStream.close();
                    break;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Строка добавлена в отчёт", "Успешно", JOptionPane.INFORMATION_MESSAGE);
    }
}

