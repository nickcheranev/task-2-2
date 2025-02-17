package ru.diasoft.ncheranev.otus.service;

import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author save(Author author);

    void deleteById(Long id);
}
