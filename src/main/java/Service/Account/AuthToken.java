package Service.Account;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс для хранения и валидации токена
 * для сессии авторизированного пользователя
 */
public class AuthToken {

    private static AuthToken instance;

    private static String token;


    private AuthToken(String userName, String password, Date currentTime) {
        int pass = 31 * password.length();
        token = userName + pass + currentTime.getTime() / 1000;

    }


    public static synchronized String getInstance(String userName, String password, Date currentTime) {
        if (instance == null) {
            instance = new AuthToken(userName, password, currentTime);
            TimerTask task = new TimerTask() {
                public void run() {
                    instance = null;
                    token = null;
                }
            };
            Timer timer = new Timer("Timer");
            timer.schedule(task, 60000 * 30);
        }
        return token;
    }

    /**
     * @param auth_token токен авторизированного пользователя
     * @return true - токены совпадают, сессия действительна
     * false - токены не совпадают, сессия не действительна
     */
    public static boolean validateToken(String auth_token) {
        if (!(token == null))
            return token.equals(auth_token);
        else
            return false;
    }
}
