package ru.diasoft.ncheranev.otus.repository;

import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий авторов
 */
public interface AuthorRepository {
    /**
     * Получить автора по имени
     *
     * @param name имя автора
     * @return автор (Optional)
     */
    Optional<Author> findByName(String name);

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<Author> getAll();

    /**
     * Сохранить автора в базе данных
     *
     * @param author автор
     * @return сохраненный автор
     */
    Author save(Author author);

    /**
     * Удалить автора по id
     *
     * @param id id автора
     */
    void deleteById(Long id);
}
