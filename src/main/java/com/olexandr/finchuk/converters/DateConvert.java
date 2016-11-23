package com.olexandr.finchuk.converters;

import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Olexandr Finchuk on 01.11.2016.
 */
@ManagedBean
@SessionScoped
public class DateConvert implements Converter {
    final static Logger LOGGER = Logger.getLogger(DateConvert.class);


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        try {
             date= format.parse(s);
             format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(date.getTime());
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date(((Date)o).getTime());
        String str ="";
        str = format.format(date);
        return str;
    }
}
