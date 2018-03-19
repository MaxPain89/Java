package com.mkalita.webserver.resources;

import com.itextpdf.text.DocumentException;
import com.mkalita.controllers.ReportController;
import com.mkalita.utils.DateParser;
import com.mkalita.webserver.exceptions.InternalServerError;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ReportResource {
    private ReportController reportController;

    public ReportResource(ReportController reportController) {
        this.reportController = reportController;
    }

    @RequestMapping(value = "/reports/decrees/collegium/{collegiumId}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDecreesReport(@RequestParam(required = false, defaultValue = "false") Boolean download,
                                               @RequestParam(required = false, defaultValue = "1") int reporterId,
                                               @PathVariable Long collegiumId,
                                               @RequestParam String payDate) {
        try {
            return reportController.getDecreesReport(collegiumId, DateParser.dateFromStr(payDate), download, reporterId);
        } catch (DocumentException | IOException e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    @RequestMapping(value = "/reports/decrees/decree/{decreeId}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDecreesReportByDecree(@RequestParam(required = false, defaultValue = "false") Boolean download,
                                                       @RequestParam(required = false, defaultValue = "1") int reporterId,
                                                       @PathVariable Long decreeId) {
        try {
            return reportController.getDecreesReportByDecree(decreeId, download, reporterId);
        } catch (DocumentException | IOException e) {
            throw new InternalServerError(e.getMessage());
        }
    }
}
