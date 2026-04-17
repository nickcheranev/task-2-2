package ru.diasoft.ncheranev.otus.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

/**
 * Книга
 */
@Getter
@Setter
@ToString(of = {"id", "title"})
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "books")
@SequenceGenerator(name = "book_seq", sequenceName = "books_seq", allocationSize = 1)
@NamedEntityGraph(name = "book-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("genre")})
public class Book {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Название
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Автор
     */
    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    /**
     * Жанр
     */
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    /**
     * Комментарии
     */
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private Set<Comment> comments;
}
