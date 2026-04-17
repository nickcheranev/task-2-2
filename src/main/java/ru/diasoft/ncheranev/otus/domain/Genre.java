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
 * Жанр книги
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString(of = {"id", "name"})
@Accessors(chain = true)
@Entity
@Table(name = "genres")
@SequenceGenerator(name = "genre_seq", sequenceName = "genres_seq", allocationSize = 1)
public class Genre {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Наименование жанра
     */
    @Column(name = "name", nullable = false)
    private String name;
}
