package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Comment;
import ru.diasoft.ncheranev.otus.service.CommentService;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс AuthorCommand")
class CommentCommandTest {
    @InjectMocks
    private CommentCommand sut;
    @Mock
    private CommentService commentService;

    @Test
    @DisplayName("должен возвращать форматированный список комментариев к книге")
    void shouldReturnCommentsForBookWithFormatting() {
        when(commentService.findByBookTitle("title")).thenReturn(List.of(
                new Comment().setId(1L).setText("text 1"),
                new Comment().setId(2L).setText("text 2")
                ));

        var actualAllAuthors = sut.allCommentsByBook("title");

        assertThat(actualAllAuthors).isEqualTo(
                "Comment(id=1, text=text 1)\n" +
                "Comment(id=2, text=text 2)");
    }

    @Test
    @DisplayName("должен вызывать метод добавления комментария")
    void shouldCallServiceMethodAddComment() {
        assertThatCode(() -> sut.addComment("title", "text"))
                .doesNotThrowAnyException();

        verify(commentService).create("title", "text");
    }

    @Test
    @DisplayName("должен вызывать метод обновления комментария")
    void shouldCallServiceMethodUpdateComment() {
        assertThatCode(() -> sut.updateComment(1L, "text"))
                .doesNotThrowAnyException();

        verify(commentService).update(1L, "text");
    }

    @Test
    @DisplayName("должен вызывать метод удаления комментария по id")
    void shouldCallServiceMethodDeleteCommentById() {
        assertThatCode(() -> sut.deleteCommentById(1L))
                .doesNotThrowAnyException();

        verify(commentService).deleteById(1L);
    }

    private static Stream<Arguments> shouldThrowExceptionThisAddCommentWhenTitleOrCommentNotSet_methodSource() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("title", null),
                Arguments.of(null, "comment")
        );
    }

    @ParameterizedTest
    @DisplayName("должен выдать исключение при добавлении, если название книги или комментарий не заданы")
    @MethodSource("shouldThrowExceptionThisAddCommentWhenTitleOrCommentNotSet_methodSource")
    void shouldThrowExceptionWhenWithAddCommentTitleOrCommentNotSet(String title, String comment) {
        assertThatCode(() -> sut.addComment(title, comment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Должны быть заданы название книги и комментарий");
    }

    private static Stream<Arguments> shouldThrowExceptionWithUpdateCommentWhenTitleOrCommentNotSet_methodSource() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(1L, null),
                Arguments.of(null, "comment")
        );
    }

    @ParameterizedTest
    @DisplayName("должен выдать исключение при обновлении, если название книги или комментарий не заданы")
    @MethodSource("shouldThrowExceptionWithUpdateCommentWhenTitleOrCommentNotSet_methodSource")
    void shouldThrowExceptionWithUpdateCommentWhenTitleOrCommentNotSet(Long commentId, String comment) {
        assertThatCode(() -> sut.updateComment(commentId, comment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Должны быть заданы идентификатор и текст комментария");
    }
}