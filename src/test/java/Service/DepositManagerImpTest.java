package Service;

import Model.Client;
import Model.Deposit;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DepositManagerImpTest {
    @Test
    public void testDeposit() {
        long time = System.currentTimeMillis();

        DepositMapper depositMapper=new DepositMapper();
        CsvHelperDeposit csvHelperDeposit = new CsvHelperDeposit(depositMapper);
        DepositManagerImp depositManagerImp = new DepositManagerImp(csvHelperDeposit);
        Date date = new GregorianCalendar(2018, Calendar.DECEMBER, 14).getTime();

        Deposit deposit = new Deposit(100000, 5, 5, 36, date, true, 1);

        String username = "da";
        String password = "135";
        CsvHelperAccount csvHelperAccount = new CsvHelperAccount();
        AccountManagerImp accountManagerImp = new AccountManagerImp(csvHelperAccount);
        accountManagerImp.addAccount(username,password);
        String token = accountManagerImp.authorize(username, password, new Date());

        Client client = new Client(2, "Ja", "Ne", "Ponyal");

        depositManagerImp.addDeposit(client, 100000, 20, 6, 90, date, true, token);


        depositManagerImp.getAllDeposits();
        List<Deposit> depositList = depositManagerImp.getClientDeposits(new Client(1, "dsad", "da", "dsada"), token);


        System.out.println(depositManagerImp.getEarnings(deposit, new Date(), token));
        long t1 = System.currentTimeMillis() - time;
        System.out.println(" read file " + (t1) + " ms");
    }

    @Test
    public void testAccount() {
        CsvHelperAccount csvHelperAccount = new CsvHelperAccount();
        AccountManagerImp accountManagerImp = new AccountManagerImp(csvHelperAccount);

        String username = "dada";
        String password = "dsadasf2";
        Date date = new Date();

        accountManagerImp.addAccount(username, password);
        accountManagerImp.getAllAccounts();
        accountManagerImp.removeAccount("dsadas", "135");
        String token = accountManagerImp.authorize(username, password, new Date());

    }

}