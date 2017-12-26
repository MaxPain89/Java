package com.mkalita;

import com.mkalita.jpa.Collegium;
import com.mkalita.jpa.Decree;
import com.mkalita.jpa.Lawyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;

/**
 * Hello world!
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws SQLException {
        log.info("Hello World!");
        String path = args[0];
        File f = new File(path);
        if(!f.exists() || f.isDirectory()) {
            throw new RuntimeException(String.format("File %s does't exist", path));
        }
        try (HibernateUtil util = new HibernateUtil(path)){
            EntityManager em = util.getEm();
            List<Decree> decrees= em.createQuery("SELECT d from Decree d JOIN fetch d.lawyer l join fetch l.collegium c").getResultList();
            for (Decree decree : decrees) {
                Lawyer lawyer = decree.getLawyer();
                Collegium collegium = null;
                if (lawyer != null) {
                    collegium = lawyer.getCollegium();
                }
                System.out.println(String.format("Lawyer: %s\t collegium: %s\t accussed: %s\t amount %s",
                        lawyer != null ? lawyer.getFullName() : null,
                        collegium != null ? collegium.getName() : null,
                        decree.getAccused(), String.valueOf(decree.getAmount())));
            }
        } catch (IOException e) {
            log.error("Couldn't close em");
        }
    }
}
