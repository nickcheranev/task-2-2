package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.repository.AuthorRepository;
import ru.diasoft.ncheranev.otus.repository.BookRepository;
import ru.diasoft.ncheranev.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс AuthorServiceImpl")
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private BookServiceImpl sut;

    @Test
    @DisplayName("должен получать все книги")
    void shouldGetAllBooks() {
        // given
        var foundBooks = new ArrayList<Book>();
        when(bookRepository.getAll()).thenReturn(foundBooks);

        // when
        var actual = sut.getAll();

        // then
        assertThat(actual).isEqualTo(foundBooks);
        verify(bookRepository).getAll();
    }

    @Test
    @DisplayName("должен создать книгу с автором и жанром")
    void shouldCreateBook() {
        // given
        var authorName = "author";
        var author = new Author().setName(authorName);
        var genreName = "genre";
        var genre = new Genre().setName(genreName);
        when(authorRepository.findByName("author")).thenReturn(Optional.of(author));
        when(genreRepository.findByName("genre")).thenReturn(Optional.of(genre));
        var captorBook = ArgumentCaptor.forClass(Book.class);
        var savedBook = new Book();
        when(bookRepository.save(captorBook.capture())).thenReturn(savedBook);

        // when
        var actual = sut.create("title", authorName, genreName);

        // then
        assertThat(actual).isEqualTo(savedBook);
        var bookForSave = captorBook.getValue();
        assertThat(bookForSave.getTitle()).isEqualTo("title");
        assertThat(bookForSave.getGenre().getName()).isEqualTo("genre");
        assertThat(bookForSave.getAuthors().stream().findFirst().orElseThrow().getName()).isEqualTo("author");
    }

    @Test
    @DisplayName("должен выбросить исключение, если книга такого названия и автора уже присутствует")
    void shouldThrowExceptionWithExistsByTitleAndAuthorOnCreateBook() {
        // given
        var authorName = "author";
        var author = new Author().setName(authorName);
        var genreName = "genre";
        var genre = new Genre().setName(genreName);
        when(authorRepository.findByName("author")).thenReturn(Optional.of(author));
        when(genreRepository.findByName("genre")).thenReturn(Optional.of(genre));
        when(bookRepository.existsByTitleAndAuthor("title", authorName)).thenReturn(true);

        // when
        assertThatThrownBy(() -> sut.create("title", authorName, genreName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Книга 'title' автора 'author' уже имеется");
    }

    @Test
    @DisplayName("должен найти книгу по идентификатору")
    void shouldFindBookById() {
        // given
        var id = 1L;
        var foundBook = Optional.of(new Book());
        when(bookRepository.findById(id)).thenReturn(foundBook);

        // when
        var actual = sut.findById(id);

        // then
        assertThat(actual).isEqualTo(foundBook);
        verify(bookRepository).findById(id);
    }

    @Test
    @DisplayName("должен найти книгу по наименованию")
    void shouldFindBookByTitle() {
        // given
        var title = "title";
        var foundBook = Optional.of(new Book());
        when(bookRepository.findByTitle(title)).thenReturn(foundBook);

        // when
        var actual = sut.findByTitle(title);

        // then
        assertThat(actual).isEqualTo(foundBook);
        verify(bookRepository).findByTitle(title);
    }

    @Test
    @DisplayName("должен найти книги заданного автора")
    void getByAuthorName() {
        // given
        var authorName = "author";
        var foundBooks = List.of(new Book());
        when(bookRepository.getByAuthorName(authorName)).thenReturn(foundBooks);

        // when
        var actual = sut.getByAuthorName(authorName);

        // then
        assertThat(actual).isEqualTo(foundBooks);
        verify(bookRepository).getByAuthorName(authorName);
    }

    @Test
    @DisplayName("должен найти книги заданного жанра")
    void getByGenreName() {
        // given
        var genreName = "genre";
        var foundBooks = List.of(new Book());
        when(bookRepository.getByGenreName(genreName)).thenReturn(foundBooks);

        // when
        var actual = sut.getByGenreName(genreName);

        // then
        assertThat(actual).isEqualTo(foundBooks);
        verify(bookRepository).getByGenreName(genreName);
    }

    @Test
    @DisplayName("должен удалить книгу по идентификатору")
    void deleteById() {
        // given
        var id = 1L;

        // when
        sut.deleteById(id);

        // then
        verify(bookRepository).deleteById(id);
    }
}