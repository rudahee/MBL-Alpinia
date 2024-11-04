package net.rudahee.movies.user.controllers;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.rudahee.movies.group.model.api.GroupDTO;
import net.rudahee.movies.user.model.api.UserWithGroupsDTO;
import net.rudahee.movies.user.services.UserService;
import net.rudahee.movies.user_movie.model.api.MovieTableDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void whenUserIdIsProvidedThenReturnUserGroups() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID group1 = UUID.randomUUID();
        UUID group2 = UUID.randomUUID();

        UserWithGroupsDTO userGroupsDTO = new UserWithGroupsDTO();

        userGroupsDTO.setName("name");
        userGroupsDTO.setGroups(List.of(new GroupDTO(group1, "group name 1"), new GroupDTO(group2, "group name 2")));

        when(userService.getGroupsFromUser(userId)).thenReturn(userGroupsDTO);

        String expectedJson = objectMapper.writeValueAsString(userGroupsDTO);

        mockMvc.perform(get("/user/groups?id=" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(userService).getGroupsFromUser(userId);
    }


    @Test
    void whenUserIdIsProvidedThenReturnUserRatedMovies() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID movieId1 = UUID.randomUUID();
        UUID movieId2 = UUID.randomUUID();

        List<MovieTableDTO> ratedMovies = List.of(
                new MovieTableDTO("Movie Title 1", null, "test1", null, null, movieId1.toString(), 4.5f),
                new MovieTableDTO("Movie Title 2", null, "test2", null, null, movieId2.toString(), 2.5f)
        );

        when(userService.getMoviesFromUserId(userId)).thenReturn(ratedMovies);

        String expectedJson = objectMapper.writeValueAsString(ratedMovies);

        mockMvc.perform(get("/user/movies?id=" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(userService).getMoviesFromUserId(userId);
    }
}
