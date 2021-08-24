package com.movies.search.models.utils;

import com.movies.search.models.Movies;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagingResponse {
    private Long count;
    private Long pageNumber;
    private Long pageSize;
    private Long pageOffset;
    private Long pageTotal;
    private List<Movies> elements;
}