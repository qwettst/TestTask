package Service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хранения и обработки записей аккаунтов пользователей
 */

public class CsvHelperAccount {
    private static final String CSV_NAME = "accountDB.csv";
    private static final String[] HEADERS = {"UserName", "Password"};

    /**
     * Методя получения всех записей из csv файла
     *
     * @return String лист записей имен аккаунтов
     */
    public List<String> getAllRecord() {
        List<String> stringList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("./" + CSV_NAME))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                stringList.add(record.get("UserName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList;
    }

    /**
     * Метод добавления записи об аккаунте в csv файл
     *
     * @param userName
     * @param password
     */
    public void addValue(String userName, String password) {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get("./" + CSV_NAME),
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
        ) {
            if (Files.size(Paths.get("./" + CSV_NAME)) == 0) {
                csvFileFormat = csvFileFormat.withHeader(HEADERS);
            }
            CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);
            csvFilePrinter.printRecord(userName, password);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод удаления записи по userName и password аккаунта
     *
     * @param userName
     * @param password
     */
    public void removeValue(String userName, String password) {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(HEADERS);
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get("./" + "withoutElemAcc.csv"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
             CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);
             BufferedReader reader = Files.newBufferedReader(Paths.get("./" + CSV_NAME))
        ) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                if (!(record.get("UserName").equals(userName) & record.get("Password").equals(password)))
                    csvFilePrinter.printRecord(record);
            }
            writer.flush();
            Files.move(Paths.get("./" + "withoutElemAcc.csv"), Paths.get("./" + CSV_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод валидации пользователя в системе для последующей авторизации
     *
     * @param userName
     * @param password
     * @return true - данные пользоввателя действительны
     * false - данные пользоввателя не действительны
     */
    public boolean validateAccount(String userName, String password) {
        boolean res = false;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("./" + CSV_NAME))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                if (record.get("UserName").equals(userName) & record.get("Password").equals(password)) {
                    res = true;
                    break;
                }
            }
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

}
