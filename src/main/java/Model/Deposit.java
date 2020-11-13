package Model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Deposit {


    private int id;


    private int idClient;
    private double ammount;
    private double percent;
    private double pretermPercent;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;

    private static final char CSV_SEPARATOR = ',';
    private String datePattern = "MM/dd/yyyy";


    public Deposit(double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization, int idClient) {
        this.ammount = ammount;
        this.percent = percent;
        this.pretermPercent = pretermPercent;
        this.termDays = termDays;
        this.startDate = startDate;
        this.withPercentCapitalization = withPercentCapitalization;
        this.idClient = idClient;
    }

    public Deposit(int id, double ammount, double percent, double pretermPercent, int termDays, boolean withPercentCapitalization, int idClient) {
        this.id = id;
        this.ammount = ammount;
        this.percent = percent;
        this.pretermPercent = pretermPercent;
        this.termDays = termDays;
        this.withPercentCapitalization = withPercentCapitalization;
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }


    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getPretermPercent() {
        return pretermPercent;
    }

    public void setPretermPercent(double pretermPercent) {
        this.pretermPercent = pretermPercent;
    }

    public int getTermDays() {
        return termDays;
    }

    public void setTermDays(int termDays) {
        this.termDays = termDays;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDateFromCsv(String startDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        try {
            Date date = simpleDateFormat.parse(startDate);
            this.startDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public boolean isWithPercentCapitalization() {
        return withPercentCapitalization;
    }

    public void setWithPercentCapitalization(boolean withPercentCapitalization) {
        this.withPercentCapitalization = withPercentCapitalization;
    }

    public List<String> printToCsv() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String date = simpleDateFormat.format(startDate);
        String s = new StringBuilder().append(id).append(CSV_SEPARATOR)
                .append(ammount).append(CSV_SEPARATOR)
                .append(percent).append(CSV_SEPARATOR)
                .append(pretermPercent).append(CSV_SEPARATOR)
                .append(termDays).append(CSV_SEPARATOR)
                .append(date).append(CSV_SEPARATOR)
                .append(withPercentCapitalization).append(CSV_SEPARATOR)
                .append(idClient)
                .toString();
        return new ArrayList<>(Arrays.asList(s.split(",")));
    }
}
