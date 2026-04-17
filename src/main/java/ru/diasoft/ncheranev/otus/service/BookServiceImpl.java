package ru.diasoft.ncheranev.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.repository.AuthorRepository;
import ru.diasoft.ncheranev.otus.repository.BookRepository;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Реализация BookService
 *
 * @see BookService
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public Book create(String title, String authorName, String genreName) {
        var author = authorRepository.findByName(authorName).orElseThrow();
        var genre = genreRepository.findByName(genreName).orElseThrow();

        if (bookRepository.existsByTitleAndAuthor(title, author.getName())) {
            throw new IllegalArgumentException("Книга '" + title + "' автора '" + author.getName() + "' уже имеется");
        }

        return bookRepository.save(new Book()
                .setTitle(title)
                .setAuthors(Set.of(author))
                .setGenre(genre)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getByAuthorName(String authorName) {
        return bookRepository.getByAuthorName(authorName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getByGenreName(String genreName) {
        return bookRepository.getByGenreName(genreName);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
