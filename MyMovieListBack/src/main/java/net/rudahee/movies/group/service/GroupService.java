package net.rudahee.movies.group.service;

import jakarta.persistence.EntityNotFoundException;
import net.rudahee.movies.group.model.api.*;
import net.rudahee.movies.group.model.db.GroupEntity;
import net.rudahee.movies.group.repository.GroupRepository;
import net.rudahee.movies.movie.model.api.MovieWithGroupMediaRatingDTO;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.movie.model.db.Platform;
import net.rudahee.movies.movie.repository.MovieRepository;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.model.api.UserError;
import net.rudahee.movies.user.model.db.UserEntity;
import net.rudahee.movies.user.repository.UserRepository;
import net.rudahee.movies.user_movie.model.api.UserRatingMovieDTO;
import net.rudahee.movies.user_movie.model.db.UserMovieEntity;
import net.rudahee.movies.user_movie.repository.UserMovieRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieRepository movieRepository;


    public GroupService(GroupRepository groupRepository, UserRepository userRepository, UserMovieRepository userMovieRepository, MovieRepository movieRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
    }

    public GroupMembersDTO getGroupMembers(UUID id) throws APIException {

        Optional<GroupEntity> groupOpt = groupRepository.findById(id);

        if (groupOpt.isPresent()) {

            List<String> usernames = groupRepository.findUsernamesByGroupId(id);

            return new GroupMembersDTO(groupOpt.get().getName(), usernames);

        } else {
            throw new APIException(GroupError.GROUP_DOESNT_EXISTS);
        }
    }

    public GroupDTO createGroup(CreateGroupDTO dto) throws APIException {
        GroupEntity group = new GroupEntity();
        group.setName(dto.getName());
        Optional<UserEntity> user = userRepository.findById(dto.getCreatorId());
        if (user.isPresent()) {
            group.setUsers(Set.of(user.get()));
            group.setInviteCode(UUID.randomUUID().toString().substring(0, 4));

            GroupEntity groupResponse = groupRepository.save(group);

            Map<UUID, String> users = groupResponse.getUsers().stream()
                    .collect(Collectors.toMap(UserEntity::getId, UserEntity::getUsername));

            return new GroupDTO(groupResponse.getId(), groupResponse.getName(), groupResponse.getInviteCode(), users);

        } else {
            throw new APIException(UserError.USER_DOESNT_EXISTS);
        }
    }

    public GroupDTO joinMemberByUserId(String inviteCode, UUID userId) throws APIException {
        Optional<GroupEntity> groupOpt = groupRepository.findByInviteCode(inviteCode);

        Optional<UserEntity> userOpt = userRepository.findById(userId);

        if (groupOpt.isPresent()) {
            GroupEntity group = groupOpt.get();
            Set<UserEntity> users = group.getUsers();
            users.add(userOpt.get());
            group.setUsers(users);
            GroupEntity response = groupRepository.save(group);

            Map<UUID, String> usersMap = response.getUsers().stream()
                    .collect(Collectors.toMap(UserEntity::getId, UserEntity::getUsername));

            return new GroupDTO(response.getId(), response.getName(), response.getInviteCode(), usersMap);
        } else {
            throw new APIException(GroupError.GROUP_DOESNT_EXISTS);
        }
    }

    public GroupDTO joinMemberByPrincipal(String inviteCode, Principal user) throws APIException {
        Optional<GroupEntity> groupOpt = groupRepository.findByInviteCode(inviteCode);

        Optional<UserEntity> userOpt = userRepository.getByUsername(user.getName());

        if (groupOpt.isPresent()) {
            GroupEntity group = groupOpt.get();
            Set<UserEntity> users = group.getUsers();
            users.add(userOpt.get());
            group.setUsers(users);
            GroupEntity response = groupRepository.save(group);

            Map<UUID, String> usersMap = response.getUsers().stream()
                    .collect(Collectors.toMap(UserEntity::getId, UserEntity::getUsername));

            return new GroupDTO(response.getId(), response.getName(), response.getInviteCode(), usersMap);
        } else {
            throw new APIException(GroupError.GROUP_DOESNT_EXISTS);
        }
    }

    public List<GroupMembersDTO> getAllGroupsWithMembers(UUID userId) {
        List<GroupEntity> groups = groupRepository.findGroupsByUserId(userId);

        return groups.stream()
                .map(group -> new GroupMembersDTO(group.getName(), group.getUsers().stream()
                        .filter(user -> user.getId() != userId).map(UserEntity::getUsername).toList())).toList();
    }


    public GetGroupWithMembersAndRatingsDTO getGroupWithMembersAndRatings(UUID groupId) {
        Optional<GroupEntity> groupOptional = groupRepository.findGroupWithUsers(groupId);
        if (groupOptional.isPresent()) {
            GroupEntity group = groupOptional.get();

            // Get all movies with ratings for the group
            List<MovieEntity> movies = movieRepository.findAllMoviesWithRatingsForGroup(groupId);

            SortedSet<MovieWithGroupMediaRatingDTO> movieDTOs = new TreeSet<>(Comparator.comparing(MovieWithGroupMediaRatingDTO::getName));

            for (MovieEntity movie : movies) {
                // Fetch user ratings for the movie within the group
                List<UserMovieEntity> userRatings = userMovieRepository.findRatingsByMovieAndGroup(movie.getId(), groupId);

                Float averageRating = null;
                if (!userRatings.isEmpty()) {
                    averageRating = userRatings.stream()
                            .map(UserMovieEntity::getRating)
                            .reduce(0.0f, Float::sum) / userRatings.size();
                }

                // Create UserRatingMovieDTOs
                SortedSet<UserRatingMovieDTO> userRatingDTOs = new TreeSet<>(Comparator.comparing(UserRatingMovieDTO::getRating).reversed());
                for (UserMovieEntity userRating : userRatings) {
                    userRatingDTOs.add(new UserRatingMovieDTO(
                            movie.getId(),
                            userRating.getUser().getUsername(),
                            userRating.getUser().getId(),
                            userRating.getRating(),
                            userRating.getComment()
                    ));
                }

                // Create MovieWithGroupMediaRatingDTO
                movieDTOs.add(new MovieWithGroupMediaRatingDTO(
                        movie.getId(),
                        movie.getName(),
                        movie.getImageUrl(),
                        formatPlatforms(movie.getBuyOn()),
                        formatPlatforms(movie.getWatchOn()),
                        averageRating,
                        userRatingDTOs
                ));
            }

            // Create and return GetGroupWithMembersAndRatingsDTO
            return new GetGroupWithMembersAndRatingsDTO(
                    group.getId(),
                    group.getName(),
                    group.getInviteCode(),
                    movieDTOs
            );
        } else {
            throw new EntityNotFoundException("Group not found");
        }
    }

    private List<String> formatPlatforms(List<Platform> platforms) {
        return platforms.stream()
                .map(platform -> platform.name().replace("_", " ").replace("PLUS", "+").toLowerCase())
                .toList();
    }

}
