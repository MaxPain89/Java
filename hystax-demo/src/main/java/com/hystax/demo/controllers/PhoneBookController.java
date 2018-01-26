package com.hystax.demo.controllers;
import com.hystax.demo.jpa.Phonebook;
import com.hystax.demo.util.JPAUtil;
import com.hystax.demo.wire.WireBook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Component
public class PhoneBookController {

    private final EntityManagerFactory emf;
    private EntityManager em;
    private static final String PERSISTENT_UNIT_NAME = "OracleJPA";

    public PhoneBookController() {
        emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public PhoneBookController(EntityManager em) {
        emf = null;
        this.em = em;
    }

//    @RequestMapping(value = "/books", method = RequestMethod.GET)
//    public @ResponseBody
//    List<Book> getCollegiums() throws NamingException, SQLException {
//        List<Book> books = new ArrayList<Book>();
//        Context ctx = new InitialContext();
//        DataSource ds = (DataSource)ctx.lookup("oracle_jdbi");
//        Connection con = ds.getConnection();
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery("SELECT * from PHONEBOOK");
//        while (rs.next()) {
//            books.add(Converter.convertToBook(rs));
//        }
////        books.add(new Book(1L, "1", "2", "213",  2L));
//        return books;
//    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody
    List<WireBook> getBooks() {
        List<Phonebook> books = JPAUtil.getObjects(em, Collections.<String, Object>emptyMap(), null, Phonebook.class);
        List<WireBook> wireBooks = new ArrayList<WireBook>();
        for (Phonebook book : books) {
            wireBooks.add(book.toWire());
        }
        return wireBooks;
    }
}
