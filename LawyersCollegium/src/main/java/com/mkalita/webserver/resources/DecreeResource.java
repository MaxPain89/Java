package com.mkalita.webserver.resources;

import com.mkalita.controllers.DecreeController;
import com.mkalita.utils.DateParser;
import com.mkalita.wire.WireDecree;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class DecreeResource {

    private final DecreeController decreeController;

    public DecreeResource(DecreeController decreeController) {
        this.decreeController = decreeController;
    }

    @RequestMapping(value = "/decrees", method = RequestMethod.GET)
    public @ResponseBody
    List<WireDecree> getDecrees(@RequestParam(value = "year", required = false) Integer year,
                                @RequestParam(value = "startDate", required = false) String startDate,
                                @RequestParam(value = "endDate", required = false) String endDate,
                                HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (startDate != null || endDate != null) {
            Date start = getDate(startDate);
            Date end = getDate(endDate);
            return decreeController.getDecreesByDate(start, end);
        } else if (year != null) {
            return decreeController.getDecreesForYear(year);
        } else {
            return decreeController.getAllDecrees();
        }
    }

    private Date getDate(String date) {
        Date result;
        if (date != null) {
            result = DateParser.dateFromStr(date);

        } else {
            result = null;
        }
        return result;
    }
}
