package ru.diasoft.ncheranev.otus.repository;

import ru.diasoft.ncheranev.otus.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий Book
 *
 * @see Book
 */
public interface BookRepository {
    /**
     * Добавить книгу в БД
     *
     * @param book добавляемая книга
     * @return идентификатор добавленной книги
     */
    Book save(Book book);

    /**
     * Найти книгу по идентификатору
     *
     * @param id идентификатор искомой книги
     * @return книга (Optional)
     */
    Optional<Book> findById(long id);

    /**
     * Найти книгу по названию
     *
     * @param title название искомой книги
     * @return книга (Optional)
     */
    Optional<Book> findByTitle(String title);

    /**
     * Получить все книги
     *
     * @return список книг
     */
    List<Book> getAll();

    /**
     * Удалить книгу по идентификатору
     *
     * @param id идентификатор удаляемой книги
     */
    void deleteById(long id);

    /**
     * Получить список книг автора
     *
     * @param authorName ФИО автора
     * @return список книг
     */
    List<Book> getByAuthorName(String authorName);

    /**
     * Получить список книг заданного жанра
     *
     * @param genreName жанр
     * @return список книг
     */
    List<Book> getByGenreName(String genreName);

    boolean existsByTitleAndAuthor(String title, String authorName);
}
