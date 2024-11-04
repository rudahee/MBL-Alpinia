package net.rudahee.movies.movie.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rudahee.movies.user_movie.model.db.UserMovieEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String shortSynopsis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String synopsis;

    @ElementCollection(targetClass = Platform.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<Platform> buyOn;

    @Column(nullable = false)
    private String imageUrl;

    @ElementCollection(targetClass = Platform.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<Platform> watchOn;

    private Float mediaGlobalRating;

    @OneToMany(mappedBy = "movie")
    private Set<UserMovieEntity> users;
}
