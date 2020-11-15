import Model.Deposit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class DepositTest {
    private int id;
    private int idClient;
    private double ammount;
    private double percent;
    private double pretermPercent;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;

    @Before
    public void setData() {
        id = 1;
        idClient = 2;
        ammount = 10000.12;
        percent = 3.4;
        pretermPercent = 5;
        termDays = 34;
        startDate = new Date();
        withPercentCapitalization = true;
    }

    @Test
    public void ModelDepositTestConstructor() {

        Deposit deposit = new Deposit(id, ammount, percent, pretermPercent, termDays, startDate, withPercentCapitalization, idClient);

        Assert.assertNotNull(deposit);
        Assert.assertEquals(id, deposit.getId());
        Assert.assertEquals(idClient, deposit.getIdClient());
        Assert.assertEquals(ammount, deposit.getAmmount(), 0.1);
        Assert.assertEquals(percent, deposit.getPercent(), 0.1);
        Assert.assertEquals(pretermPercent, deposit.getPretermPercent(), 0.1);
        Assert.assertEquals(termDays, deposit.getTermDays());
        Assert.assertEquals(startDate, deposit.getStartDate());
        Assert.assertEquals(true, deposit.isWithPercentCapitalization());

    }

    @Test
    public void ModelDepositTestWithoutId() {
        Deposit deposit = new Deposit(ammount, percent, pretermPercent, termDays, startDate, withPercentCapitalization, idClient);

        Assert.assertNotNull(deposit);
        Assert.assertEquals(0, deposit.getId());
        Assert.assertEquals(idClient, deposit.getIdClient());
        Assert.assertEquals(ammount, deposit.getAmmount(), 0.1);
        Assert.assertEquals(percent, deposit.getPercent(), 0.1);
        Assert.assertEquals(pretermPercent, deposit.getPretermPercent(), 0.1);
        Assert.assertEquals(termDays, deposit.getTermDays());
        Assert.assertEquals(startDate, deposit.getStartDate());
        Assert.assertEquals(true, deposit.isWithPercentCapitalization());
    }

    @Test
    public void ModelDepositTestGetterSetter() {
        Deposit deposit = new Deposit(ammount, percent, pretermPercent, termDays, startDate, withPercentCapitalization, idClient);
        Assert.assertEquals(0, deposit.getId());
        deposit.setId(id);
        Assert.assertEquals(id, deposit.getId());
    }
}
