package com.mkalita.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mkalita.jpa.Collegium;
import com.mkalita.utils.PdfGenerator;
import com.mkalita.wire.WireDecree;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class ReportController {
    public static final float HEADER_CELL_HEIGHT = 30.62f;
    public static final float ROWS_CELL_HEIGHT = 40.03f;
    public static final float[] COLUMN_WIGHTS = new float[] {119.39f, 52.05f, 183.68f, 113.27f, 64.2924f};
    BaseFont bf;
    private DecreeController decreeController;
    private CollegiumController collegiumController;

    public ReportController(DecreeController decreeController,
                            CollegiumController collegiumController) {
        this.decreeController = decreeController;
        this.collegiumController = collegiumController;
        try {
            bf = BaseFont.createFont("/home/mkalitinenkov/temp/TNR.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Couldn't initialize fonts system");
        }
    }

    public HttpEntity<byte[]> getDecreesReport(Long collegiumId, Date payDate, Boolean download) throws DocumentException, IOException {
        Collegium collegium = collegiumController._getCollegium(collegiumId);
        List<WireDecree> decreesForYear = decreeController.getDecreesByCollegium(collegiumId, payDate);
        PdfGenerator pdf = new PdfGenerator();
        byte[] documentBody = pdf
                .addParagraph(generateUpperLabel("Управление Судебного департамента в Смоленской области", Element.ALIGN_RIGHT, 12))
                .addParagraph(generateUpperLabel(" ", Element.ALIGN_CENTER, 12))
                .addParagraph(generateUpperLabel("Ведомость на оплату адвокатов по определениям", Element.ALIGN_CENTER, 14))
                .addParagraph(generateUpperLabel("(районный, городских, мировых) судов", Element.ALIGN_CENTER, 14))
                .addTable(generateMainTable(collegium, decreesForYear))
                .build();
        return addRequestHeaders(download, documentBody);
    }

    private Paragraph generateUpperLabel(String value, int alignment, int size) {
        Font font = new Font(bf, size, Font.NORMAL);
        Paragraph paragraph = new Paragraph(value, font);
        paragraph.setAlignment(alignment);
        return paragraph;
    }

    private PdfPTable generateMainTable(Collegium collegium, List<WireDecree> decreesForYear) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(COLUMN_WIGHTS);
        table.setLockedWidth(true);
        table.setSpacingBefore(35);
        table.setSpacingAfter(35);
        Font fontForCollegium = new Font(bf, 8, Font.BOLD);
        Font fontForDate = new Font(bf, 10, Font.NORMAL);
        Font fontForHeaders = new Font(bf, 10, Font.BOLD);
        Font fontForOtherTableElements = new Font(bf, 11, Font.NORMAL);
        generateHeaders(fontForHeaders, table, HEADER_CELL_HEIGHT, "Наименование юрконсультации", "Дата постанов.", "Адвокат", "Обвиняемый", "Сумма");
        Boolean firstRow = true;
        for (WireDecree wireDecree : decreesForYear) {
            PdfPCell collegiumCell = new PdfPCell(new Phrase(firstRow ? collegium.getName() : "", fontForCollegium));
            collegiumCell.setFixedHeight(ROWS_CELL_HEIGHT);
            table.addCell(collegiumCell);
            firstRow = false;
            PdfPCell dateCell = new PdfPCell(new Phrase(wireDecree.getDate().replace("/", "."), fontForDate));
            dateCell.setFixedHeight(ROWS_CELL_HEIGHT);
            table.addCell(dateCell);
            PdfPCell lawyerCell = new PdfPCell(new Phrase(wireDecree.getLawyer(), fontForOtherTableElements));
            dateCell.setFixedHeight(ROWS_CELL_HEIGHT);
            table.addCell(lawyerCell);
            PdfPCell accusedCell = new PdfPCell(new Phrase(wireDecree.getAccused(), fontForOtherTableElements));
            dateCell.setFixedHeight(ROWS_CELL_HEIGHT);
            table.addCell(accusedCell);
            PdfPCell amountCell = new PdfPCell(new Phrase(String.valueOf(wireDecree.getAmount()), fontForOtherTableElements));
            dateCell.setFixedHeight(ROWS_CELL_HEIGHT);
            table.addCell(amountCell);
        }
        return table;
    }

    private void generateHeaders(Font font10Bold, PdfPTable table, float cellHeight, String... names) {
        for (String name : names) {
            PdfPCell cell = new PdfPCell(new Phrase(name, font10Bold));
            cell.setFixedHeight(cellHeight);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private HttpEntity<byte[]> addRequestHeaders(Boolean download, byte[] documentBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        if (download) {
            headers.set(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=report.pdf");
        } else {
            headers.set(HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=report.pdf");
        }
        headers.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, headers);
    }
}
