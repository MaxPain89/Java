package com.revivermx.common;

import org.apache.log4j.Logger;

import java.io.*;

public class App {
    private static final Logger log = Logger.getLogger(App.class);
    public static void main(String[] args) throws IOException {
        String path = args[0];
        RevertUtil revertUtil = new RevertUtil();
        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream revertedChangesOs = new ByteArrayOutputStream();
        FileOutputStream fout;
        String outPath;
        if (args.length == 1) {
           outPath = path + ".fixed";
        } else if (args.length == 2) {
            outPath = args[1];
        } else {
            throw new RuntimeException(String.format("The util receive one (source file) or two (source and destination file) arguments. received %d arguments", args.length));
        }
        fout = new FileOutputStream(outPath);
        log.info("Reverting changes");
        revertUtil.revertChanges(fis, revertedChangesOs);
        ByteArrayInputStream withHeaderIs;
        try (ByteArrayInputStream revertedChangesIs = new ByteArrayInputStream(revertedChangesOs.toByteArray())) {
            try (ByteArrayOutputStream withHeaderOs = new ByteArrayOutputStream()) {
                log.info("Return bmp header");
                revertUtil.returnHeader(revertedChangesIs, withHeaderOs);
                withHeaderIs = new ByteArrayInputStream(withHeaderOs.toByteArray());
            }
        }
        log.info("Rotating image");
        revertUtil.rotateImage(withHeaderIs, fout);
        log.info("Success");
        log.info(String.format("Result in file: %s", outPath));
    }
}
