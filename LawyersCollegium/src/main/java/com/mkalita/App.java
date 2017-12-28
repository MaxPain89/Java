package com.mkalita;

import com.mkalita.controllers.MdbController;
import com.mkalita.jpa.Collegium;
import com.mkalita.jpa.Decree;
import com.mkalita.jpa.Lawyer;
import com.mkalita.utils.HibernateUtil;
import net.ucanaccess.jdbc.UcanaccessDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;


/**
 * Hello world!
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Starting...");
        String pathToDb = "";
        if (args.length < 1) {
            log.error("Missed path to .mdb file. Exit.");
            System.exit(1);
        } else {
            pathToDb = args[0];
        }
        try {
            Class.forName(UcanaccessDriver.class.getName());
        } catch (ClassNotFoundException e) {
            log.error(String.format("Couldn't find %s in classpath.", UcanaccessDriver.class.getName()));
            System.exit(1);
        }
        File f = new File(pathToDb);
        if(!f.exists() || f.isDirectory()) {
            throw new RuntimeException(String.format("File %s does't exist", pathToDb));
        }
        try (HibernateUtil hibernateUtil = new HibernateUtil(pathToDb)) {
            MdbController mdbController = new MdbController(hibernateUtil);
            mdbController.fixInconsistency();
            List<Decree> decrees = mdbController.getAllDecrees();
            for (Decree decree : decrees) {
                Lawyer lawyer = decree.getLawyer();
                Collegium collegium = null;
                if (lawyer != null) {
                    collegium = lawyer.getCollegium();
                }

                System.out.println(String.format("Lawyer: %s\t wireCollegium: %s\t accused: %s\t amount %s, number: %s",
                        lawyer != null ? lawyer.getFullName() : null,
                        collegium != null ? collegium.getName() : null,
                        decree.getAccused(), String.valueOf(decree.getAmount()), String.valueOf(decree.getId())));
            }
        }
    }
}
