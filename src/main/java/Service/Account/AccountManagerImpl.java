package Service.Account;

import Exceptions.AuthException;


import java.util.Date;
import java.util.List;

public class AccountManagerImpl implements AccountManager {
    private AccountStorage accountStorage;

    public AccountManagerImpl(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    @Override
    public void addAccount(String userName, String password) {
        accountStorage.add(userName, password);
    }

    @Override
    public void removeAccount(String userName, String password) {
        accountStorage.remove(userName, password);
    }

    @Override
    public List<String> getAllAccounts() {
        List<String> stringList = accountStorage.getAll();
        return stringList;
    }

    @Override
    public String authorize(String userName, String password, Date currentTime) throws AuthException {
        if (accountStorage.validate(userName, password)) {
            return AuthToken.getInstance(userName, password, currentTime);
        } else
            throw new AuthException("User authentication error");
    }
}
