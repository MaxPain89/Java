package com.revivermx.common;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.*;

public class RevertUtil {
    private static final Logger log = Logger.getLogger(RevertUtil.class);
    private static final int HALF_ROW_WIDTH_BYTES = 2400/4;
    private static final int DLP_IMAGE_ROTATION_ANGLE = 180;

    RevertUtil() {
    }

    public void revertChanges(InputStream is, OutputStream outStream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        IOUtils.copy(is, bout);
        byte[] imageArray = bout.toByteArray();
        int length = imageArray.length;
        int offset = length/2;
        byte[] firstPart = new byte[length/2];
        byte[] secondPart = new byte[length/2];
        System.arraycopy(imageArray, 0, firstPart, 0, firstPart.length);
        System.arraycopy(imageArray, offset, secondPart, 0, secondPart.length);
        InputStream firstPartIs = new ByteArrayInputStream(firstPart);
        InputStream secondPartIs = new ByteArrayInputStream(secondPart);
        int count = length/HALF_ROW_WIDTH_BYTES;
        byte[] temp = new byte[HALF_ROW_WIDTH_BYTES];
        for (int i = 0; i < count; i++) {
            int read;
            if (i % 2 == 0) {
                read = firstPartIs.read(temp);
            } else {
                read = secondPartIs.read(temp);
            }
            log.info(String.format("Read %d bytes", read));
            outStream.write(temp);
        }
    }

    public void returnHeader(InputStream is, OutputStream os) throws IOException {
        InputStream isHeader = this.getClass().getClassLoader().getResourceAsStream("bmp_header");
        IOUtils.copy(isHeader, os);
        IOUtils.copy(is, os);
    }

    public void rotateImage(InputStream is, OutputStream os) throws IOException {
        BufferedImage image = ImageIO.read(is);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(DLP_IMAGE_ROTATION_ANGLE), image.getWidth() / 2., image.getHeight() / 2.);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType(), (IndexColorModel)image.getColorModel());
        op.filter(image, result);
        ImageIO.write(result, "bmp", os);
    }
}
