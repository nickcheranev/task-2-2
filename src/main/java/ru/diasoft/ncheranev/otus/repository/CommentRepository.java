package ru.diasoft.ncheranev.otus.repository;

import ru.diasoft.ncheranev.otus.domain.Comment;

import java.util.List;

/**
 * Репозиторий комментариев к книге
 */
public interface CommentRepository {
    /**
     * Сохранить комментарий к книге в базе данных
     *
     * @param comment комментарий к книге
     * @return сохраненный комментарий
     */
    Comment save(Comment comment);

    /**
     * Получить комментарии к книге по id комментария
     * Если комментарий не найден, то выбросить исключения
     *
     * @param commentId идентификатор комментария
     * @return комментарий
     */
    Comment getById(Long commentId);

    /**
     * Получить комментарии к книге по id книги
     *
     * @param bookId идентификатор книги
     * @return список комментариев
     */
    List<Comment> findByBookId(Long bookId);

    /**
     * Удалить комментарий по id
     *
     * @param id id комментария
     */
    void deleteById(Long id);
}
