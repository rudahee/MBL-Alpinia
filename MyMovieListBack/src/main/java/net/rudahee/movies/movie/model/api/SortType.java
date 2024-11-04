package net.rudahee.movies.movie.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SortType {
    ASC, DESC, NONE
}
