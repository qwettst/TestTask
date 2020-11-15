package Service;

import Exceptions.AuthException;
import Model.Client;
import Model.Deposit;
import Service.Account.AccountManagerImp;
import Service.Account.CsvAccountStorage;
import Service.Deposit.CsvDepositStorage;
import Service.Deposit.DepositManagerImp;
import Service.Deposit.DepositMapper;
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
        CsvDepositStorage csvDepositStorage = new CsvDepositStorage(depositMapper);
        DepositManagerImp depositManagerImp = new DepositManagerImp(csvDepositStorage);

        Date date = new GregorianCalendar(2018, Calendar.DECEMBER, 14).getTime();

        Deposit deposit = new Deposit(100000.0231, 5, 5, 36, date, true, 1);

        String username = "da";
        String password = "133";
        CsvAccountStorage csvAccountStorage = new CsvAccountStorage();
        AccountManagerImp accountManagerImp = new AccountManagerImp(csvAccountStorage);
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
        CsvAccountStorage csvAccountStorage = new CsvAccountStorage();
        AccountManagerImp accountManagerImp = new AccountManagerImp(csvAccountStorage);

        String username = "dada";
        String password = "dsadasf2";

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