package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.Role;
import com.olexandr.finchuk.entities.User;
import com.olexandr.finchuk.jpa_dao.JPADAORole;
import com.olexandr.finchuk.jpa_dao.JPADAOUser;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@ManagedBean
public class AuthorizationBean {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationBean.class);

    String firstName = "";
    String lastName = "";
    String eMail = "";
    String password = "";
    String logEmail = "";
    String logPassword = "";
    @EJB
    JPADAOUser jpadaoUser;
    @EJB
    JPADAORole jpadaoRole;



    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/index.xhtml?faces-redirect=true";
    }

    public String logIn(){
        ExternalContext c = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ((HttpServletRequest)c.getRequest()).login(logEmail,logPassword);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "/pages/index.xhtml?faces-redirect=true";
    }



    public String signIn(){

        User user = new User();
        ArrayList<User> users = jpadaoUser.getObjectsByCondition("u.eMail = '"+eMail+"'");
        if(!users.isEmpty()){
            return "/registration.xhtml?faces-redirect=true&error_email=true";
        }

       String passwordHash = getEncodedPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordHash);
        user.seteMail(eMail);
        ArrayList<Role> roles = jpadaoRole.getObjectsByCondition("r.role = 'authenticated_user'");
        Role role = roles.isEmpty()? null : roles.get(0);
        user.setRole(role);
        try{
            jpadaoUser.addObject(user);
        }catch (Exception e){
            return "/registration.xhtml?faces-redirect=true&error=true";
        }

        firstName = "";
        lastName = "";
        eMail = "";
        password = "";
        return "/pages/index.xhtml?faces-redirect=true";
    }



    public static String getEncodedPassword(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] passwordBytes = password.getBytes();
        byte[] hash = md.digest(passwordBytes);
        String passwordHash =
                Base64.getEncoder().encodeToString(hash);
        return passwordHash;

    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
