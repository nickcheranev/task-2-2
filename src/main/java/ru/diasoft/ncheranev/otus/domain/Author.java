package ru.diasoft.ncheranev.otus.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Автор книги
 */
@Getter
@Setter
@ToString(of = {"id", "name"})
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "authors")
@SequenceGenerator(name = "author_seq", sequenceName = "authors_seq", allocationSize = 1)
public class Author {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Фамилия Имя (Отчество) автора
     */
    private String name;
}
