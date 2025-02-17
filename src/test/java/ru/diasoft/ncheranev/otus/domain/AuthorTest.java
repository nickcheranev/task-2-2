package ru.diasoft.ncheranev.otus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Author")
class AuthorTest {

    @Test
    @DisplayName("должен корректно конвертировать в строку")
    void shouldCorrectlyConvertToString() {
        assertThat(new Author().setId(1L).setName("Имя Фамилия").toString())
                .isEqualTo("Author(id=1, name=Имя Фамилия)");
    }
}