package ru.diasoft.ncheranev.otus.repository;

import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    /**
     * Получить жанр по имени
     *
     * @param name наименование жанра
     * @return жанр (Optional)
     */
    Optional<Genre> findByName(String name);

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    List<Genre> getAll();

    /**
     * Сохранить жанр в базе данных
     *
     * @param genre жанр
     * @return сохраненный жанр
     */
    Genre save(Genre genre);

    /**
     * Удалить жанр по id
     *
     * @param id id жанра
     */
    void deleteById(Long id);
}
