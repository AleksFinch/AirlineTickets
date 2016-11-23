package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.User;
import com.olexandr.finchuk.jpa_dao.JPADAOUser;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@ManagedBean
@ViewScoped
public class UserBean {
    @EJB
    JPADAOUser jpadaoUser;
    User user;
    String lastName;
    String firstName;
    String eMail;

    boolean editable = false;




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @PostConstruct
    public void updateUser(){
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        ArrayList<User> users = jpadaoUser.getObjectsByCondition("u.eMail = '"+username+"'");
        user = users.isEmpty()? null : users.get(0);

    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    public void saveUser(){
        jpadaoUser.updateObject(user);
        editable = false;
    }
    public String edit(){
        editable = true;
        return null;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
