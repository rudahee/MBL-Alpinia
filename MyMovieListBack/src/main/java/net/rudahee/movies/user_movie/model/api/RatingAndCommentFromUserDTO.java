package net.rudahee.movies.user_movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingAndCommentFromUserDTO {
    private Float rating;
    private String comment;
}
