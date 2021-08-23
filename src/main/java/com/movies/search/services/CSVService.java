package com.movies.search.services;

import com.movies.search.helper.CSVHelper;
import com.movies.search.models.Movies;
import com.movies.search.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class CSVService {
    @Autowired
    MovieRepository movieRepository;

    public void save(MultipartFile file) {
        try {

            List<Movies> movies = CSVHelper.csvToMovies(file.getInputStream());
            movieRepository.saveAll(movies);


        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Movies> getAllMovies() {
        return movieRepository.findAll();
    }
}
