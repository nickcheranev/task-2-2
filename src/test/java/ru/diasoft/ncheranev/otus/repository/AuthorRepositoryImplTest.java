package ru.diasoft.ncheranev.otus.repository;

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
import ru.diasoft.ncheranev.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(AuthorRepositoryImpl.class)
@Sql(statements = {
        "insert into authors (id, name) values (10, 'author')"
})
class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepositoryImpl authorRepository;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("должен найти автора по имени")
    void shouldFindAuthorByName() {
        // given
        var authorName = "author";

        // when
        var actual = authorRepository.findByName(authorName);

        // then
        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("должен найти всех авторов")
    void getAll() {
        // when
        var actual = authorRepository.getAll();

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("должен добавить нового автора")
    void shouldInsertAuthor() {
        // given
        var newAuthor = new Author()
                .setName("name");

        // when
        var actual = authorRepository.save(newAuthor);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("name");
        var expected = tem.find(Author.class, actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен обновить существующего автора")
    void shouldUpdateAuthor() {
        // given
        var newAuthor = new Author()
                .setId(10L)
                .setName("name1");

        // when
        var actual = authorRepository.save(newAuthor);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("name1");
        var expected = tem.find(Author.class, actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен удалить автора по идентификатору")
    @Transactional
    @Commit
    @Sql(statements = {"insert into authors (id, name) values (11, 'name11')"})
    void deleteById() {
        // given
        var foundAuthor = tem.find(Author.class, 11L);
        assertThat(foundAuthor).isNotNull();

        // when
        authorRepository.deleteById(11L);

        TestTransaction.end();
        TestTransaction.start();

        // then
        assertThat(tem.find(Author.class, 11L)).isNull();
    }
}