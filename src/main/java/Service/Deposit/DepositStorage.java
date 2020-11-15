package Service.Deposit;

import Model.Deposit;

import java.util.List;

public interface DepositStorage {

    /**
     * Методя получения всех записей
     *
     * @return Deposit лист записей имен аккаунтов
     */
    List<Deposit> getAll();

    /**
     * Метод добавления записи о депозите
     *
     * @param deposit
     * @return Deposit
     */
    Deposit add(Deposit deposit);

    /**
     * Метод фильтрации записей по id клиента
     *
     * @param idClient - id клиента
     * @return
     */
    List<Deposit> getDepositsByClient(int idClient);

    /**
     * Метод удаления записи по id депозита
     *
     * @param idDeposit - id депозита
     */
    void remove(int idDeposit);


}
