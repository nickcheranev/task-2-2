package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Comment;
import ru.diasoft.ncheranev.otus.repository.BookRepository;
import ru.diasoft.ncheranev.otus.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс CommentServiceImpl")
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private CommentServiceImpl sut;

    @Test
    @DisplayName("должен создать комментарий к книге")
    void shouldCreateCommentForBook() {
        // given
        var foundBook = new Book();
        when(bookRepository.findByTitle("title")).thenReturn(Optional.of(foundBook));
        var captorComment = ArgumentCaptor.forClass(Comment.class);
        var savedComment = new Comment().setId(1L).setText("text");
        when(commentRepository.save(captorComment.capture())).thenReturn(savedComment);

        // when
        var actual = sut.create("title", "text");

        // then
        assertThat(actual).isEqualTo(savedComment);
        var captorCommentValue = captorComment.getValue();
        assertThat(captorCommentValue.getText()).isEqualTo("text");
    }

    @Test
    @DisplayName("должен выбросить исключение при попытке создать комментарий к отсутствующей книге")
    void shouldThrowExceptionWithCreateCommentForAbsentBook() {
        // when
        assertThatThrownBy(() -> sut.create("title", "text"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book not found");
    }

    @Test
    @DisplayName("должен обновить имеющийся комментарий к книге")
    void update() {
        // given
        final var UPDATE_COMMENT_TEXT = "update comment";
        var foundComment = new Comment().setText("old comment");
        when(commentRepository.getById(1L)).thenReturn(foundComment);
        var captorComment = ArgumentCaptor.forClass(Comment.class);
        var updateComment = new Comment().setId(1L).setText(UPDATE_COMMENT_TEXT);
        when(commentRepository.save(captorComment.capture())).thenReturn(updateComment);

        // when
        var actual = sut.update(1L, UPDATE_COMMENT_TEXT);

        // then
        assertThat(actual).isEqualTo(updateComment);
        var captorCommentValue = captorComment.getValue();
        assertThat(captorCommentValue.getText()).isEqualTo(UPDATE_COMMENT_TEXT);
    }

    @Test
    @DisplayName("должен вернуть имеющиеся комментарии к книге")
    void shouldFindCommentsByBookTitle() {
        // given
        var foundBook = new Book().setId((1L));
        when(bookRepository.findByTitle("title")).thenReturn(Optional.of(foundBook));
        var foundComments = List.of(new Comment().setId(1L).setText("comment1"));
        when(commentRepository.findByBookId(1L)).thenReturn(foundComments);

        // when
        var actual = sut.findByBookTitle("title");

        // then
        assertThat(actual)
                .hasSize(1)
                .first()
                .extracting(Comment::getText).isEqualTo("comment1");
    }

    @Test
    @DisplayName("должен выбросить исключение при попытке вернуть комментарии к отсутствующей книге")
    void shouldThrowExceptionWithFindCommentsByAbsentBook() {
        // when
        assertThatThrownBy(() -> sut.findByBookTitle("title"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book not found");
    }

    @Test
    @DisplayName("должен удалить комментарий к книге по идентификатору комментария")
    void deleteById() {
        // when
        sut.deleteById(1L);

        // then
        verify(commentRepository).deleteById(1L);
    }
}