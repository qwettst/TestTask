package Service;

import Exceptions.NumberOfTermDaysException;
import Model.Client;
import Model.Deposit;

import java.util.Date;
import java.util.List;

/*
 Интерфейс для управления вкладами
*/
public interface DepositManager {
    /*
    Метод добавляет в систему информацию о новом вкладе
    * @param pretermPercent процент при досрочном изъятии вклада
    */
    Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent,
                       int termDays, Date startDate,
                       boolean withPercentCapitalization, String token) throws NumberOfTermDaysException;

    /*
    Метод возвращает список вкладов клиента
    */
    List<Deposit> getClientDeposits(Client client, String token);

    /*
    Метод возвращает список всех вкладов принятых банком
    */
    List<Deposit> getAllDeposits();

    /*
    Метод возвращает текущий доход по вкладу
    */
    double getEarnings(Deposit deposit, Date currentDate, String token);

    /*
    Метод удаляет запись о вкладе и возвращает сумму к выплате в кассе. Если вклад
    закрывается досрочно, то сумма к выплате рассчитывается исходя из процента при досрочном
    изъятии.
    */
    double removeDeposit(Deposit deposit, Date closeDate, String token);
}