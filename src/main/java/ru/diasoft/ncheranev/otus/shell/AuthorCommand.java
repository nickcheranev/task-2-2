package ru.diasoft.ncheranev.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.service.AuthorService;

import java.util.stream.Collectors;

/**
 * Команда Shell "Работа с авторами"
 */
@ShellComponent
@RequiredArgsConstructor
public class AuthorCommand {

    private final AuthorService authorService;

    @ShellMethod("Получить список всех авторов")
    public String allAuthors() {
        return authorService.getAll()
                .stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Добавить автора в хранилище")
    public void addAuthor(@ShellOption String name) {
        authorService.save(new Author().setName(name));
    }

    @ShellMethod("Удалить автора из хранилища по id")
    public void deleteAuthorById(@ShellOption Long id) {
        authorService.deleteById(id);
    }
}
