package com.movies.search.repositories;

import com.movies.search.models.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface MovieRepository extends PagingAndSortingRepository<Movies, String>,
        JpaSpecificationExecutor<Movies>, JpaRepository<Movies, String> {
}
