package com.mkalita;

import net.ucanaccess.jdbc.UcanaccessDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;


/**
 * Hello world!
 */
@SpringBootApplication
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

        SpringApplication.run(App.class, args);
    }
}
