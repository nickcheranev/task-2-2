package ru.diasoft.ncheranev.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.ncheranev.otus.domain.Comment;
import ru.diasoft.ncheranev.otus.service.CommentService;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Команда Shell "Работа с книгами"
 */
@ShellComponent
@RequiredArgsConstructor
public class CommentCommand {

    private final CommentService commentService;

    @ShellMethod("Получить список всех комментариев к книге")
    public String allCommentsByBook(@ShellOption String title) {
        return commentService.findByBookTitle(title)
                .stream()
                .map(Comment::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Добавить комментарий к книге")
    public void addComment(@ShellOption String title, @ShellOption String text) {
        if (isBlank(title) || isBlank(text)) {
            throw new IllegalArgumentException("Должны быть заданы название книги и комментарий");
        }
        commentService.create(title, text);
    }

    @ShellMethod("Обновить указанный комментарий к книге")
    public void updateComment(@ShellOption Long commentId, @ShellOption String text) {
        if (isNull(commentId) || isBlank(text)) {
            throw new IllegalArgumentException("Должны быть заданы идентификатор и текст комментария");
        }
        commentService.update(commentId, text);
    }

    @ShellMethod("Удалить комментарий книги по id комментария")
    public void deleteCommentById(@ShellOption() Long id) {
        commentService.deleteById(id);
    }
}
