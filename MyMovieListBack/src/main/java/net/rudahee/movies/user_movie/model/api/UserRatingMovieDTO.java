package net.rudahee.movies.user_movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRatingMovieDTO {

    private UUID movieId;
    private String name;
    private UUID userId;
    private Float rating;
    private String comment;
}
