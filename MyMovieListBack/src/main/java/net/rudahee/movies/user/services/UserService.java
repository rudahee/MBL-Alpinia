package net.rudahee.movies.user.services;

import net.rudahee.movies.group.model.api.GroupDTO;
import net.rudahee.movies.group.model.api.SimpleGroupDTO;
import net.rudahee.movies.group.model.db.GroupEntity;
import net.rudahee.movies.movie.model.db.Platform;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.model.api.SimpleUserDTO;
import net.rudahee.movies.user.model.api.UserError;
import net.rudahee.movies.user.model.api.UserWithGroupsDTO;
import net.rudahee.movies.user.model.db.UserEntity;
import net.rudahee.movies.user.repository.UserRepository;
import net.rudahee.movies.user_movie.model.api.MovieTableDTO;
import net.rudahee.movies.user_movie.repository.UserMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMovieRepository ratingRepository;

    public UserService(UserRepository repository, UserMovieRepository ratingRepository) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
    }

    public SimpleUserDTO getSimpleUserById(UUID id) {
        return new SimpleUserDTO(this.repository.findById(id).orElseThrow().getUsername());
    }

    public UserWithGroupsDTO getGroupsFromUser(UUID id) throws APIException {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new APIException(UserError.USER_DOESNT_EXISTS));

        List<GroupDTO> groupsDTO = repository.findGroupsByUserId(id).stream()
                .map(group -> new GroupDTO(group.getId(), group.getName()))
                .toList();

        return new UserWithGroupsDTO(user.getUsername(), groupsDTO);
    }

    public List<SimpleGroupDTO> getGroupsFromUserId(UUID id) {
        List<GroupEntity> entities = repository.findGroupsByUserId(id);

        List<SimpleGroupDTO> groups = entities.stream()
                .map(entity -> new SimpleGroupDTO(entity.getId(), entity.getName())).toList();

        return groups;
    }

    public List<MovieTableDTO> getMoviesFromUserId(UUID id) {
        return repository.findMoviesRatedByUserId(id).stream()
                .map(entity -> {
                    MovieTableDTO dto = new MovieTableDTO();
                    dto.setName(entity.getName());
                    dto.setId(String.valueOf(entity.getId()));
                    dto.setShortSynopsis(entity.getShortSynopsis());
                    dto.setImageUrl(entity.getImageUrl());
                    dto.setWatchOn(formatPlatforms(entity.getWatchOn()));
                    dto.setBuyOn(formatPlatforms(entity.getBuyOn()));
                    dto.setMediaRating(mediaRatingByUserAndMovie(id, entity.getId()));
                    return dto;
                }).toList();
    }

    private List<String> formatPlatforms(List<Platform> platforms) {
        return platforms.stream()
                .map(platform -> platform.name().replace("_", " ").replace("PLUS", "+").toLowerCase())
                .toList();
    }

    private Float mediaRatingByUserAndMovie(UUID userId, UUID movieId) {
        return ratingRepository.findRatingFromUserForMovie(userId, movieId);
    }
}
