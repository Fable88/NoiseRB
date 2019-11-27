package SwingGUI;

import Backend.FileOperations;
import Backend.Main;
import Backend.PathOperations;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;


public class Form extends JFrame {
    private JPanel rootPanel;
    private JButton openExcelFileButton;
    private JButton openFile_1;
    private JButton openFile_3;
    private JButton createReportButton;
    private JLabel excelFileName;
    private JLabel txt_1_FileName;
    private JLabel txt_3_FileName;
    private JTextField versionText;
    private JTextField PJIText;
    private boolean PJIisCorrect;
    private boolean versionIsCorrect;
    private boolean excelFileIsSelected;
    private boolean txt_1_FileIsSelected;
    private boolean txt_3_FileIsSelected;
    private FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter(".xlsx", "xlsx");
    private FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(".txt", "txt", "text");




    public Form() {                                                             // Конструктор окна

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image.png"));
        setIconImage(imageIcon.getImage());

        add(rootPanel);
        setTitle("Report Builder");
        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PJIText.addFocusListener(new FocusAdapter() {                           // Выделение текста при фокусе на строке с PJI
            @Override
            public void focusGained(FocusEvent e) {
                PJIText.selectAll();
            }
        });
        PJIText.addFocusListener(new FocusAdapter() {                           // Уход со строки PJI
            @Override
            public void focusLost(FocusEvent e) {                                    // Если цифр не 7, окрасить красным
                if (PJIText.getText().length() != 7) {                                             // boolean false
                    PJIText.setForeground(Color.RED);
                } else {
                    PJIText.setForeground(new Color(0, 128, 0));
                    PJIisCorrect = true;
                }
            }
        });

        versionText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {                             // Выделение текста при фокусе на строке с Version
                versionText.selectAll();
            }
        });
        versionText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (versionText.getText().equals("Введите версию а/м")) {
                    versionText.setForeground(Color.RED);
                } else {
                    versionText.setForeground(new Color(0, 128, 0));
                    versionText.setText(versionText.getText().toUpperCase());
                    versionIsCorrect = true;
                }
            }
        });


        openExcelFileButton.addActionListener(new ActionListener() {            // Нажатие на кнопку Excel - открыть Excel файл
            @Override
            public void actionPerformed(ActionEvent e) {

                FileOperations.setExcelFilePath(PathOperations.changeSlash((pathOpener(xlsxFilter)))); // Меняем слеши и сохраняем путь
                final String EXCEL_PATH = FileOperations.getExcelFilePath();
                if (EXCEL_PATH.contains("Audit FRF Allegee") && PathOperations.isExtension(EXCEL_PATH, ".xlsx")) {
                    excelFileName.setForeground(new Color(0, 128, 0));
                    excelFileName.setText("   OK");
                    excelFileIsSelected = true;
                } else {
                    excelFileName.setForeground(new Color(255, 153, 0));
                    excelFileName.setText("   Ошибка! Выберите снова");
                    excelFileIsSelected = false;
                    JOptionPane.showMessageDialog(null, "Выберите .xlsx файл с отчётом", "Вы выбрали не тот файл", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        openFile_1.addActionListener(new ActionListener() {                     // Нажатие на кнопку File 1 - открыть File 1_1 файл
            @Override
            public void actionPerformed(ActionEvent e) {

                FileOperations.setTxtFile_1_1_Path(PathOperations.changeSlash((pathOpener(txtFilter))));
                final String TXT_1_PATH = FileOperations.getTxtFile_1_1_Path();
                if (FileOperations.getTxtFile_1_1_Path().contains("_1_1") && PathOperations.isExtension(TXT_1_PATH, ".txt", ".text")) {
                    txt_1_FileName.setForeground(new Color(0, 128, 0));
                    txt_1_FileName.setText("   OK");
                    txt_1_FileIsSelected = true;
                } else {
                    txt_1_FileName.setForeground(new Color(255, 153, 0));
                    txt_1_FileName.setText("   Ошибка! Выберите снова");
                    txt_1_FileIsSelected = false;
                    JOptionPane.showMessageDialog(null, "Выберите .txt файл \"*_1_1\"", "Вы выбрали не тот файл", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        openFile_3.addActionListener(new ActionListener() {                     // Нажатие на кнопку File 3 - открыть File 1_3 файл
            @Override
            public void actionPerformed(ActionEvent e) {

                FileOperations.setTxtFile_1_3_Path(PathOperations.changeSlash((pathOpener(txtFilter))));
                final String TXT_3_PATH = FileOperations.getTxtFile_1_3_Path();
                if (FileOperations.getTxtFile_1_3_Path().contains("_1_3") && PathOperations.isExtension(TXT_3_PATH, ".txt", ".text")) {
                    txt_3_FileName.setForeground(new Color(0, 128, 0));
                    txt_3_FileName.setText("   OK");
                    txt_3_FileIsSelected = true;
                } else {
                    txt_3_FileName.setForeground(new Color(255, 153, 0));
                    txt_3_FileName.setText("   Ошибка! Выберите снова");
                    txt_3_FileIsSelected = false;
                    JOptionPane.showMessageDialog(null, "Выберите .txt файл \"*_1_3\"", "Вы выбрали не тот файл", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createReportButton.addActionListener(new ActionListener() {             // Нажатие на кнопку "Создать отчёт" - запускает создание отчёта
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOperations.setPJI(PJIText.getText());
                FileOperations.setVersion(versionText.getText());
                try {
                    startToReport();
                } catch (IOException | InterruptedException ex) {
                    System.out.println("ОШИБКА ЗАПУСКА");
                }
            }
        });
    }


    private String pathOpener(FileNameExtensionFilter fileNameExtensionFilter) {

        JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        jFileChooser.setFileFilter(fileNameExtensionFilter);
        File file = new File("");
        int ret = jFileChooser.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser.getSelectedFile();
        }
        System.out.println(file.getPath());
        return file.getPath();
    }

    private void startToReport() throws IOException, InterruptedException {                                                  // *** СТАРТ ПРОГРАММЫ РАСЧЁТА ***

        if (!PJIisCorrect && !versionIsCorrect) {
            JOptionPane.showMessageDialog(null, "Проверьте PJI и версию автомобиля", "PJI или версия неверны", JOptionPane.ERROR_MESSAGE);
        } else if (!excelFileIsSelected || !txt_1_FileIsSelected || !txt_3_FileIsSelected) {
            JOptionPane.showMessageDialog(null, "Проверьте выбранные файлы", "Выбраны неверные файлы", JOptionPane.ERROR_MESSAGE);
        } else {
            Main.main();
        }
    }
}
