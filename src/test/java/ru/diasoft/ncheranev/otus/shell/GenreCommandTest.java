package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.service.GenreService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreCommand")
class GenreCommandTest {
    private static final String GENRE_NAME = "genre";

    @InjectMocks
    private GenreCommand sut;
    @Mock
    private GenreService genreService;

    @Test
    @DisplayName("должен возвращать форматированный список всех жанров")
    void shouldReturnAllGenresWithFormatting() {
        // given
        when(genreService.getAll()).thenReturn(List.of(
                new Genre().setId(1L).setName("genre 1"),
                new Genre().setId(2L).setName("genre 2")
                ));

        // when
        var actualAllGenres = sut.allGenres();

        // then
        assertThat(actualAllGenres).contains("Genre(id=1, name=genre 1)");
        assertThat(actualAllGenres).contains("Genre(id=2, name=genre 2)");
    }

    @Test
    @DisplayName("должен вызывать сервис добавления жанра")
    void shouldCallServiceMethodAddGenre() {
        // when
        assertThatCode(() -> sut.addGenre(GENRE_NAME))
                .doesNotThrowAnyException();

        // then
        var captorGenre = ArgumentCaptor.forClass(Genre.class);
        verify(genreService).save(captorGenre.capture());
        assertThat(captorGenre.getValue().getName()).isEqualTo(GENRE_NAME);
    }

    @Test
    @DisplayName("должен вызывать сервис удаления жанра по id")
    void shouldCallServiceMethodDeleteGenreById() {
        // when
        assertThatCode(() -> sut.deleteGenreById(1L))
                .doesNotThrowAnyException();

        // then
        verify(genreService).deleteById(1L);
    }
}