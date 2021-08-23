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
    @Column(name = "showid")
    @Getter private String showId;

    @Column(name = "type")
    @Getter @Setter
    private String type;

    @Column(name = "title")
    @Getter @Setter
    private String title;

    @Column(name = "director")
    @Getter @Setter
    private String director;

    @Column(name = "cast")
    @Getter @Setter
    private String cast;

    @Column(name = "country")
    @Getter @Setter
    private String country;

    @Column(name = "date_added")
    @Getter @Setter
    private String date_added;

    @Column(name = "release_year")
    @Getter @Setter
    private String release_year;

    @Column(name = "rating")
    @Getter @Setter
    private String rating;

    @Column(name = "duration")
    @Getter @Setter
    private String duration;

    @Column(name = "listed_in")
    @Getter @Setter
    private String listed_in;

    @Column(name = "description")
    @Getter @Setter
    private String description;

}
