package com.revivermx.common;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        RevertUtil revertUtil = new RevertUtil();
        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream revertedChangesOs = new ByteArrayOutputStream();
        FileOutputStream fout;
        if (args.length == 1) {
            fout = new FileOutputStream(path + ".fixed");
        } else if (args.length == 2) {
            fout = new FileOutputStream(args[1]);
        } else {
            throw new RuntimeException(String.format("The util receive one (source file) or two (source and destination file) arguments. received %d arguments", args.length));
        }
        revertUtil.revertChanges(fis, revertedChangesOs);
        ByteArrayInputStream revertedChangesIs = new ByteArrayInputStream(revertedChangesOs.toByteArray());
        ByteArrayOutputStream withHeaderOs = new ByteArrayOutputStream();
        revertUtil.returnHeader(revertedChangesIs, withHeaderOs);
        ByteArrayInputStream withHeaderIs = new ByteArrayInputStream(withHeaderOs.toByteArray());
        revertUtil.rotateImage(withHeaderIs, fout);
    }
}
