package ru.diasoft.ncheranev.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Comment;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(BookRepositoryImpl.class)
@Sql(statements = {
        "insert into genres (id, name) values (10, 'genre')",
        "insert into authors (id, name) values (10, 'author')",
        "insert into books (id, title, genre_id) values (10, 'title', 10)",
        "insert into book_author (book_id, author_id) values (10, 10)"
        })
class BookRepositoryImplTest {
    @Autowired
    private BookRepositoryImpl bookRepository;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("должен сохранить новую книгу")
    void shouldInsertBook() {
        // given
        var newBook = new Book()
                .setTitle("title")
                .setGenre(tem.find(Genre.class, 10))
                .setAuthors(Set.of(tem.find(Author.class, 10)))
                .setComments(Set.of(new Comment().setText("comment")));

        // when
        var actual = bookRepository.save(newBook);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getTitle()).isEqualTo("title");
        assertThat(actual.getGenre().getName()).isEqualTo("genre");
        assertThat(actual.getAuthors()).hasSize(1);
        assertThat(actual.getComments()).hasSize(1);
        var expected = tem.find(Book.class, actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен обновить имеющуюся книгу")
    void shouldUpdateBook() {
        // given
        var newBook = new Book()
                .setId(10L)
                .setTitle("title1");

        // when
        var actual = bookRepository.save(newBook);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getTitle()).isEqualTo("title1");
        var expected = tem.find(Book.class, actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен найти книгу по идентификатору")
    void shouldFindBookById() {
        // given
        var id = 10L;

        // when
        var actual = bookRepository.findById(id);

        // then
        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("должен найти книгу по названию")
    void shouldFindBookByTitle() {
        // when
        var actual = bookRepository.findByTitle("title");

        // then
        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("должен найти все книги и загрузить жанр")
    void shouldGetAllBooks() {
        // when
        var actual = bookRepository.getAll();

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getGenre()).isNotNull();
    }

    @Test
    @DisplayName("должен найти все книги заданного автора")
    void shouldGetBooksByAuthorName() {
        // when
        var actual = bookRepository.getByAuthorName("author");

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("должен найти все книги заданного жанра")
    void shouldGetBooksByGenreName() {
        // when
        var actual = bookRepository.getByGenreName("genre");

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("должен проверить существование книги по названию и автору")
    void existsByTitleAndAuthor() {
        // when
        var actual = bookRepository.existsByTitleAndAuthor("title", "author");

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("должен удалить книгу по идентификатору")
    @Transactional
    @Commit
    @Sql(statements = {"insert into books (id, title) values (11, 'title')"})
    void deleteById() {
        // given
        var foundBook = tem.find(Book.class, 11L);
        assertThat(foundBook).isNotNull();

        // when
        bookRepository.deleteById(11L);

        TestTransaction.end();
        TestTransaction.start();

        // then
        assertThat(tem.find(Book.class, 11L)).isNull();
    }

    private static Stream<Arguments> shouldCheckExistsBookByTitleAndAuthorMethodSource() {
        return Stream.of(
                Arguments.of("title", "author", true),
                Arguments.of("title1", "author1", false)
        );
    }

    @ParameterizedTest
    @DisplayName("должен проверить наличие книги по названию и автору")
    @MethodSource("shouldCheckExistsBookByTitleAndAuthorMethodSource")
    void shouldCheckExistsBookByTitleAndAuthor(String title, String author, boolean expected) {
        // when
        var actual = bookRepository.existsByTitleAndAuthor(title, author);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}