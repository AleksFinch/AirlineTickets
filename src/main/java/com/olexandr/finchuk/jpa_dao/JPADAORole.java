package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.Role;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAORole extends JPADataAccessObject<Role> {

    public JPADAORole() {

    }
    @Override
    public ArrayList<Role> getObjectsByCondition(String condition){

        Query query =manager.createQuery("SELECT r from Role r WHERE "+ condition);
        return (ArrayList<Role>) query.getResultList();
    }


    @Override
    public Role getObjectById(int id){

        return manager.find(Role.class,id);
    }



    @Override
    public void deleteObjectById(int id){

        Query q = manager.createQuery("DELETE FROM Role r WHERE r.roleId= :id");
        q.setParameter("id",id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll(){

        manager.createQuery("DELETE FROM Role r").executeUpdate();

    }
    @Override
    public ArrayList<Role> getAllObjects() {
        return (ArrayList<Role>) manager.createQuery("SELECT r from Role r").getResultList();
    }
}
