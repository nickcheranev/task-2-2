package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс BookCommand")
class BookCommandTest {
    @InjectMocks
    private BookCommand sut;
    @Mock
    private BookService bookService;

    @Test
    @DisplayName("должен возвращать форматированный список всех книг")
    void shouldReturnAllBooksWithFormatting() {
        when(bookService.getAll()).thenReturn(List.of(
                new Book()
                        .setId(1L)
                        .setTitle("title 1")
                        .setAuthors(Set.of(new Author().setId(11L).setName("fio 1")))
                        .setGenre(new Genre().setId(111L).setName("genre 1")),
                new Book()
                        .setId(2L)
                        .setTitle("title 2")
                        .setAuthors(Set.of(new Author().setId(22L).setName("fio 2")))
                        .setGenre(new Genre().setId(222L).setName("genre 2"))
                ));

        var actualAllAuthors = sut.allBooks();

        assertThat(actualAllAuthors).isEqualTo("Book(id=1, title=title 1)\n" +
                "Book(id=2, title=title 2)");
    }

    @Test
    @DisplayName("должен вызывать сервис добавления книги")
    void shouldCallServiceMethodAddBook() {
        assertThatCode(() -> sut.addBook("title", "author", "genre"))
                .doesNotThrowAnyException();

        verify(bookService).create("title", "author", "genre");
    }

    private static Stream<Arguments> shouldThrowExceptionWhenTitleOrAuthorOrGenreNotSet_methodSource() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of("title", null, null),
                Arguments.of("title", "author", null),
                Arguments.of(null, "author", null),
                Arguments.of(null, null, "genre"),
                Arguments.of(null, "author", "genre")
        );
    }

    @ParameterizedTest
    @MethodSource("shouldThrowExceptionWhenTitleOrAuthorOrGenreNotSet_methodSource")
    @DisplayName("должен выдать исключение, если название книги, автор или жанр не заданы")
    void shouldThrowExceptionWhenTitleOrAuthorOrGenreNotSet(String title, String authorFio, String genreName) {
        assertThatCode(() -> sut.addBook(title, authorFio, genreName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Должны быть заданы название книги, автор и жанр");
    }

    @Test
    @DisplayName("должен вызывать сервис удаления книги по id")
    void shouldCallServiceMethodDeleteBookById() {
        assertThatCode(() -> sut.deleteBookById(1L))
                .doesNotThrowAnyException();

        verify(bookService).deleteById(1L);
    }

    @Test
    @DisplayName("должен возвращать книги заданного автора")
    void shouldReturnBooksOfAuthor() {
        when(bookService.getByAuthorName("author")).thenReturn(List.of(
                new Book()
                        .setId(1L)
                        .setTitle("title 1")
                        .setAuthors(Set.of(new Author().setId(11L).setName("author")))
                        .setGenre(new Genre().setId(111L).setName("genre 1")),
                new Book()
                        .setId(2L)
                        .setTitle("title 2")
                        .setAuthors(Set.of(new Author().setId(22L).setName("author")))
                        .setGenre(new Genre().setId(222L).setName("genre 2"))
        ));

        var actualAllAuthors = sut.allBooksByAuthor("author");

        assertThat(actualAllAuthors).isEqualTo("Book(id=1, title=title 1)\n" +
                "Book(id=2, title=title 2)");
    }

    @Test
    @DisplayName("должен возвращать книги заданного жанра")
    void shouldReturnBooksOfGenre() {
        when(bookService.getByGenreName("genre")).thenReturn(List.of(
                new Book()
                        .setId(1L)
                        .setTitle("title 1")
                        .setAuthors(Set.of(new Author().setId(11L).setName("fio 1")))
                        .setGenre(new Genre().setId(111L).setName("genre")),
                new Book()
                        .setId(2L)
                        .setTitle("title 2")
                        .setAuthors(Set.of(new Author().setId(22L).setName("fio 2")))
                        .setGenre(new Genre().setId(222L).setName("genre"))
        ));

        var actualAllAuthors = sut.allBooksByGenre("genre");

        assertThat(actualAllAuthors).isEqualTo("Book(id=1, title=title 1)\n" +
                "Book(id=2, title=title 2)");
    }
}