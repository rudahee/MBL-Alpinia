package net.rudahee.movies.user_movie.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMoviePrimaryKey implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "movie_id")
    private UUID movieId;


}
