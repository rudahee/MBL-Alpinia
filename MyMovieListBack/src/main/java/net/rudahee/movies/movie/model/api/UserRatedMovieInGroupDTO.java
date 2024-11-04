package net.rudahee.movies.movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatedMovieInGroupDTO {
    private String id;
    private String name;
    private String rating;
}
