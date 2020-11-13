package Controller;

import Model.Deposit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CsvHelper {

    private static final String CSV_NAME = "depositDB.csv";
    private static final String[] HEADERS = {"ID", "Ammount", "Percent", "PretermPercent",
            "TermDays", "StartDate", "withPercentCapitalization", "IDclient"};

    public List<Deposit> getAllRecord() {
        List<Deposit> listDeposits = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("./" + CSV_NAME))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                int id = Integer.parseInt(record.get("ID"));
                double ammount = Double.parseDouble(record.get("Ammount"));
                double percent = Double.parseDouble(record.get("Percent"));
                double pretermPercent = Double.parseDouble(record.get("PretermPercent"));
                int termDays = Integer.parseInt(record.get("TermDays"));
                String startDate = record.get("StartDate");
                boolean withPercentCapitalization = Boolean.parseBoolean(record.get("withPercentCapitalization"));
                int idClient = Integer.parseInt(record.get("IDclient"));
                Deposit deposit = new Deposit(id, ammount, percent, pretermPercent, termDays, withPercentCapitalization, idClient);
                deposit.setStartDateFromCsv(startDate);
                listDeposits.add(deposit);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDeposits;
    }

    public void addValue(Deposit deposit) {
        int incr = getIncrement();
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        if (incr == 0) {
            csvFileFormat = csvFileFormat.withHeader(HEADERS);
        }
        incr++;
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get("./" + CSV_NAME),
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
             CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat)) {
            deposit.setId(incr);
            csvFilePrinter.printRecord(deposit.printToCsv());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Deposit> getValueByClient(int idClient) {
        List<Deposit> listAllDeposits = getAllRecord();
        List<Deposit> listDeposits = new ArrayList<>();
        for (Deposit deposit : listAllDeposits) {
            if (deposit.getIdClient() == idClient) {
                listDeposits.add(deposit);
            }
        }
        return listDeposits;
    }

    public boolean removeValue(int idDeposit) {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(HEADERS);
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get("./" + "withoutElem.csv"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
             CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);
             BufferedReader reader = Files.newBufferedReader(Paths.get("./" + CSV_NAME))
        ) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                if (!(Integer.parseInt(record.get("ID")) == idDeposit))
                    csvFilePrinter.printRecord(record);
            }
            writer.flush();
            Files.move(Paths.get("./" + "withoutElem.csv"), Paths.get("./" + CSV_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private int getIncrement() {
        int incr = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("./" + CSV_NAME))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                int id = Integer.parseInt(record.get("ID"));
                incr = id;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return incr;
    }
}
