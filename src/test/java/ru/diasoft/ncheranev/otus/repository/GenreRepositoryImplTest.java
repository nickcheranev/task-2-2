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
import ru.diasoft.ncheranev.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(GenreRepositoryImpl.class)
@Sql(statements = {
        "insert into genres (id, name) values (10, 'genre')"
})
class GenreRepositoryImplTest {
    @Autowired
    private GenreRepositoryImpl genreRepository;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("должен найти жанр по имени")
    void shouldFindGenresByName() {
        // given
        var genreName = "genre";

        // when
        var actual = genreRepository.findByName(genreName);

        // then
        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("должен найти все жанры")
    void getAll() {
        // when
        var actual = genreRepository.getAll();

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("должен добавить новый жанр")
    void shouldInsertGenre() {
        // given
        var newGenre = new Genre()
                .setName("name");

        // when
        var actual = genreRepository.save(newGenre);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("name");
        var expected = tem.find(Genre.class, actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен обновить существующий жанр")
    void shouldUpdateGenre() {
        // given
        var newGenre = new Genre()
                .setId(10L)
                .setName("name1");

        // when
        var actual = genreRepository.save(newGenre);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("name1");
        var expected = tem.find(Genre.class, actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("должен удалить жанр по идентификатору")
    @Transactional
    @Commit
    @Sql(statements = {"insert into genres (id, name) values (11, 'name11')"})
    void deleteById() {
        // given
        var foundGenre = tem.find(Genre.class, 11L);
        assertThat(foundGenre).isNotNull();

        // when
        genreRepository.deleteById(11L);

        TestTransaction.end();
        TestTransaction.start();

        // then
        assertThat(tem.find(Genre.class, 11L)).isNull();
    }
}