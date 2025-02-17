package ru.diasoft.ncheranev.otus.service;

import ru.diasoft.ncheranev.otus.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с книгами
 */
public interface BookService {
    /**
     * Создать запись о книге
     *
     * @param title     название
     * @param authorFio ФИО автора
     * @param genreName жанр
     * @return книга
     */
    Book create(String title, String authorFio, String genreName);

    /**
     * Получить все книги
     *
     * @return список книг
     */
    List<Book> getAll();

    /**
     * Найти книгу по идентификатору
     *
     * @param id идентификатор книги
     * @return книга (Optional)
     */
    Optional<Book> findById(long id);

    /**
     * Найти книгу по наименованию
     *
     * @param title наименование книги
     * @return книга (Optional)
     */
    Optional<Book> findByTitle(String title);

    /**
     * Найти все книги автора
     *
     * @param authorName имя автора
     * @return список книг
     */
    List<Book> getByAuthorName(String authorName);

    /**
     * Найти все книги заданного жанра
     *
     * @param genreName название жанра
     * @return список книг
     */
    List<Book> getByGenreName(String genreName);

    /**
     * Удалить книгу по идентификатору
     *
     * @param id идентификатор книги
     */
    void deleteById(long id);
}
