package com.hystax.demo.resources;

import com.hystax.demo.controllers.PhoneBookController;
import com.hystax.demo.wire.CalculateResponse;
import com.hystax.demo.wire.WireBook;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

@RestController
public class PhoneBookResource {
    private PhoneBookController phoneBookController;

    public PhoneBookResource() {
        this.phoneBookController = new PhoneBookController();
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody
    List<WireBook> getBooks() {
        return phoneBookController.getBooks();
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public @ResponseBody
    WireBook getBook(@PathVariable("id") Long id) {
        return phoneBookController.getBook(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public @ResponseBody
    WireBook createBook(@RequestBody WireBook wireBook) {
        return phoneBookController.addBook(wireBook);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    WireBook updateBook(@PathVariable("id") Long id,
                        @RequestBody WireBook wireBook) {
        return phoneBookController.updateBook(id, wireBook);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    WireBook deleteBook(@PathVariable("id") Long id) {
        return phoneBookController.removeBook(id);
    }

    @RequestMapping(value = "/book/calculate", method = RequestMethod.GET)
    public @ResponseBody
    CalculateResponse calculateBooks() {
        return phoneBookController.calculate();
    }

    @RequestMapping(value = "/book/invalidate", method = RequestMethod.POST)
    public @ResponseBody
    String invalidate() {
        return phoneBookController.invalidateCache();
    }
}
