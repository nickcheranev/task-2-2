package ru.diasoft.ncheranev.otus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Book")
class BookTest {

    @Test
    @DisplayName("должен корректно преобразовывать в строку")
    void shouldCorrectConvertToString() {
        var actualBookStringValue = new Book()
                .setId(1L)
                .setTitle("title")
                .setAuthors(Set.of(new Author().setId(2L).setName("fio")))
                .setGenre(new Genre().setId(3L).setName("name"))
                .toString();

        assertThat(actualBookStringValue)
                .isEqualTo("Book(id=1, title=title)");
    }
}