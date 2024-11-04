package net.rudahee.movies.user_movie.controllers;

import net.rudahee.movies.movie.model.api.MovieDTO;
import net.rudahee.movies.movie.service.MovieService;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user_movie.model.api.GroupMaxMinRatingDTO;
import net.rudahee.movies.user_movie.model.api.RatingAndCommentFromUserDTO;
import net.rudahee.movies.user_movie.model.api.UserRatingMovieDTO;
import net.rudahee.movies.user_movie.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> createRatingMovie(@RequestBody UserRatingMovieDTO dtoIn) throws APIException {

        MovieDTO dtoOut = ratingService.userRatingMovie(dtoIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoOut);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingAndCommentFromUserDTO> getRatingMovie(@RequestParam UUID userId, @RequestParam UUID movieId) throws APIException {

        RatingAndCommentFromUserDTO dtoOut = ratingService.getRatingAndComment(userId, movieId);
        return ResponseEntity.ok(dtoOut);
    }

    @GetMapping(value = "/group")
    public ResponseEntity<List<GroupMaxMinRatingDTO>> getMaxRatingGroup(@RequestParam UUID movieId, @RequestParam UUID userId) {
        List<GroupMaxMinRatingDTO> dtos = ratingService.getMaxAndMinRating(movieId, userId);
        return ResponseEntity.ok(dtos);
    }
}
