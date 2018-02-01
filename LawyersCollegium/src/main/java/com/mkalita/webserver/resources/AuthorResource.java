package com.mkalita.webserver.resources;

import com.mkalita.controllers.AuthorController;
import com.mkalita.wire.WireAuthor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorResource {
    AuthorController authorController;

    public AuthorResource(AuthorController authorController) {
        this.authorController = authorController;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody
    List<WireAuthor> getAuthors() {
        return authorController.getAuthors();
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
    public @ResponseBody
    WireAuthor getAuthor(@PathVariable("id") Integer id) {
        return authorController.getAuthor(id);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public @ResponseBody
    WireAuthor createAuthor(@RequestBody WireAuthor wireAuthor) {
        return authorController.addAuthor(wireAuthor);
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    WireAuthor updateAuthor(@PathVariable("id") Integer id,
                         @RequestBody WireAuthor wireAuthor) {
        return authorController.updateAuthor(id, wireAuthor);
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    WireAuthor deleteAuthor(@PathVariable("id") Integer id) {
        return authorController.deleteAuthor(id);
    }
}
