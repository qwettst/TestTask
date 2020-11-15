package Service.Account;

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

public class CsvAccountStorage implements AccountStorage {
    private static final String CSV_NAME = "accountDB.csv";
    private static final String[] HEADERS = {"UserName", "Password"};

    @Override
    public List<String> getAll() {
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

    @Override
    public void add(String userName, String password) {
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

    @Override
    public void remove(String userName, String password) {
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

    @Override
    public boolean validate(String userName, String password) {
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
