package net.rudahee.movies.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.rudahee.movies.group.model.api.GroupDTO;
import net.rudahee.movies.group.model.db.GroupEntity;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.movie.model.db.Platform;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.model.api.SimpleUserDTO;
import net.rudahee.movies.user.model.api.UserWithGroupsDTO;
import net.rudahee.movies.user.model.db.UserEntity;
import net.rudahee.movies.user.repository.UserRepository;
import net.rudahee.movies.user_movie.model.api.MovieTableDTO;
import net.rudahee.movies.user_movie.repository.UserMovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMovieRepository userMovieRepository;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenUserIdExistsThenReturnSimpleUserDTO() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("testUser");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));

        SimpleUserDTO result = userService.getSimpleUserById(userId);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository).findById(userId);
    }

    @Test
    void whenUserHasGroupsThenReturnUserWithGroupsDTO() throws APIException {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("testUser");

        GroupDTO groupDTO = new GroupDTO(UUID.randomUUID(), "Test Group");
        List<GroupDTO> groups = List.of(groupDTO);

        List<GroupEntity> groupEntities = List.of(new GroupEntity(UUID.randomUUID(), "Test Group", "e352", Set.of(userEntity)));

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.findGroupsByUserId(userId)).thenReturn(groupEntities);

        UserWithGroupsDTO result = userService.getGroupsFromUser(userId);

        assertNotNull(result);
        assertEquals("testUser", result.getName());
        assertEquals(1, result.getGroups().size());
        assertEquals("Test Group", result.getGroups().get(0).getName());
    }

    @Test
    void whenUserRatedMoviesExistThenReturnListOfMovieTableDTO() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        MovieTableDTO movieTableDTO = new MovieTableDTO( "Movie Title", "imageUrl", "Synopsis", List.of("Netflix"), List.of("Netflix"), movieId.toString(), 4.0F);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieId);
        movieEntity.setName("Movie Title");
        movieEntity.setImageUrl("imageUrl");
        movieEntity.setSynopsis("Synopsis");
        movieEntity.setShortSynopsis("Synopsis");
        movieEntity.setBuyOn(List.of(Platform.NETFLIX));
        movieEntity.setWatchOn(List.of(Platform.NETFLIX));

        when(userMovieRepository.findRatingFromUserForMovie(userId, movieId)).thenReturn(4.0f);
        when(userRepository.findMoviesRatedByUserId(userId)).thenReturn(List.of(movieEntity));

        List<MovieTableDTO> result = userService.getMoviesFromUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Movie Title", result.get(0).getName());
        assertEquals(4.0f, result.get(0).getMediaRating());
    }

}
