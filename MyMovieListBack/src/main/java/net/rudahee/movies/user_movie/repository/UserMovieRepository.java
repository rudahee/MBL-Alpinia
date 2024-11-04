package net.rudahee.movies.user_movie.repository;

import net.rudahee.movies.user_movie.model.db.UserMovieEntity;
import net.rudahee.movies.user_movie.model.db.UserMoviePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovieEntity, UserMoviePrimaryKey> {

    @Query("SELECT um.rating FROM UserMovieEntity um WHERE um.id.userId = :userId AND um.id.movieId = :movieId")
    Float findRatingFromUserForMovie(@Param("userId") UUID userId, @Param("movieId") UUID movieId);

    @Query("SELECT um.rating FROM UserMovieEntity um WHERE um.movie.id = :movieId")
    List<Float> findRatingsFromMovie(@Param("movieId") UUID movieId);

    @Query("""
    SELECT AVG(um.rating)
        FROM UserMovieEntity um
        JOIN um.user u
        JOIN u.groups g
        WHERE g.id = :groupId
        AND um.movie.id = :movieId
    """)
    Float getAverageRatingForGroup(@Param("groupId") UUID groupId, @Param("movieId") UUID movieId);

    @Query("SELECT MAX(um.rating)" +
            "FROM UserMovieEntity um " +
            "JOIN um.user u " +
            "JOIN u.groups g " +
            "WHERE um.movie.id = :movieId AND g.id = :groupId")
    Float findMaxRatingFromMovieByGroup(@Param("movieId") UUID movieId, @Param("groupId") UUID groupId);

    @Query("SELECT MIN(um.rating)" +
            "FROM UserMovieEntity um " +
            "JOIN um.user u " +
            "JOIN u.groups g " +
            "WHERE um.movie.id = :movieId AND g.id = :groupId")
    Float findMinRatingFromMovieByGroup(@Param("movieId") UUID movieId, @Param("groupId") UUID groupId);

    @Query("""
            SELECT u.username
                FROM UserMovieEntity um
                JOIN um.user u
                JOIN u.groups g
                WHERE um.movie.id = :movieId AND g.id = :groupId
                AND um.rating = (SELECT MAX(um2.rating)
                                 FROM UserMovieEntity um2
                                 JOIN um2.user u2
                                 JOIN u2.groups g2
                                 WHERE um2.movie.id = :movieId AND g2.id = :groupId)
        """)
    String findMaxRatingUsernameFromMovieByGroup(@Param("movieId") UUID movieId, @Param("groupId") UUID groupId);

    @Query("""
            SELECT u.username
                FROM UserMovieEntity um
                JOIN um.user u
                JOIN u.groups g
                WHERE um.movie.id = :movieId AND g.id = :groupId
                AND um.rating = (SELECT MIN(um2.rating)
                                 FROM UserMovieEntity um2
                                 JOIN um2.user u2
                                 JOIN u2.groups g2
                                 WHERE um2.movie.id = :movieId AND g2.id = :groupId)
        """)
    String findMinRatingUsernameFromMovieByGroup(@Param("movieId") UUID movieId, @Param("groupId") UUID groupId);


    @Query("SELECT um FROM UserMovieEntity um " +
            "JOIN um.user u " +
            "JOIN GroupEntity g ON u.id IN (SELECT ug.id FROM g.users ug) " +
            "WHERE um.movie.id = :movieId AND g.id = :groupId")
    List<UserMovieEntity> findRatingsByMovieAndGroup(@Param("movieId") UUID movieId, @Param("groupId") UUID groupId);

    @Query("SELECT AVG(um.rating) FROM UserMovieEntity um " +
            "JOIN um.user u " +
            "JOIN GroupEntity g ON u.id IN (SELECT ug.id FROM g.users ug) " +
            "WHERE um.movie.id = :movieId AND g.id = :groupId")
    Float findAverageRatingByMovieAndGroup(@Param("movieId") UUID movieId, @Param("groupId") UUID groupId);


}
