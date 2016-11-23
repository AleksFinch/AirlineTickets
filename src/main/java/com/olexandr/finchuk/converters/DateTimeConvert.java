package com.olexandr.finchuk.converters;

import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Olexandr Finchuk on 01.11.2016.
 */
@ManagedBean
@SessionScoped
public class DateTimeConvert implements Converter {
    final static Logger LOGGER = Logger.getLogger(DateTimeConvert.class);


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        java.util.Date date = new java.util.Date();
        try {
             date= format.parse(s);
             format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(date.getTime());
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        java.util.Date date = new java.util.Date(((Timestamp)o).getTime());
        String str ="";
        str = format.format(date);
        return str;
    }
}
