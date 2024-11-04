package net.rudahee.movies.group.service;

import static org.junit.jupiter.api.Assertions.*;

import net.rudahee.movies.group.model.api.*;
import net.rudahee.movies.group.model.db.GroupEntity;
import net.rudahee.movies.group.repository.GroupRepository;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.movie.model.db.Platform;
import net.rudahee.movies.movie.repository.MovieRepository;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.model.db.UserEntity;
import net.rudahee.movies.user.repository.UserRepository;
import net.rudahee.movies.user_movie.model.db.UserMovieEntity;
import net.rudahee.movies.user_movie.repository.UserMovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    private GroupService groupService;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private UserMovieRepository userMovieRepository;
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        groupRepository = mock(GroupRepository.class);
        userRepository = mock(UserRepository.class);
        userMovieRepository = mock(UserMovieRepository.class);
        movieRepository = mock(MovieRepository.class);

        groupService = new GroupService(groupRepository, userRepository, userMovieRepository, movieRepository);
    }

    @Test
    void whenGroupExistsThenReturnGroupMembers() throws APIException {
        UUID groupId = UUID.randomUUID();
        GroupEntity group = new GroupEntity();
        group.setId(groupId);
        group.setName("Test Group");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.findUsernamesByGroupId(groupId)).thenReturn(List.of("user1", "user2"));

        GroupMembersDTO result = groupService.getGroupMembers(groupId);

        assertEquals("Test Group", result.getName());
        verify(groupRepository).findById(groupId);
    }

    @Test
    void whenGroupDoesNotExistThenThrowAPIException() {
        UUID groupId = UUID.randomUUID();
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        APIException exception = assertThrows(APIException.class, () -> groupService.getGroupMembers(groupId));
        assertEquals(GroupError.GROUP_DOESNT_EXISTS, exception.getCode());
    }

    @Test
    void whenCreatingGroupWithValidUserThenReturnGroupDTO() throws APIException {
        CreateGroupDTO dto = new CreateGroupDTO();
        dto.setName("New Group");
        dto.setCreatorId(UUID.randomUUID());

        UserEntity creator = new UserEntity();
        creator.setId(dto.getCreatorId());
        creator.setUsername("creator");

        when(userRepository.findById(dto.getCreatorId())).thenReturn(Optional.of(creator));
        when(groupRepository.save(any(GroupEntity.class))).thenAnswer(invocation -> {
            GroupEntity group = invocation.getArgument(0);
            group.setId(UUID.randomUUID());
            return group;
        });

        GroupDTO result = groupService.createGroup(dto);

        assertNotNull(result.getId());
        assertEquals("New Group", result.getName());
        assertNotNull(result.getInviteCode());
        assertEquals("creator", result.getUsers().get(creator.getId()));
        verify(userRepository).findById(dto.getCreatorId());
        verify(groupRepository).save(any(GroupEntity.class));
    }

    @Test
    void whenUserJoinsGroupByInviteCodeThenReturnGroupDTO() throws APIException {
        String inviteCode = "abcd";
        UUID userId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user1");

        GroupEntity group = new GroupEntity();
        group.setId(UUID.randomUUID());
        group.setName("Test Group");
        group.setInviteCode(inviteCode);
        group.setUsers(new HashSet<>());

        when(groupRepository.findByInviteCode(inviteCode)).thenReturn(Optional.of(group));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(groupRepository.save(group)).thenReturn(group);

        GroupDTO result = groupService.joinMemberByUserId(inviteCode, userId);

        assertEquals("Test Group", result.getName());
        assertTrue(result.getUsers().containsKey(userId));
        verify(groupRepository).findByInviteCode(inviteCode);
        verify(userRepository).findById(userId);
    }

    @Test
    void whenGettingGroupWithMembersAndRatingsThenReturnDTO() {
        UUID groupId = UUID.randomUUID();
        GroupEntity group = new GroupEntity();
        group.setId(groupId);
        group.setName("Group with Movies");

        MovieEntity movie = new MovieEntity();
        movie.setId(UUID.randomUUID());
        movie.setWatchOn(List.of(Platform.NETFLIX));
        movie.setBuyOn(List.of(Platform.NETFLIX));
        movie.setName("Movie 1");

        when(groupRepository.findGroupWithUsers(groupId)).thenReturn(Optional.of(group));
        when(movieRepository.findAllMoviesWithRatingsForGroup(groupId)).thenReturn(List.of(movie));

        List<UserMovieEntity> userRatings = new ArrayList<>();
        UserMovieEntity userRating = new UserMovieEntity();
        userRating.setRating(4.0f);
        userRating.setUser(new UserEntity());
        userRating.getUser().setUsername("user1");
        userRating.getUser().setId(UUID.randomUUID());

        userRatings.add(userRating);

        when(userMovieRepository.findRatingsByMovieAndGroup(movie.getId(), groupId)).thenReturn(userRatings);

        GetGroupWithMembersAndRatingsDTO result = groupService.getGroupWithMembersAndRatings(groupId);

        assertEquals(groupId, result.getId());
        assertEquals("Group with Movies", result.getName());
        assertNotNull(result.getMovies());
        assertFalse(result.getMovies().isEmpty());
        assertEquals("Movie 1", result.getMovies().first().getName());
    }
}
