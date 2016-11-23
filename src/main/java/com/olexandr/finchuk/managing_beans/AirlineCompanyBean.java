package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.AirlineCompany;
import com.olexandr.finchuk.jpa_dao.JPADAOAirlineCompany;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Olexandr Finchuk on 19.11.2016.
 */
@ManagedBean
@SessionScoped
public class AirlineCompanyBean {
    Collection<AirlineCompany> companies = new ArrayList<AirlineCompany>();
    String companyName;
    String imgPath;

    @EJB
    JPADAOAirlineCompany jpadaoAirlineCompany;

    public Collection<AirlineCompany> getCompanies() {
        return companies;
    }
    @PostConstruct
    public void updateCurrCompanies() {


        companies = jpadaoAirlineCompany.getAllObjects();

    }

    public void addNewCompany(){
        AirlineCompany company = new AirlineCompany();
        company.setCompanyName(companyName);
        company.setImgPath(imgPath);
        jpadaoAirlineCompany.addObject(company);
        updateCurrCompanies();
        companyName="";
        companyName = "";
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
