package Service;

import Exceptions.AuthException;
import Model.Client;
import Model.Deposit;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

public class DepositManagerImpTest {
    private static Logger log = Logger.getLogger(DepositManagerImpTest.class.getName());

    @Test
    public void testDeposit() {

        DepositMapper depositMapper = new DepositMapper();
        CsvHelperDeposit csvHelperDeposit = new CsvHelperDeposit(depositMapper);
        DepositManagerImp depositManagerImp = new DepositManagerImp(csvHelperDeposit);

        Date date = new GregorianCalendar(2018, Calendar.DECEMBER, 14).getTime();

        Deposit deposit = new Deposit(100000.0231, 5, 5, 36, date, true, 1);

        String username = "da";
        String password = "133";
        CsvHelperAccount csvHelperAccount = new CsvHelperAccount();
        AccountManagerImp accountManagerImp = new AccountManagerImp(csvHelperAccount);
        accountManagerImp.addAccount(username, password);
        String token = null;

        Client client = new Client(2, "Ja", "Ne", "Ponyal");

        try {
            token = accountManagerImp.authorize(username, password, new Date());
            depositManagerImp.addDeposit(client, 100000, 20, 6, 35, date, true, token);
        } catch (Exception e) {
            log.info("Error: " + e.getMessage());
        }

        depositManagerImp.getAllDeposits();
        List<Deposit> depositList = depositManagerImp.getClientDeposits(new Client(1, "dsad", "da", "dsada"), token);
        System.out.println(depositManagerImp.getEarnings(deposit, new Date(), token));
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
        try {
            String token = accountManagerImp.authorize(username, password, new Date());
        } catch (AuthException e) {
            log.info("Error: " + e.getMessage());
        }

    }

}