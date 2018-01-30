package com.mkalita.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    Document document;
    PdfWriter writer;
    ByteArrayOutputStream baos;

    public PdfGenerator() throws DocumentException {
        document = new Document(PageSize.A4, 20, 20, 20, 20);
        baos = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(document, this.baos);
        document.open();
    }

    public PdfGenerator addTable(PdfPTable table) throws DocumentException {
        this.document.add(table);
        return this;
    }

    public PdfGenerator addParagraph(Paragraph paragraph) throws DocumentException {
        this.document.add(paragraph);
        return this;
    }

    public byte[] build() throws IOException {
        if (document.isOpen()) {
            document.close();
        }
        writer.close();
        baos.close();
        return baos.toByteArray();
    }
}
