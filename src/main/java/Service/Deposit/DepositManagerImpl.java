package Service.Deposit;

import Exceptions.NumberOfTermDaysException;
import Model.Client;
import Model.Deposit;
import Service.Account.AuthToken;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class DepositManagerImpl implements DepositManager {

    private DepositStorage depositStorage;

    /**
     * @param depositStorage instance сервиса для работы с csv
     */
    public DepositManagerImpl(DepositStorage depositStorage) {
        this.depositStorage = depositStorage;
    }

    @Override
    public Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent,
                              int termDays, Date startDate, boolean withPercentCapitalization, String token) throws NumberOfTermDaysException {
        if (AuthToken.validateToken(token)) {
            if (termDays < 30)
                throw new NumberOfTermDaysException("The number of days should not be less than 30");
            Deposit deposit;
            deposit = depositStorage.add(new Deposit(ammount, percent, pretermPercent, termDays, startDate, withPercentCapitalization, client.getId()));
            return deposit;
        }
        return null;
    }

    @Override
    public List<Deposit> getClientDeposits(Client client, String token) {
        if (AuthToken.validateToken(token)) {
            List<Deposit> depositList = depositStorage.getDepositsByClient(client.getId());
            return depositList;
        }
        return null;
    }

    @Override
    public List<Deposit> getAllDeposits() {
        List<Deposit> depositList = depositStorage.getAll();
        return depositList;
    }

    @Override
    public double getEarnings(Deposit deposit, Date currentDate, String token) {
        if (AuthToken.validateToken(token)) {
            double res = 0;
            Date depositDate = deposit.getStartDate();
            depositDate = DateUtils.addDays(depositDate, deposit.getTermDays());
            if (DateUtils.isSameDay(currentDate, depositDate)) {
                res = calcDeposit(deposit, currentDate, false);
            } else if (currentDate.before(depositDate)) {
                res = calcDeposit(deposit, currentDate, false);
            } else {
                res = calcDeposit(deposit, depositDate, false);
            }
            return res;
        }
        return 0;
    }

    @Override
    public double removeDeposit(Deposit deposit, Date closeDate, String token) {
        if (AuthToken.validateToken(token)) {
            double res = 0;
            Date depositDate = deposit.getStartDate();
            depositDate = DateUtils.addDays(depositDate, deposit.getTermDays());
            if (DateUtils.isSameDay(closeDate, depositDate)) {
                res = calcDeposit(deposit, closeDate, false);
            } else if (closeDate.before(depositDate)) {
                res = calcDeposit(deposit, closeDate, true);
            } else {
                res = calcDeposit(deposit, depositDate, false);
            }
            BigDecimal bd = new BigDecimal(Double.toString(res + deposit.getAmmount()));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            depositStorage.remove(deposit.getId());
            return bd.doubleValue();
        }
        return 0;
    }

    /**
     * @param deposit
     * @param currentDate
     * @param termPayout, true - расчет для досрочных выплат,
     *                    false - расчет при выплате в срок
     */
    private double calcDeposit(Deposit deposit, Date currentDate, boolean termPayout) {
        double res = 0;
        int dayOfYear = 365;
        double percent = 0;
        Date calendarDepositDate = deposit.getStartDate();

        GregorianCalendar calendarStartDate = (GregorianCalendar) DateUtils.toCalendar(calendarDepositDate);
        GregorianCalendar calendarCurrentDate = (GregorianCalendar) DateUtils.toCalendar(currentDate);

        if (calendarCurrentDate.isLeapYear(calendarCurrentDate.get(Calendar.YEAR))) {
            dayOfYear = 366;
        } else {
            dayOfYear = 365;
        }

        long i = ChronoUnit.DAYS.between(calendarStartDate.toInstant(), calendarCurrentDate.toInstant()) / dayOfYear;

        if (termPayout) {
            percent = deposit.getPretermPercent();
        } else {
            percent = deposit.getPercent();
        }

        if (deposit.isWithPercentCapitalization()) {
            if (i != 0) {
                double effPercent = Math.pow((1 + percent / 100 / 12), 12) - 1;
                res += deposit.getAmmount() * Math.pow((1 + effPercent), i);
            } else
                res = deposit.getAmmount();
            long days = ChronoUnit.DAYS.between(deposit.getStartDate().toInstant(), currentDate.toInstant()) - i * 365;
            long n = (days / 30);
            res += res * (Math.pow(1 + percent * 30 / dayOfYear / 100, n) - 1);
            if (days % 30 > 0) {
                res += (res * percent * (days % 30) / dayOfYear) / 100;
            }
            res -= deposit.getAmmount();
        } else {
            long days = ChronoUnit.DAYS.between(deposit.getStartDate().toInstant(), currentDate.toInstant());
            res += (deposit.getAmmount() * percent * days / 365) / 100;
        }
        BigDecimal bd = new BigDecimal(Double.toString(res));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
