package Model;

import java.util.Date;

public class Deposit {
    private int id;
    private int idClient;
    private double ammount;
    private double percent;
    private double pretermPercent;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;

    public Deposit(double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization,int idClient) {
        this.ammount = ammount;
        this.percent = percent;
        this.pretermPercent = pretermPercent;
        this.termDays = termDays;
        this.startDate = startDate;
        this.withPercentCapitalization = withPercentCapitalization;
        this.idClient=idClient;
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

    public boolean isWithPercentCapitalization() {
        return withPercentCapitalization;
    }

    public void setWithPercentCapitalization(boolean withPercentCapitalization) {
        this.withPercentCapitalization = withPercentCapitalization;
    }
}
