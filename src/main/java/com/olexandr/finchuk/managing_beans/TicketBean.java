package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.Flight;
import com.olexandr.finchuk.entities.Ticket;
import com.olexandr.finchuk.entities.User;
import com.olexandr.finchuk.jpa_dao.JPADAOFlight;
import com.olexandr.finchuk.jpa_dao.JPADAOTicket;
import com.olexandr.finchuk.jpa_dao.JPADAOUser;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.jms.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Olexandr Finchuk on 19.11.2016.
 */
@ManagedBean
@SessionScoped
public class TicketBean {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationBean.class);
    Collection<Flight> currFlights = new ArrayList<Flight>();
    String fromTown;
    String toTown;
    Date departure_date;
    Flight currFlight;
    HashSet<Ticket> choosenTickets = new HashSet<>();

    @EJB
    JPADAOFlight jpadaoFlight;
    @EJB
    JPADAOTicket jpadaoTicket;
    @EJB
    JPADAOUser jpadaoUser;

    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:jboss/exported/jms/topic/test")
    private Topic destination;

    public Collection<Flight> getCurrFlights() {
        return currFlights;
    }

    public void swapCurrTicket(Ticket t){
       if(isChosen(t)){

                choosenTickets.remove(t);
        }else{
            choosenTickets.add(t);
        }

    }

    public boolean isChosen(Ticket t){
        return choosenTickets.contains(t);
    }


    public String updateCurrFlights() {


        currFlights = jpadaoFlight.getObjectsByParams(fromTown.toLowerCase(),toTown.toLowerCase(),departure_date);
        return null;
    }
    public ArrayList<Ticket> getTicketsLuxFirst(){
        ArrayList<Ticket> tickets= jpadaoTicket.getObjectsByCondition("1<=t.placeNumber AND t.placeNumber<=2 AND t.flight.flightId="+currFlight.getFlightId());
        return tickets;
    }
    public ArrayList<Ticket> getTicketsLuxSecond(){
        ArrayList<Ticket> tickets= jpadaoTicket.getObjectsByCondition("3<=t.placeNumber AND t.placeNumber<=4 AND t.flight.flightId="+currFlight.getFlightId());
        return tickets;
    }
    public ArrayList<Ticket> getTicketsFirst(){
        ArrayList<Ticket> tickets= jpadaoTicket.getObjectsByCondition("5<=t.placeNumber AND t.placeNumber<=12 AND t.flight.flightId="+currFlight.getFlightId());
        return tickets;
    }
    public ArrayList<Ticket> getTicketsSecond(){
        ArrayList<Ticket> tickets= jpadaoTicket.getObjectsByCondition("13<=t.placeNumber AND t.placeNumber<=20 AND t.flight.flightId="+currFlight.getFlightId());
        return tickets;
    }
    public ArrayList<Ticket> getTicketsThird(){
        ArrayList<Ticket> tickets= jpadaoTicket.getObjectsByCondition("21<=t.placeNumber AND t.placeNumber<=28 AND t.flight.flightId="+currFlight.getFlightId());
        return tickets;
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public String getToTown() {
        return toTown;
    }

    public void setToTown(String toTown) {
        this.toTown = toTown;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public Flight getCurrFlight() {
        return currFlight;
    }

    public void setCurrFlight(Flight currFlight) {
        this.currFlight = currFlight;
    }
    public String saveCurrFlight(Flight c){
        currFlight=c;
        return "/pages/choose_ticket_page.xhtml?faces-redirect=true";
    }

    public HashSet<Ticket> getChoosenTickets() {
        return choosenTickets;
    }

    public void setChoosenTickets(HashSet<Ticket> choosenTickets) {
        this.choosenTickets = choosenTickets;
    }

    public double totalPrice(){
        double sum=0;
        for (Ticket t:
             choosenTickets) {
            sum+=t.getPrice();
        }
        return sum;
    }

    public String buyTickets(){

        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        ArrayList<User> users = jpadaoUser.getObjectsByCondition("u.eMail = '"+username+"'");
        User user = users.isEmpty()? null : users.get(0);
        for (Ticket t:
             choosenTickets) {
            t.setOwner(user);
            jpadaoTicket.updateObject(t);
            sendMessage(t.getTicketId());
        }
        choosenTickets = new HashSet<>();
        Collection<Flight> currFlights = new ArrayList<Flight>();
        String fromTown = "";
        String toTown = "";
        Date departure_date =null;
        Flight currFlight = null;
        return "/pages/personal_tickets.xhtml?faces-redirect=true";
    }

    public void sendMessage(Integer userId) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage();
            message.setText("" + userId);
            producer.send(message);
            LOGGER.warn("Sending message: " + message.getText());
            session.close();
            connection.close();
        } catch (JMSException ex) {
            LOGGER.error("Sending message failed : " + ex);
        }
    }
}
