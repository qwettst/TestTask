package Service.Deposit;


import Model.Deposit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Класс для обработки данных модели Deposit
 */
public class DepositMapper {
    private static final char CSV_SEPARATOR = ',';
    private String datePattern = "MM/dd/yyyy";

    public List<String> printToCsv(Deposit deposit) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String date = simpleDateFormat.format(deposit.getStartDate());
        String s = new StringBuilder().append(deposit.getId()).append(CSV_SEPARATOR)
                .append(deposit.getAmmount()).append(CSV_SEPARATOR)
                .append(deposit.getPercent()).append(CSV_SEPARATOR)
                .append(deposit.getPretermPercent()).append(CSV_SEPARATOR)
                .append(deposit.getTermDays()).append(CSV_SEPARATOR)
                .append(date).append(CSV_SEPARATOR)
                .append(deposit.isWithPercentCapitalization()).append(CSV_SEPARATOR)
                .append(deposit.getIdClient())
                .toString();
        return Arrays.asList(s.split(","));
    }

    public Date setStartDateFromCsv(String startDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(startDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
