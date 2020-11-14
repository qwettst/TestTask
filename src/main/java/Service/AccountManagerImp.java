package Service;

import Exceptions.AuthException;

import java.util.Date;
import java.util.List;

public class AccountManagerImp implements AccountManager {
    private CsvHelperAccount csvHelperAccount;

    public AccountManagerImp(CsvHelperAccount csvHelperAccount) {
        this.csvHelperAccount = csvHelperAccount;
    }

    @Override
    public void addAccount(String userName, String password) {
        csvHelperAccount.addValue(userName, password);
    }

    @Override
    public void removeAccount(String userName, String password) {
        csvHelperAccount.removeValue(userName, password);
    }

    @Override
    public List<String> getAllAccounts() {
        List<String> stringList = csvHelperAccount.getAllRecord();
        return stringList;
    }

    @Override
    public String authorize(String userName, String password, Date currentTime) throws AuthException {
        if (csvHelperAccount.validateAccount(userName, password)) {
            return AuthToken.getInstance(userName, password, currentTime);
        } else
            throw new AuthException("User authentication error");
    }
}
