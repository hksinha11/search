package com.movies.search.services;

import com.movies.search.helper.CSVHelper;
import com.movies.search.models.Movies;
import com.movies.search.models.utils.PagingHeaders;
import com.movies.search.models.utils.PagingResponse;
import com.movies.search.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Service
public class MovieService {
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

    public Movies get(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can not find the entity car (%s = %s).", "id", id.toString())));
    }


    public PagingResponse get(Specification<Movies> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Movies> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }


    public List<Movies> get(Specification<Movies> spec, Sort sort) {
        return movieRepository.findAll(spec, sort);
    }

    public PagingResponse get(Specification<Movies> spec, Pageable pageable) {
        Page<Movies> page = movieRepository.findAll(spec, pageable);
        List<Movies> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

}
