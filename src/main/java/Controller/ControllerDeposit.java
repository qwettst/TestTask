package Controller;

import Model.Client;
import Model.Deposit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.time.DateUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class ControllerDeposit implements DepositManager {

    @Override
    public Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization) {

        Deposit deposit = new Deposit(ammount, percent, pretermPercent, termDays, startDate, withPercentCapitalization, client.getId());

        BufferedWriter writer = null;
        CSVPrinter csvFilePrinter = null;
        try {
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader("ID", "IDclient", "Ammount", "Percent", "PretermPercent", "TermDays", "StartDate", "withPercentCapitalization");
            writer = Files.newBufferedWriter(
                    Paths.get("./depositDB.csv"),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
            csvFilePrinter = new CSVPrinter(writer, csvFileFormat);
            csvFilePrinter.printRecord("1", client.getId(), deposit.getAmmount(), deposit.getPercent(), deposit.getPretermPercent(), deposit.getTermDays(), deposit.getStartDate(), deposit.isWithPercentCapitalization());
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Deposit> getClientDeposits(Client client) {
        return null;
    }

    @Override
    public List<Deposit> getAllDeposits() {
        return null;
    }

    @Override
    public double getEarnings(Deposit deposit, Date currentDate) {
        double res = calcDeposite(deposit, currentDate, false);
        return res;
    }

    @Override
    public double removeDeposit(Deposit deposit, Date closeDate) {
        double res = 0;
        Date depositDate = deposit.getStartDate();
        depositDate = DateUtils.addDays(depositDate, deposit.getTermDays());
        if (DateUtils.isSameDay(closeDate, depositDate)) {
            res = calcDeposite(deposit, closeDate, false);
        } else if (closeDate.before(depositDate)) {
            res = calcDeposite(deposit, closeDate, true);
        } else {
            res = calcDeposite(deposit, depositDate, false);
        }
        return res + deposit.getAmmount();
    }

    public double calcDeposite(Deposit deposit, Date currentDate, boolean termPayout) {
        double res = 0;
        double day_of_year = 365;
        double percent = 0;
        int days = daysBetween(deposit.getStartDate(), currentDate);

        Calendar calendar = DateUtils.toCalendar(currentDate);
        if (((GregorianCalendar) calendar).isLeapYear(calendar.get(Calendar.YEAR))) {
            day_of_year = 366;
        } else {
            day_of_year = 365;
        }
        if (termPayout) {
            percent = deposit.getPretermPercent();
        } else {
            percent = deposit.getPercent();
        }
        if (deposit.isWithPercentCapitalization()) {
            int n = days / 30;
            res = deposit.getAmmount() * (Math.pow(1 + percent * 30 / day_of_year / 100, n) - 1);
            if (days % 30 > 0) {
                res += ((res + deposit.getAmmount()) * percent * (days % 30) / day_of_year) / 100;
            }
        } else {
            res = (deposit.getAmmount() * percent * days / day_of_year) / 100;
        }
        BigDecimal bd = new BigDecimal(Double.toString(res));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
