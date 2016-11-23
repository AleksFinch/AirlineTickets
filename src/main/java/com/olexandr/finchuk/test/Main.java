package com.olexandr.finchuk.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by Olexandr Finchuk on 10/3/2016.
 */
public class Main {
    public static void main(String[] args){


        String password = "user2";
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
        System.out.println("password hash: "+passwordHash);


    }
}
