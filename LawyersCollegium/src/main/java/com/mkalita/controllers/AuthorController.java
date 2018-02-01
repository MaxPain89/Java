package com.mkalita.controllers;

import com.mkalita.jpa.Author;
import com.mkalita.webserver.exceptions.ConflictException;
import com.mkalita.webserver.exceptions.InternalServerError;
import com.mkalita.webserver.exceptions.InvalidInputDataException;
import com.mkalita.webserver.exceptions.NotFoundException;
import com.mkalita.wire.WireAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuthorController {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
    private static final String GET_REQUEST = "SELECT Составил, Телефон FROM Составил ORDER BY Составил";
    private static final String INSERT_REQUEST = "INSERT INTO Составил VALUES ('%s', '%s')";
    private static final String UPDATE_REQUEST = "UPDATE Составил SET Составил='%s', Телефон='%s' WHERE Составил='%s' AND Телефон='%s'";
    private static final String DELETE_REQUEST = "DELETE from Составил WHERE Составил='%s' AND Телефон='%s'";
    private EntityManager em;
    public AuthorController(EntityManager em) {
        this.em = em;
    }

    public List<WireAuthor> getAuthors() {
        return _getAuthors().stream().map(Author::toWire).collect(Collectors.toList());
    }

    public Author _getAuthor(int id) {
        List<Author> authors = _getAuthors();
        if (authors.size() >= id) {
            return authors.get(id - 1);
        } else {
            throw new NotFoundException("Can't find this author");
        }
    }

    public WireAuthor getAuthor(int id) {
        return _getAuthor(id).toWire();
    }

    public List<Author> _getAuthors() {
        List<Author> authors = new ArrayList<>();
        List<Object[]> authorsFromDb = (List<Object[]>)em.createNativeQuery(GET_REQUEST).getResultList();
        authorsFromDb.forEach(item -> {
            authors.add(new Author(authors.size() + 1, (String)item[0], (String)item[1]));
        });
        return authors;
    }

    private Author _findAuthor(Author author) {
        for (Author currAuthor : _getAuthors()) {
            if (currAuthor.equals(author)) {
                return currAuthor;
            }
        }
        return null;
    }

    public WireAuthor addAuthor(WireAuthor wireAuthor) {
        _checkAuthor(wireAuthor);
        Author author = new Author(wireAuthor);
        if (_findAuthor(author) != null) {
            throw new ConflictException("Author with specified data already exist");
        }
        _execRequest(String.format(INSERT_REQUEST, author.getName(), author.getPhone()));
        author = _findAuthor(author);
        if (author == null) {
            throw new InternalServerError("New author wasn't added see logs");
        }
        return author.toWire();
    }

    public WireAuthor updateAuthor(int id, WireAuthor wireAuthor) {
        _checkAuthor(wireAuthor);
        Author author = _getAuthor(id);
        Author updatedAuthor = new Author(wireAuthor);
        _execRequest(String.format(UPDATE_REQUEST, updatedAuthor.getName(), updatedAuthor.getPhone(), author.getName(), author.getPhone()));
        author = _findAuthor(updatedAuthor);
        if (author == null) {
            throw new InternalServerError("Author wasn't updated see logs");
        }
        return author.toWire();
    }

    public WireAuthor deleteAuthor(int id) {
        Author author = _getAuthor(id);
        _execRequest(String.format(DELETE_REQUEST, author.getName(), author.getPhone()));
        Author newAuthor = _findAuthor(author);
        if (newAuthor != null) {
            throw new InternalServerError("Author wasn't updated see logs");
        }
        return author.toWire();
    }


    private void _checkAuthor(WireAuthor wireAuthor) {
        String name = wireAuthor.getName();
        String phone = wireAuthor.getPhone();
        if (name != null && name.length() > 50) {
            throw new InvalidInputDataException("Name should be less than 50 chars");
        }
        if (phone != null && phone.length() > 10) {
            throw new InvalidInputDataException("Phone should be less than 10 chars");
        }
    }

    private void _execRequest(String request) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery(request).executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}
