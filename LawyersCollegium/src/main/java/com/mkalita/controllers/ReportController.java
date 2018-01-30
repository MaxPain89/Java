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
    private static final float HEADER_CELL_HEIGHT = 30.62f;
    private static final float SUM_CELL_HEIGHT = 25.00f;
    private static final float ROWS_CELL_HEIGHT = 38.03f;
    private static final float[] COLUMN_WIGHTS = new float[] {119.39f, 52.05f, 183.68f, 113.27f, 64.2924f};
    private static final float[] COLUMN_WIGHTS_FOR_SUMMS = new float[] {119.39f, 292.36f, 120.92f};
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
        List<WireDecree> decreesForDate = decreeController.getDecreesByCollegium(collegiumId, payDate);
        PdfGenerator pdf = new PdfGenerator();
        Double sum = decreesForDate.stream().mapToDouble(WireDecree::getAmount).sum();
        byte[] documentBody = pdf
                .addParagraph(generateParagraph("Управление Судебного департамента в Смоленской области", Element.ALIGN_RIGHT, 12))
                .addParagraph(generateParagraph(" ", Element.ALIGN_CENTER, 12))
                .addParagraph(generateParagraph("Ведомость на оплату адвокатов по определениям", Element.ALIGN_CENTER, 14))
                .addParagraph(generateParagraph("(районный, городских, мировых) судов", Element.ALIGN_CENTER, 14))
                .addTable(generateMainTable(collegium, decreesForDate))
                .addTable(generateStringForSum(sum))
                .build();
        return addRequestHeaders(download, documentBody);
    }

    private Paragraph generateParagraph(String value, int alignment, int size) {
        return generateParagraph(value, alignment, size, 0f);
    }

    private PdfPTable generateStringForSum(double amount) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(COLUMN_WIGHTS_FOR_SUMMS);
        table.setLockedWidth(true);
        generateRowForSum(table, " Итого:", amount);
        generateRowForSum(table, " Оплата труда адвокатов за счет государства:", amount);
        return table;
    }

    private void generateRowForSum(PdfPTable table, String label, double amount) {
        Font font = new Font(bf, 12, Font.NORMAL);
        //empty cell
        PdfPCell cell1 = new PdfPCell(new Phrase(" ", font));
        cell1.setFixedHeight(SUM_CELL_HEIGHT);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(label, font));
        cell2.setFixedHeight(SUM_CELL_HEIGHT);
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase(String.format("%.2f", amount), font));
        cell3.setFixedHeight(SUM_CELL_HEIGHT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell3.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell3);
    }

    private Paragraph generateParagraph(String value, int alignment, int size, float indentation) {
        Font font = new Font(bf, size, Font.NORMAL);
        Paragraph paragraph = new Paragraph(value, font);
        if (alignment != -1) {
            paragraph.setAlignment(alignment);
        }
        paragraph.setIndentationLeft(indentation);
        return paragraph;
    }

    private PdfPTable generateMainTable(Collegium collegium, List<WireDecree> decreesForYear) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(COLUMN_WIGHTS);
        table.setLockedWidth(true);
        table.setSpacingBefore(35);
        table.setSpacingAfter(5);
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

    private void generateHeaders(Font font, PdfPTable table, float cellHeight, String... names) {
        for (String name : names) {
            PdfPCell cell = new PdfPCell(new Phrase(name, font));
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
