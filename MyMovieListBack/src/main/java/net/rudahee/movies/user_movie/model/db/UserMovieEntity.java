package net.rudahee.movies.user_movie.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.user.model.db.UserEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMovieEntity {

    @EmbeddedId
    private UserMoviePrimaryKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @Column(nullable = false)
    private Float rating;

    private String comment;
}
