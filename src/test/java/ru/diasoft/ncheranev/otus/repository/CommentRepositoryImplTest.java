package ru.diasoft.ncheranev.otus.repository;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(showSql = false)
@Import(CommentRepositoryImpl.class)
@Sql(statements = {
        "insert into genres (id, name) values (10, 'genre')",
        "insert into authors (id, name) values (10, 'author')",
        "insert into books (id, title, genre_id) values (10, 'title', 10)",
        "insert into book_author (book_id, author_id) values (10, 10)",
        "insert into comments (id, text, book_id) values (10, 'comment10', 10)"
})
class CommentRepositoryImplTest {
    @Autowired
    private CommentRepositoryImpl commentRepository;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("должен получить комментарий по идентификатору")
    void shouldGetCommentById() {
        // given
        var expected = tem.find(Comment.class, 10L);

        // when
        var actual = commentRepository.getById(10L);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен выбросить исключение при попытке получить отсутствующий комментарий по идентификатору")
    void shouldThrowExceptionWithGetAbsentCommentById() {
        assertThatThrownBy(() -> commentRepository.getById(20L))
                .isInstanceOf(NoResultException.class)
                .hasMessageStartingWith("No result found");
    }

    @Test
    @DisplayName("должен добавить комментарий к книге")
    void shouldSaveCommentToBook() {
        Comment comment = new Comment();
        comment.setText("text");
        comment.setBook(tem.find(Book.class, 10L));

        var actual = commentRepository.save(comment);

        // then
        assertThat(actual).isEqualTo(tem.find(Comment.class, actual.getId()));
    }

    @Test
    @DisplayName("должен обновить комментарий к книге")
    void shouldUpdateCommentOfBook() {
        Comment comment = new Comment();
        comment.setId(10L);
        comment.setText("text");
        comment.setBook(tem.find(Book.class, 10L));

        var actual = commentRepository.save(comment);

        // then
        assertThat(actual).isEqualTo(tem.find(Comment.class, actual.getId()));
    }

    @Test
    @DisplayName("должен найти все комментарии к книге")
    void shouldFindCommentsByBookId() {
        // given
        var expected = tem.find(Comment.class, 10L);

        // when
        var actual = commentRepository.findByBookId(10L);

        // then
        assertThat(actual).isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("должен удалить комментарий по идентификатору")
    @Transactional
    @Commit
    @Sql(statements = {
            "insert into genres (id, name) values (10, 'genre')",
            "insert into books (id, title, genre_id) values (10, 'title', 10)",
            "insert into comments (id, text, book_id) values (11, 'comment11', 10)"})
    void deleteById() {
        // given
        var comments = tem.find(Comment.class, 11L);
        assertThat(comments).isNotNull();

        // when
        commentRepository.deleteById(11L);

        TestTransaction.end();
        TestTransaction.start();

        // then
        assertThat(tem.find(Comment.class, 11L)).isNull();
    }
}