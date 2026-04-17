package ru.diasoft.ncheranev.otus.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(BookRepositoryImpl.class)
class BookCommentsBatchSizeTest {
    @Autowired
    private BookRepositoryImpl bookRepository;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("должен найти все комментарии к книгам с оптимизацией запросов @BatchSize(size = 2)")
    @Transactional
    @Sql(statements = {
            "insert into genres (id, name) values (10, 'genre')",
            "insert into authors (id, name) values (10, 'author')",
            "insert into books (id, title, genre_id) values (10, 'title10', 10)",
            "insert into books (id, title, genre_id) values (11, 'title11', 10)",
            "insert into books (id, title, genre_id) values (12, 'title10', 10)",
            "insert into comments (id, text, book_id) values (10, 'comment10', 10)",
            "insert into comments (id, text, book_id) values (11, 'comment11', 11)",
            "insert into comments (id, text, book_id) values (12, 'comment11', 12)"
    })
    void checkBatchSizeOptimizationWithGetAll() {
        var sessionFactory = tem.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        // Включаем статистику для подсчета количества запросов
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        // when
        var actual = bookRepository.getAll();

        // then
        // Извлекаем ленивую коллекцию комментариев к книге
        actual.forEach(book -> {
            assertThat(book.getComments()).hasSize(1);
        });
        // Количество запросов должно быть 2:
        // 1 шт. - для книги
        // 1 шт. - для комментариев
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(2);
    }
}