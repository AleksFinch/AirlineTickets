package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.Ticket;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Olexandr Finchuk on 23.11.2016.
 */
@ManagedBean(name = "boughtTicket" ,  eager=true)
@ApplicationScoped
public class BoughtTicket {
    Collection<Ticket> tickets = new ArrayList<>();

    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName="java:jboss/exported/jms/topic/test")
    private Topic destination;

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket t){
        tickets.add(t);
    }

    public String showMessage() {
        String message = null;
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(destination);
            connection.start();
            TextMessage msg = (TextMessage)consumer.receive();
            if (msg != null) {
                message = msg.getText();

            }
        } catch (JMSException ex) {
        }
        return message;
    }

}
