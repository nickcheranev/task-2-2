package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.repository.AuthorRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс AuthorServiceImpl")
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl sut;

    @Test
    @DisplayName("должен получать всех авторов")
    void shouldGetAllAuthors() {
        // given
        var foundAuthors = new ArrayList<Author>();
        when(authorRepository.getAll()).thenReturn(foundAuthors);
        
        // when
        var actual = sut.getAll();

        // then
        assertThat(actual).isEqualTo(foundAuthors);
        verify(authorRepository).getAll();
    }

    @Test
    @DisplayName("должен сохранять автора")
    void shouldSaveAuthor() {
        // given
        var author = new Author();
        when(authorRepository.save(author)).thenReturn(author);

        // when
        var actual = sut.save(author);

        // then
        assertThat(actual).isEqualTo(author);
        verify(authorRepository).save(author);
    }

    @Test
    @DisplayName("должен удалять автора по идентификатору")
    void shouldDeleteAuthorById() {
        // given
        var id = 1L;

        // when
        sut.deleteById(id);

        // then
        verify(authorRepository).deleteById(id);
    }
}