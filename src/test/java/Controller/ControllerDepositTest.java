package Controller;

import Model.Client;
import Model.Deposit;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class ControllerDepositTest {
    @Test
    public void getEarnings(){
        Date date = new GregorianCalendar(2020, Calendar.AUGUST, 13).getTime();
        Deposit deposit=new Deposit(100000,20,6,90,date,true,1);
        ControllerDeposit controllerDeposit=new ControllerDeposit();
        Date currentdate = new GregorianCalendar(2020, Calendar.OCTOBER, 13).getTime();
        System.out.println(controllerDeposit.removeDeposit(deposit,currentdate));
        Client client=new Client(2,"Ja","Ne","Ponyal");
        controllerDeposit.addDeposit(client,100000,20,6,90,date,true);
    }

}