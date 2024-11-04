package net.rudahee.movies.movie.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rudahee.movies.movie.model.db.Platform;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private UUID id;
    private String name;
    private String shortSynopsis;
    private String synopsis;
    private List<String> buyOn;
    private String imageUrl;
    private List<String> watchOn;
    private Float mediaGlobalRating;

}
