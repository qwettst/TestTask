package Service;

import Exceptions.AuthException;
import Model.Client;
import Model.Deposit;
import Service.Account.AccountManagerImpl;
import Service.Account.CsvAccountStorage;
import Service.Deposit.CsvDepositStorage;
import Service.Deposit.DepositManagerImpl;
import Service.Deposit.DepositMapper;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

public class DepositManagerImplTest {
    private static Logger log = Logger.getLogger(DepositManagerImplTest.class.getName());

    @Test
    public void testDeposit() {

        DepositMapper depositMapper = new DepositMapper();
        CsvDepositStorage csvDepositStorage = new CsvDepositStorage(depositMapper);
        DepositManagerImpl depositManagerImpl = new DepositManagerImpl(csvDepositStorage);

        Date date = new GregorianCalendar(2018, Calendar.DECEMBER, 14).getTime();

        Deposit deposit = new Deposit(100000.0231, 5, 5, 36, date, true, 1);

        String username = "da";
        String password = "133";
        CsvAccountStorage csvAccountStorage = new CsvAccountStorage();
        AccountManagerImpl accountManagerImpl = new AccountManagerImpl(csvAccountStorage);
        accountManagerImpl.addAccount(username, password);
        String token = null;

        Client client = new Client(2, "Ja", "Ne", "Ponyal");

        try {
            token = accountManagerImpl.authorize(username, password, new Date());
            depositManagerImpl.addDeposit(client, 100000, 20, 6, 35, date, true, token);
        } catch (Exception e) {
            log.info("Error: " + e.getMessage());
        }

        depositManagerImpl.getAllDeposits();
        List<Deposit> depositList = depositManagerImpl.getClientDeposits(new Client(1, "dsad", "da", "dsada"), token);
        System.out.println(depositManagerImpl.getEarnings(deposit, new Date(), token));
    }

    @Test
    public void testAccount() {
        CsvAccountStorage csvAccountStorage = new CsvAccountStorage();
        AccountManagerImpl accountManagerImpl = new AccountManagerImpl(csvAccountStorage);

        String username = "dada";
        String password = "dsadasf2";

        accountManagerImpl.addAccount(username, password);
        accountManagerImpl.getAllAccounts();
        accountManagerImpl.removeAccount("dsadas", "135");
        try {
            String token = accountManagerImpl.authorize(username, password, new Date());
        } catch (AuthException e) {
            log.info("Error: " + e.getMessage());
        }

    }

}