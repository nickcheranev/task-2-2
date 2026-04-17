package ru.diasoft.ncheranev.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.domain.Comment;
import ru.diasoft.ncheranev.otus.repository.BookRepository;
import ru.diasoft.ncheranev.otus.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    public static final String BOOK_NOT_FOUND_MESSAGE = "Book not found";
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Comment create(String title, String text) {
        var optFoundBook = bookRepository.findByTitle(title);
        if (optFoundBook.isPresent()) {
            var foundBook = optFoundBook.get();
            var comment = new Comment().setText(text).setBook(foundBook);
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException(BOOK_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public Comment update(Long commentId, String text) {
        var foundComment = commentRepository.getById(commentId);
        return commentRepository.save(foundComment.setText(text));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookTitle(String title) {
        var foundBook = bookRepository.findByTitle(title);
        if (foundBook.isPresent()) {
            return commentRepository.findByBookId(foundBook.get().getId());
        } else {
            throw new IllegalArgumentException(BOOK_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
