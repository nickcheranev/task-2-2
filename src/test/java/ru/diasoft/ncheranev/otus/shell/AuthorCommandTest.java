package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.service.AuthorService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс AuthorCommand")
class AuthorCommandTest {
    @InjectMocks
    private AuthorCommand sut;
    @Mock
    private AuthorService authorService;

    @Test
    @DisplayName("должен возвращать форматированный список всех авторов")
    void shouldReturnAllAuthorsWithFormatting() {
        when(authorService.getAll()).thenReturn(List.of(
                new Author().setId(1L).setName("name 1"),
                new Author().setId(2L).setName("name 2")
                ));

        var actualAllAuthors = sut.allAuthors();

        assertThat(actualAllAuthors).isEqualTo(
                "Author(id=1, name=name 1)\n" +
                "Author(id=2, name=name 2)");
    }

    @Test
    @DisplayName("должен вызывать сервис добавления автора")
    void shouldCallServiceMethodAddAuthor() {
        assertThatCode(() -> sut.addAuthor("fio"))
                .doesNotThrowAnyException();

        verify(authorService).save(any());
    }

    @Test
    @DisplayName("должен вызывать сервис удаления автора по id")
    void shouldCallServiceMethodDeleteAuthorById() {
        assertThatCode(() -> sut.deleteAuthorById(1L))
                .doesNotThrowAnyException();

        verify(authorService).deleteById(1L);
    }
}