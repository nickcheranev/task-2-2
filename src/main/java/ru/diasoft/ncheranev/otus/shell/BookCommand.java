package ru.diasoft.ncheranev.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.service.BookService;

import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Команда Shell "Работа с книгами"
 */
@ShellComponent
@RequiredArgsConstructor
public class BookCommand {

    private final BookService bookService;

    @ShellMethod("Получить список всех книг")
    public String allBooks() {
        return bookService.getAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Получить список всех книг заданного автора")
    public String allBooksByAuthor(@ShellOption String authorName) {
        return bookService.getByAuthorName(authorName)
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Получить список всех книг заданного жанра")
    public String allBooksByGenre(@ShellOption String genre) {
        return bookService.getByGenreName(genre)
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Добавить книгу в хранилище")
    public void addBook(@ShellOption String title, @ShellOption String author, @ShellOption String genre) {
        if (isBlank(title) || isBlank(author) || isBlank(genre)) {
            throw new IllegalArgumentException("Должны быть заданы название книги, автор и жанр");
        }
        bookService.create(title, author, genre);
    }

    @ShellMethod("Удалить книгу из хранилища по id")
    public void deleteBookById(@ShellOption() Long id) {
        bookService.deleteById(id);
    }
}
