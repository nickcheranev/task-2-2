package ru.diasoft.ncheranev.otus.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Книга
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString(of = {"id", "text"})
@Accessors(chain = true)
@Entity
@Table(name = "comments")
@SequenceGenerator(name = "comment_seq", sequenceName = "comments_seq", allocationSize = 1)
public class Comment {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Текст
     */
    @Column(name = "text", nullable = false)
    private String text;
    /**
     * Книга
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
