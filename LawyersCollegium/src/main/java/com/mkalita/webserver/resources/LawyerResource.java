package com.mkalita.webserver.resources;

import com.mkalita.controllers.LawyerController;
import com.mkalita.wire.WireLawyer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LawyerResource {
    private LawyerController lawyerController;

    public LawyerResource(LawyerController lawyerController) {
        this.lawyerController = lawyerController;
    }

    @RequestMapping(value = "/lawyers", method = RequestMethod.GET)
    public @ResponseBody
    List<WireLawyer> getDecrees() {
        return lawyerController.getLawyers();
    }

    @RequestMapping(value = "/lawyer/{id}", method = RequestMethod.GET)
    public @ResponseBody
    WireLawyer getLawyer(@PathVariable Long id) {
        return lawyerController.getLawyer(id);
    }

    @RequestMapping(value = "/lawyers", method = RequestMethod.POST)
    public @ResponseBody
    WireLawyer createLawyer(@RequestParam(required = false) Long collegiumId,
                            @RequestBody WireLawyer wireLawyer) {
        return lawyerController.createLawyer(wireLawyer, collegiumId);
    }

    @RequestMapping(value = "/lawyer/{id}", method = RequestMethod.POST)
    public @ResponseBody
    WireLawyer updateLawyer(@PathVariable Long id,
                            @RequestParam(required = false) Long collegiumId,
                            @RequestBody WireLawyer wireLawyer) {
        return lawyerController.updateLawyer(wireLawyer, id, collegiumId);
    }
}
