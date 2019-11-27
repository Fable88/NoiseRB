package Backend;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class FileOperations {

    private static String excelFilePath;             // Путь к Excel файлу
    private static String txtFile_1_1_Path;          // Путь к .txt файлу 1_1
    private static String txtFile_1_3_Path;          // Путь к .txt файлу 1_3
    private static String version;
    private static String PJI;
    public static String getExcelFilePath() {
        return excelFilePath;
    }
    public static String getTxtFile_1_1_Path() {
        return txtFile_1_1_Path;
    }
    public static String getTxtFile_1_3_Path() {
        return txtFile_1_3_Path;
    }
    public static String getVersion() {
        return version;
    }
    public static String getPJI() {
        return PJI;
    }

    public static void setExcelFilePath(String excelFilePath) {
        FileOperations.excelFilePath = excelFilePath;
    }
    public static void setTxtFile_1_1_Path(String txtFile_1_1_Path) {
        FileOperations.txtFile_1_1_Path = txtFile_1_1_Path;
    }
    public static void setTxtFile_1_3_Path(String txtFile_1_3_Path) {
        FileOperations.txtFile_1_3_Path = txtFile_1_3_Path;
    }
    public static void setVersion(String version) {
        FileOperations.version = version;
    }
    public static void setPJI(String PJI) {
        FileOperations.PJI = PJI;
    }

    // Чтение книги по указанному в паратемрах пути

    static Workbook readWorkbook(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        fileInputStream.close();
        return workbook;
    }

    // Построчное чтение файла по указанному в параметрах пути. Возвращает String

    private static String readFile(String path) throws IOException {

        File file = new File(path);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        return stringBuilder.toString();
    }

    // Возвращает список Double чисел из текста файла

    static ArrayList<Double> cutToList(String path) throws IOException {

        ArrayList<Double> nums = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(readFile(path));                // Читаем файл в StringBuilder

        int indexOfReal = stringBuilder.lastIndexOf("Real");                        // Индекс начала слова "Real". После него начинаются числа
        String text = stringBuilder.delete(0, indexOfReal + 5).toString().replace(',', '.'); // Оставляем только числа, меняем "," на "."

        int indexOfSpace = 0;                                                           // Индекс пробела ('\t')
        indexOfSpace = text.indexOf('\t', indexOfSpace);                            // Индекс следующего пробела
        int indexOfSeparator = text.indexOf('\n', indexOfSpace);                    // Индекс разделителя строк ('\n'), ищется начиная с пробела

        int qtyOfStringNums;                                                            //Количество строк с числами
        if (path.equals(txtFile_1_1_Path)) {
            qtyOfStringNums = 8;                                                        // В файле 1_1 их 8
        } else {
            qtyOfStringNums = 22;                                                       // В файле 1_3 их 22
        }
        for (int i = 0; i < qtyOfStringNums; i++) {                                     // Перебор всех строк с числами
            String num = text.substring(indexOfSpace + 2, indexOfSeparator);            // Берём число от пробела + 2 (начало числа без символа плюс) до разделителя
            nums.add(Double.parseDouble(num));                                          // Делаем Double из числа в String и кладём в список nums
            indexOfSpace = text.indexOf('\t', indexOfSeparator);                    // Меняем переменные, чтобы выбрать следующее число
            indexOfSeparator = text.indexOf('\n', indexOfSpace);                    // Цикл повторяется, пока indexOfSeparator не станет -1 (дальше сепараторов нет)
            if (indexOfSeparator == -1) {                                               // Если сепараторы закончились, то...
                indexOfSpace = text.lastIndexOf('\t');                              // ...ищем последний пробел...
                num = text.substring(indexOfSpace + 2);                                 // ...берём число, начиная от пробела + 2 (начало числа без символа плюс)...
                nums.add(Double.parseDouble(num));                                      // ...делаем Double из числа в String и кладём в список nums.
                break;                                                                  // Всё!
            }
        }
        if (path.equals(txtFile_1_1_Path)) {                                            // Для отчёта нужно только 3 последних значения из файла 1_1, остальное убираем
            nums.subList(0, 5).clear();
        }
        return nums;                                                                    // Возвращаем список чисел в виде Double
    }
}