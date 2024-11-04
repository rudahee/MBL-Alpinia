package net.rudahee.movies.movie.controllers;

import net.rudahee.movies.movie.model.api.SortType;
import net.rudahee.movies.movie.model.api.CreateMovieDTO;
import net.rudahee.movies.movie.model.api.MovieDTO;
import net.rudahee.movies.movie.model.api.ResponseMovieIdDTO;
import net.rudahee.movies.movie.service.MovieService;
import net.rudahee.movies.shared.exceptions.APIException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMovieIdDTO> createMovie(@RequestBody CreateMovieDTO dto) throws APIException {

        ResponseMovieIdDTO responseMovieCreation = movieService.createMovie(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMovieCreation);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> getMovie(@PathVariable UUID id) throws APIException {
        MovieDTO dto = movieService.getMovieById(id);

        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MovieDTO>> getAllMoviesPageable(@RequestParam("page") Integer page,
                                                  @RequestParam("page_size") Integer pageSize,
                                                  @RequestParam(value = "sort", defaultValue = "none") String sort,
                                                  @RequestParam(value = "field", defaultValue = "rating") String field) throws APIException {

        SortType type = SortType.valueOf(sort.toUpperCase());


        Page<MovieDTO> pages = movieService.getAllMovies(page, pageSize, type, field);

        return ResponseEntity.ok(pages);
    }
}
