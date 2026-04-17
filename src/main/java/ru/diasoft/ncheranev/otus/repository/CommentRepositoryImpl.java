package ru.diasoft.ncheranev.otus.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.diasoft.ncheranev.otus.domain.Comment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Comment getById(Long commentId) {
        var query = em.createQuery("select c " +
                        "from Comment c " +
                        "where c.id = :id",
                Comment.class);
        query.setParameter("id", commentId);
        return query.getSingleResult();
    }

    @Override
    public List<Comment> findByBookId(Long bookId) {
        var query = em.createQuery("select c " +
                        "from Comment c " +
                        "where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        var query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
