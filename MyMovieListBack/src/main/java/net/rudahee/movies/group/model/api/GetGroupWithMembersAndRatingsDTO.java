package net.rudahee.movies.group.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rudahee.movies.movie.model.api.MovieWithGroupMediaRatingDTO;

import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupWithMembersAndRatingsDTO {

    private UUID id;
    private String name;
    private String inviteCode;
    private SortedSet<MovieWithGroupMediaRatingDTO> movies;

}
