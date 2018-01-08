package com.mkalita.webserver.controllers;

import com.mkalita.controllers.MdbController;
import com.mkalita.wire.WireDecree;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {

    private final MdbController mdbController;

    public AppController(MdbController mdbController) {
        this.mdbController = mdbController;
    }

    @RequestMapping(value = "/decrees", method = RequestMethod.GET)
    public @ResponseBody
    List<WireDecree> getDecrees(@RequestParam(value = "year", required = false) Integer year) {
        mdbController.fixInconsistency();
        return mdbController.getDecreesForYear(year);
    }
}
