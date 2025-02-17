package ru.diasoft.ncheranev.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.diasoft.ncheranev.otus.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * JPA реализация BookRepository
 *
 * @see BookRepository
 */
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        var query = em.createQuery("select b " +
                        "from Book b " +
                        "where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Book> getAll() {
        var entityGraph = em.getEntityGraph("book-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b left join fetch b.genre", Book.class);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> getByAuthorName(String authorName) {
        var query = em.createQuery("select b " +
                        "from Book b " +
                        "join b.authors a " +
                        "where a.name = :authorName",
                Book.class);
        query.setParameter("authorName", authorName);
        return query.getResultList();
    }

    @Override
    public List<Book> getByGenreName(String genreName) {
        var query = em.createQuery("select b " +
                        "from Book b " +
                        "join b.genre g " +
                        "where g.name = :genreName",
                Book.class);
        query.setParameter("genreName", genreName);
        return query.getResultList();
    }

    @Override
    public boolean existsByTitleAndAuthor(String title, String authorName) {
        var query = em.createQuery("select b " +
                        "from Book b " +
                        "join b.authors a " +
                        "where b.title = :title and a.name = :authorName",
                Book.class);
        query.setParameter("title", title);
        query.setParameter("authorName", authorName);
        return !query.getResultList().isEmpty();
    }
}
