package net.rudahee.movies.user.controllers;

import net.rudahee.movies.group.model.api.SimpleGroupDTO;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.model.api.UserWithGroupsDTO;
import net.rudahee.movies.user.services.UserService;
import net.rudahee.movies.user_movie.model.api.MovieTableDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getGroupsByUserId(@RequestParam("id") UUID id) throws APIException {
        UserWithGroupsDTO groups = userService.getGroupsFromUser(id);

        return ResponseEntity.ok(groups);
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getMoviesRatedByUserId(@RequestParam("id") UUID id) {
        List<MovieTableDTO> movies = userService.getMoviesFromUserId(id);

        return ResponseEntity.ok(movies);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getSimpleUserById(id));
    }

}
