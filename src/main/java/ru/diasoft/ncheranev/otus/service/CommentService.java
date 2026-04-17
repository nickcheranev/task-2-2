package ru.diasoft.ncheranev.otus.service;

import ru.diasoft.ncheranev.otus.domain.Comment;

import java.util.List;

public interface CommentService {
    /**
     * Сохранить комментарий к книге в базе данных
     *
     * @param title название книги
     * @param text  комментарий к книге
     * @return комментарий к книге
     */
    Comment create(String title, String text);

    /**
     * Сохранить комментарий к книге в базе данных
     *
     * @param commentId идентификатор комментария
     * @param text  новое значение комментария к книге
     * @return обновленный комментарий к книге
     */
    Comment update(Long commentId, String text);

    /**
     * Получить комментарии к книге по названию книги
     *
     * @param title название книги
     * @return список комментариев
     */
    List<Comment> findByBookTitle(String title);

    /**
     * Удалить комментарий по id
     *
     * @param id id комментария
     */
    void deleteById(Long id);
}
