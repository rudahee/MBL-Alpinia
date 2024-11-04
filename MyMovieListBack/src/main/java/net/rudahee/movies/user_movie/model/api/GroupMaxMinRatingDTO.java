package net.rudahee.movies.user_movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMaxMinRatingDTO {
    private String name;
    private Float maxRating;
    private String maxRatingUsername;
    private Float minRating;
    private String minRatingUsername;
    private Float avgRating;
}
