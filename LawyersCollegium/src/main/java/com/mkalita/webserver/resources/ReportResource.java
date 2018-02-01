package com.mkalita.webserver.resources;

import com.itextpdf.text.DocumentException;
import com.mkalita.controllers.ReportController;
import com.mkalita.utils.DateParser;
import com.mkalita.webserver.exceptions.InternalServerError;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ReportResource {
    private ReportController reportController;

    public ReportResource(ReportController reportController) {
        this.reportController = reportController;
    }

    @RequestMapping(value = "/reports/decrees/collegium/{collegiumId}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDecreesByYear(@RequestParam(required = false, defaultValue = "false") Boolean download,
                                               @PathVariable Long collegiumId,
                                               @RequestParam String payDate) {
        try {
            return reportController.getDecreesReport(collegiumId, DateParser.dateFromStr(payDate), download);
        } catch (DocumentException|IOException e) {
            throw new InternalServerError(e.getMessage());
        }

    }
}
