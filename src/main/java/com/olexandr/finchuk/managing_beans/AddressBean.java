package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.Address;
import com.olexandr.finchuk.entities.Flight;
import com.olexandr.finchuk.jpa_dao.JPADAOAddress;
import com.olexandr.finchuk.jpa_dao.JPADAOFlight;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Olexandr Finchuk on 19.11.2016.
 */
@ManagedBean
@SessionScoped
public class AddressBean {
    Collection<Address> addresses = new ArrayList<Address>();
    String town;
    String country;

    @EJB
    JPADAOAddress jpadaoAddress;

    public Collection<Address> getAddresses() {
        return addresses;
    }
    @PostConstruct
    public void updateCurrAdresses() {


        addresses = jpadaoAddress.getAllObjects();

    }

    public void addNewAdress(){
        Address address = new Address();
        address.setCountry(country);
        address.setTown(town.toLowerCase());
        jpadaoAddress.addObject(address);
        updateCurrAdresses();
        town="";
        country = "";
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
