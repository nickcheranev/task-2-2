package ru.diasoft.ncheranev.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.service.GenreService;

import java.util.stream.Collectors;

/**
 * Команда Shell "Работа с жанрами"
 */
@ShellComponent
@RequiredArgsConstructor
public class GenreCommand {

    private final GenreService genreService;

    @ShellMethod("Получить список всех жанров")
    public String allGenres() {
        return genreService.getAll()
                .stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Добавить жанр в хранилище")
    public void addGenre(@ShellOption String name) {
        genreService.save(new Genre().setName(name));
    }

    @ShellMethod("Удалить жанр из хранилища по id")
    public void deleteGenreById(@ShellOption Long id) {
        genreService.deleteById(id);
    }
}
