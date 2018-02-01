package com.maxpain.experiments;

import org.apache.log4j.Logger;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger log = Logger.getLogger(App.class);
    public static void main( String[] args )
    {

        log.info("Hello World!");
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            log.info(url.getFile());
        }

        int numberOfSpaces = 80;
        float sum = 74567.00f;
        System.out.println(String.format("Итого:%" + 80 + ".2f", sum));
    }
}
