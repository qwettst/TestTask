
import Controller.DepositManager;
import Model.Client;
import Model.Deposit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class sdad implements DepositManager {

    @Override
    public Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization) {
        return null;
    }

    @Override
    public List<Deposit> getClientDeposits(Client client) {
        return null;
    }

    @Override
    public List<Deposit> getAllDeposits() {
        return null;
    }

    @Override
    public double getEarnings(Deposit deposit, Date currentDate) {
        double res = 0;
        double day_of_year = 365;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        int days = daysBetween(deposit.getStartDate(), currentDate);
        if (((GregorianCalendar) calendar).isLeapYear(calendar.get(Calendar.YEAR))) {
            day_of_year = 366;
        } else {
            day_of_year = 365;
        }
        if (deposit.isWithPercentCapitalization()) {
            int n = days / 30;
            res = deposit.getAmmount() * (Math.pow(1 + deposit.getPercent() * 30 / day_of_year / 100, n) - 1);
            if (days % 30 > 0) {
                res += ((res + deposit.getAmmount()) * deposit.getPercent() * (days % 30) / day_of_year) / 100;
            }
        } else {
            res = (deposit.getAmmount() * deposit.getPercent() * days / day_of_year) / 100;
        }
        BigDecimal bd = new BigDecimal(Double.toString(res));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public double removeDeposit(Deposit deposit, Date closeDate) {
        double res=0;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(deposit.getStartDate());
        calendar.add(Calendar.DATE, deposit.getTermDays());
        Date  date=calendar.getTime();
        if (closeDate.equals(date)) {
            res=getEarnings(deposit,closeDate);
        }

        return res;
    }


    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
