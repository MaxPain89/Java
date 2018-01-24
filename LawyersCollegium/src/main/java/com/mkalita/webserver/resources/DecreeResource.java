package com.mkalita.webserver.resources;

import com.mkalita.controllers.DecreeController;
import com.mkalita.utils.DateParser;
import com.mkalita.wire.WireDecree;
import org.springframework.web.bind.annotation.*;

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
                                @RequestParam(value = "endDate", required = false) String endDate) {
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

    @RequestMapping(value = "/decrees", method = RequestMethod.POST)
    public @ResponseBody
    WireDecree createDecree(@RequestParam Long lawyerId,
                            @RequestBody WireDecree wireDecree) {
        return decreeController.createDecree(wireDecree, lawyerId);
    }

    @RequestMapping(value = "/decree/{id}", method = RequestMethod.GET)
    public @ResponseBody
    WireDecree getDecree(@PathVariable(value = "id") Long id) {
        return decreeController.getDecree(id);
    }

    @RequestMapping(value = "/decree/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    WireDecree updateDecree(@PathVariable Long id,
                            @RequestParam Long lawyerId,
                            @RequestBody WireDecree wireDecree) {
        return decreeController.updateDecree(id, wireDecree, lawyerId);
    }

    @RequestMapping(value = "/decree/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    WireDecree deleteDecree(@PathVariable Long id) {
        return decreeController.deleteDecree(id);
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
