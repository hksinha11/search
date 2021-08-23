package com.movies.search.repositories;

import com.movies.search.models.Movies;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movies, String> {
}
