package net.rudahee.movies.group.repository;

import net.rudahee.movies.group.model.db.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

    @Query("SELECT u.username FROM GroupEntity g JOIN g.users u WHERE g.id = :groupId")
    List<String> findUsernamesByGroupId(@Param("groupId") UUID groupId);


    @Query("SELECT g FROM GroupEntity g JOIN g.users u WHERE u.id = :userId")
    List<GroupEntity> findGroupsByUserId(@Param("userId") UUID userId);

    Optional<GroupEntity> findByInviteCode(String inviteCode);

    @Query("SELECT g FROM GroupEntity g JOIN FETCH g.users WHERE g.id = :groupId")
    Optional<GroupEntity> findGroupWithUsers(@Param("groupId") UUID groupId);

    @Query("SELECT g.name FROM GroupEntity g WHERE g.id = :groupId")
    String findNameById(@Param("groupId") UUID groupId);
}
