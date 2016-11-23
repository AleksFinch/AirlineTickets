package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.Ticket;
import com.olexandr.finchuk.jpa_dao.JPADAORole;
import com.olexandr.finchuk.jpa_dao.JPADAOTicket;
import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.bean.ManagedProperty;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@MessageDriven(
        mappedName="orderListUpdater",
        activationConfig =
                {
                        @ActivationConfigProperty(propertyName="messagingType", propertyValue="javax.jms.MessageListener"),
                        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
                        @ActivationConfigProperty(propertyName="destination", propertyValue="java:jboss/exported/jms/topic/test"),
                        @ActivationConfigProperty(propertyName="connectionFactoryName", propertyValue="java:/JmsXA"),
                        @ActivationConfigProperty(propertyName="MaxPoolSize", propertyValue="10"),
                        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true")
                })
public class OrderListUpdater implements MessageListener {

    private static final Logger logger = Logger.getLogger(OrderListUpdater.class);

    @EJB
    JPADAOTicket jpadaoTicket;
    @ManagedProperty(value = "#{boughtTicket}")
    private BoughtTicket boughtTicket ;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String messageText = ((TextMessage) message).getText();
                Integer ticketOrderId = Integer.parseInt(messageText.trim());
                Ticket t = jpadaoTicket.getObjectById(ticketOrderId);
                logger.info("Recieve id :  " + ticketOrderId);
                boughtTicket.addTicket(t);
                logger.info("Updating with id = " + ticketOrderId);
            } catch (JMSException e) {
                logger.error("Error in onMessage : " + e);
            }
        } else {
            logger.warn("Message is not instance of text message!");
        }
    }

    public BoughtTicket getBoughtTicket() {
        return boughtTicket;
    }

    public void setBoughtTicket(BoughtTicket boughtTicket) {
        this.boughtTicket = boughtTicket;
    }
}