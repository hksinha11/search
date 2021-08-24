package com.movies.search.controllers;

import com.movies.search.exception.UnsupportedColumnException;
import com.movies.search.helper.CSVHelper;
import com.movies.search.message.ResponseMessage;
import com.movies.search.models.Movies;
import com.movies.search.models.utils.PagingHeaders;
import com.movies.search.models.utils.PagingResponse;
import com.movies.search.services.MovieService;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/")
@Slf4j
public class MoviesController {
    @Autowired
    MovieService movieService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        log.info("Uploading file {}", file.getOriginalFilename());
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                movieService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
            } catch (Exception e) {
                log.error("Exception while uploading the file ", e);
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        log.info("Please Upload the correct file {}", file.getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movies>> getAllMovies() {
        log.info("Returning all the movies uploaded by the file");
        try {
            List<Movies> movies = movieService.getAllMovies();

            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while displaying all the movies data", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search API with AND condition to filter out movie results.
    @Transactional
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Movies>> get(
            @And({
                    @Spec(path = "showid", params = "showid", spec = Equal.class),
                    @Spec(path = "type", params = "type", spec = LikeIgnoreCase.class),
                    @Spec(path = "title", params = "title", spec = LikeIgnoreCase.class),
                    @Spec(path = "director", params = "director", spec = LikeIgnoreCase.class),
                    @Spec(path = "cast", params = "cast", spec = LikeIgnoreCase.class),
                    @Spec(path = "country", params = "country", spec = LikeIgnoreCase.class),
                    @Spec(path = "date_added", params = "date_added", spec = LikeIgnoreCase.class),
                    @Spec(path = "release_year", params = "release_year", spec = LikeIgnoreCase.class),
                    @Spec(path = "duration", params = "duration", spec = LikeIgnoreCase.class),
                    @Spec(path = "listed_in", params = "listed_in", spec = LikeIgnoreCase.class),
                    @Spec(path = "description", params = "description", spec = LikeIgnoreCase.class)

            }) Specification<Movies> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) throws UnsupportedColumnException {
        if (Objects.isNull(spec)) {
            throw new UnsupportedColumnException("Unsupported column name");
        }
        log.info("The search with various parameters and the and close {} ", spec.toString());
        final PagingResponse response = movieService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        log.info("Pagination headers set {}", response.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }



}
