package com.mkalita.webserver.resources;

import com.mkalita.controllers.DecreeController;
import com.mkalita.wire.WireDecree;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

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
                                HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return decreeController.getDecreesForYear(year);
    }
}
