package net.rudahee.movies.user.repository;

import net.rudahee.movies.group.model.db.GroupEntity;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.user.model.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> getByUsername(String username);

    @Query("SELECT g FROM GroupEntity g JOIN g.users u WHERE u.id = :userId")
    List<GroupEntity> findGroupsByUserId(@Param("userId") UUID userId);

    @Query("SELECT m FROM UserEntity u JOIN u.movies um JOIN um.movie m WHERE u.id = :userId")
    List<MovieEntity> findMoviesRatedByUserId(@Param("userId") UUID userId);
}
