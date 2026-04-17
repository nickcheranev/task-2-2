package ru.diasoft.ncheranev.otus.service;

import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre save(Genre genre);

    void deleteById(Long id);
}
