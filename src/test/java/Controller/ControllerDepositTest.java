package Controller;

import Model.Client;
import Model.Deposit;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ControllerDepositTest {
    @Test
    public void getEarnings() throws IOException, ParseException {
        long time = System.currentTimeMillis();
        CsvHelper csvHelper = new CsvHelper();
        ControllerDeposit controllerDeposit = new ControllerDeposit(csvHelper);
        Date date = new GregorianCalendar(2018, Calendar.DECEMBER, 14).getTime();

        Deposit deposit = new Deposit(100000, 5, 5, 36, date, false, 1);
        Date currentdate = new GregorianCalendar(2020, Calendar.NOVEMBER, 13).getTime();


        Client client = new Client(2, "Ja", "Ne", "Ponyal");

        controllerDeposit.addDeposit(client, 100000, 20, 6, 90, date, true);

        long t1 = System.currentTimeMillis() - time;
        System.out.println(" read file " + (t1) + " ms");

        controllerDeposit.getAllDeposits();
        List<Deposit> depositList = controllerDeposit.getClientDeposits(new Client(1, "dsad", "da", "dsada"));


        System.out.println(depositList.size());
        System.out.println(controllerDeposit.removeDeposit(deposit, currentdate));
    }

}