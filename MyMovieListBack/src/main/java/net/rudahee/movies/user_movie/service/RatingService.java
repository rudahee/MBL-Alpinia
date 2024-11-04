package net.rudahee.movies.user_movie.service;

import net.rudahee.movies.group.model.api.GroupError;
import net.rudahee.movies.group.model.api.SimpleGroupDTO;
import net.rudahee.movies.group.repository.GroupRepository;
import net.rudahee.movies.group.service.GroupService;
import net.rudahee.movies.movie.model.api.MovieDTO;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.movie.repository.MovieRepository;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.repository.UserRepository;
import net.rudahee.movies.user.services.UserService;
import net.rudahee.movies.user_movie.model.api.GroupMaxMinRatingDTO;
import net.rudahee.movies.user_movie.model.api.RatingAndCommentFromUserDTO;
import net.rudahee.movies.user_movie.model.api.UserRatingMovieDTO;
import net.rudahee.movies.user_movie.model.db.UserMovieEntity;
import net.rudahee.movies.user_movie.model.db.UserMoviePrimaryKey;
import net.rudahee.movies.user_movie.repository.UserMovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {

    private final UserMovieRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;

    public RatingService(UserMovieRepository repository, UserRepository userRepository, MovieRepository movieRepository,
                         UserService userService, GroupRepository groupRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.userService = userService;
        this.groupRepository = groupRepository;
    }

    public MovieDTO userRatingMovie(UserRatingMovieDTO dto) throws APIException {
        MovieEntity movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new APIException(GroupError.GROUP_DOESNT_EXISTS));

        UserMovieEntity entity = new UserMovieEntity(
                new UserMoviePrimaryKey(dto.getUserId(), dto.getMovieId()),
                userRepository.findById(dto.getUserId()).orElseThrow(() -> new APIException(GroupError.GROUP_DOESNT_EXISTS)),
                movie,
                dto.getRating(),
                dto.getComment());

        repository.save(entity);
        updateMovieRating(movie);

        return convertToMovieDTO(movie);
    }

    public RatingAndCommentFromUserDTO getRatingAndComment(UUID userId, UUID movieId) throws APIException {
        return repository.findById(new UserMoviePrimaryKey(userId, movieId))
                .map(entity -> new RatingAndCommentFromUserDTO(entity.getRating(), entity.getComment()))
                .orElseThrow(() -> new APIException(GroupError.GROUP_DOESNT_EXISTS));
    }

    public List<GroupMaxMinRatingDTO> getMaxAndMinRating(UUID movieId, UUID userId) {
        List<SimpleGroupDTO> groups = userService.getGroupsFromUserId(userId);
        List<GroupMaxMinRatingDTO> dtos = new ArrayList<>();

        for (SimpleGroupDTO group : groups) {
            GroupMaxMinRatingDTO dto = new GroupMaxMinRatingDTO();
            dto.setMaxRating(repository.findMaxRatingFromMovieByGroup(movieId, group.getId()));
            dto.setMaxRatingUsername(repository.findMaxRatingUsernameFromMovieByGroup(movieId, group.getId()));
            dto.setMinRating(repository.findMinRatingFromMovieByGroup(movieId, group.getId()));
            dto.setMinRatingUsername(repository.findMinRatingUsernameFromMovieByGroup(movieId, group.getId()));
            dto.setName(groupRepository.findNameById(group.getId()));
            dto.setAvgRating(repository.getAverageRatingForGroup(group.getId(), movieId));
            dtos.add(dto);
        }

        return dtos;
    }

    private MovieDTO convertToMovieDTO(MovieEntity movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setMediaGlobalRating(movie.getMediaGlobalRating());
        return dto;
    }
    private void updateMovieRating(MovieEntity movie) {
        List<Float> ratings = repository.findRatingsFromMovie(movie.getId());
        Float averageRating = ratings.isEmpty() ? null : (float) ratings.stream().mapToDouble(Float::doubleValue).average().orElse(0);
        movie.setMediaGlobalRating(averageRating);
        movieRepository.save(movie);
    }
}
