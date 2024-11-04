package net.rudahee.movies.user_movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieTableDTO {

    private String name;
    private String imageUrl;
    private String shortSynopsis;
    private List<String> watchOn;
    private List<String> buyOn;
    private String id;
    private Float mediaRating;
}
