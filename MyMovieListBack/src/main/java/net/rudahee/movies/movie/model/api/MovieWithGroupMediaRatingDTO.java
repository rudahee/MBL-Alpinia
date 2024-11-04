package net.rudahee.movies.movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rudahee.movies.user_movie.model.api.UserRatingMovieDTO;

import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieWithGroupMediaRatingDTO {
    private UUID id;
    private String name;
    private String imageUrl;
    private List<String> buyOn;
    private List<String> watchOn;
    private Float averageGroupRating;
    private SortedSet<UserRatingMovieDTO> users;
}
