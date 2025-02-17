package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.repository.GenreRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreServiceImpl")
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreServiceImpl sut;

    @Test
    @DisplayName("должен получать все жанры")
    void shouldGetAllGenres() {
        // given
        var foundGenres = new ArrayList<Genre>();
        when(genreRepository.getAll()).thenReturn(foundGenres);
        
        // when
        var actual = sut.getAll();

        // then
        assertThat(actual).isEqualTo(foundGenres);
        verify(genreRepository).getAll();
    }

    @Test
    @DisplayName("должен сохранять жанр")
    void shouldSaveGenre() {
        // given
        var genre = new Genre();
        when(genreRepository.save(genre)).thenReturn(genre);

        // when
        var actual = sut.save(genre);

        // then
        assertThat(actual).isEqualTo(genre);
        verify(genreRepository).save(genre);
    }

    @Test
    @DisplayName("должен удалять жанр по идентификатору")
    void shouldDeleteGenreById() {
        // given
        var id = 1L;

        // when
        sut.deleteById(id);

        // then
        verify(genreRepository).deleteById(id);
    }
}