package Service.Account;

import java.util.List;



public interface AccountStorage {

    /**
     * Методя получения всех записей
     *
     * @return String лист записей имен аккаунтов
     */
    List<String> getAll();

    /**
     * Метод добавления записи об аккаунте
     *
     * @param userName
     * @param password
     */
    void add(String userName, String password);

    /**
     * Метод удаления записи по userName и password аккаунта
     *
     * @param userName
     * @param password
     */
    void remove(String userName, String password);

    /**
     * Метод валидации пользователя в системе для последующей авторизации
     *
     * @param userName
     * @param password
     * @return true - данные пользоввателя действительны
     * false - данные пользоввателя не действительны
     */
    boolean validate(String userName, String password);

}
