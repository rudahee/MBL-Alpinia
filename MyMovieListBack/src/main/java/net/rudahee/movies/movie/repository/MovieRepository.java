package net.rudahee.movies.movie.repository;

import net.rudahee.movies.movie.model.db.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

    Page<MovieEntity> findAllByOrderByMediaGlobalRatingAsc(Pageable pageable);

    @Query("SELECT m FROM MovieEntity m " +
            "JOIN UserMovieEntity um ON m.id = um.movie.id " +
            "JOIN GroupEntity g ON um.user.id IN (SELECT u.id FROM g.users u) " +
            "WHERE g.id = :groupId " +
            "GROUP BY m.id")
    List<MovieEntity> findAllMoviesWithRatingsForGroup(@Param("groupId") UUID groupId);

}
