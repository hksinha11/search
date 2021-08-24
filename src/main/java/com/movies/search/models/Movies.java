package com.movies.search.models;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movies {
    // Column names : showid, type, title, director, cast, country
    // date added, release_year, duration, listed_in, description
    @Id
    @Column(name = "show_id")
    private String showid;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "cast", columnDefinition="VARCHAR(6000)")
    private String cast;

    @Column(name = "country")
    private String country;

    @Column(name = "date_added")
    private String dateAdded;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "rating")
    private String rating;

    @Column(name = "duration")
    private String duration;

    @Column(name = "listed_in")
    private String listedIn;

    @Column(name = "description", columnDefinition="VARCHAR(9000)")
    private String description;

}
