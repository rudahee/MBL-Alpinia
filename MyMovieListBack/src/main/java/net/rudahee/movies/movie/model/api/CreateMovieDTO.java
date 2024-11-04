package net.rudahee.movies.movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieDTO {

    private String name;
    private String shortSynopsis;
    private String synopsis;
    private String imageUrl;

    private List<String> buyOn;
    private List<String> watchOn;

}
