package com.mkalita.webserver.resources;

import com.mkalita.controllers.CollegiumController;
import com.mkalita.wire.WireCollegium;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollegiumResource {
    private CollegiumController collegiumController;

    public CollegiumResource(CollegiumController collegiumController) {
        this.collegiumController = collegiumController;
    }

    @RequestMapping(value = "/collegiums", method = RequestMethod.GET)
    public @ResponseBody
    List<WireCollegium> getCollegiums() {
        return collegiumController.getCollegiums();
    }

    @RequestMapping(value = "/collegium/{id}", method = RequestMethod.GET)
    public @ResponseBody
    WireCollegium getCollegium(@PathVariable Long id) {
        return collegiumController.getCollegium(id);
    }

    @RequestMapping(value = "/collegiums", method = RequestMethod.POST)
    public @ResponseBody
    WireCollegium createCollegium(@RequestBody WireCollegium wireCollegium) {
        return collegiumController.createCollegium(wireCollegium);
    }

    @RequestMapping(value = "/collegium/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    WireCollegium updateCollegium(@PathVariable Long id,
                                  @RequestBody WireCollegium wireCollegium) {
        return collegiumController.updateCollegium(wireCollegium, id);
    }

    @RequestMapping(value = "/collegium/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    WireCollegium deleteCollegium(@PathVariable Long id,
                                  @RequestParam(defaultValue = "false") Boolean force) {
        return collegiumController.deleteCollegium(id, force);
    }
}
