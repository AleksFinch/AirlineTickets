package com.olexandr.finchuk.converters;

import com.olexandr.finchuk.entities.Address;
import com.olexandr.finchuk.jpa_dao.JPADAOAddress;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by Olexandr Finchuk on 01.11.2016.
 */
@ManagedBean
@SessionScoped
public class AddressConvert implements Converter {
    final static Logger LOGGER = Logger.getLogger(AddressConvert.class);
    @EJB
    JPADAOAddress jpadaoAddress;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return jpadaoAddress.getObjectById(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return String.valueOf(((Address)o).getAddressId());
    }
}
