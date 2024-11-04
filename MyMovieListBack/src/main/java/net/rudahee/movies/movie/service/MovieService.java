package net.rudahee.movies.movie.service;

import net.rudahee.movies.group.model.api.GroupError;
import net.rudahee.movies.movie.model.api.SortType;
import net.rudahee.movies.movie.model.api.CreateMovieDTO;
import net.rudahee.movies.movie.model.api.MovieDTO;
import net.rudahee.movies.movie.model.api.ResponseMovieIdDTO;
import net.rudahee.movies.movie.model.db.MovieEntity;
import net.rudahee.movies.movie.model.db.Platform;
import net.rudahee.movies.movie.repository.MovieRepository;
import net.rudahee.movies.shared.exceptions.APIException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public ResponseMovieIdDTO createMovie(CreateMovieDTO dto) {
        MovieEntity entity = new MovieEntity();

        entity.setName(dto.getName());
        entity.setSynopsis(dto.getSynopsis());
        entity.setShortSynopsis(dto.getShortSynopsis());
        entity.setImageUrl(dto.getImageUrl());

        List<Platform> buyPlatforms = convertStringToPlatform(dto.getBuyOn());
        entity.setBuyOn(buyPlatforms);
        List<Platform> watchPlatforms = convertStringToPlatform(dto.getWatchOn());
        entity.setWatchOn(watchPlatforms);

        MovieEntity responseEntity = this.movieRepository.save(entity);
        ResponseMovieIdDTO responseDTO = new ResponseMovieIdDTO(responseEntity.getId());

        return responseDTO;
    }

    public MovieDTO getMovieById(UUID id) throws APIException {
        Optional<MovieEntity> movieOpt = movieRepository.findById(id);

        if (movieOpt.isPresent()) {
            return convertToDTO(movieOpt.get());
        } else {
            throw new APIException(GroupError.GROUP_DOESNT_EXISTS);
        }
    }

    public Page<MovieDTO> getAllMovies(Integer page, Integer size, SortType sort, String field) {
        PageRequest pageRequest;
        if (field.equals("rating")) {
            pageRequest = PageRequest.of(page, size,
                (sort == SortType.DESC) ? Sort.by("mediaGlobalRating").descending() : Sort.by("mediaGlobalRating").ascending());
        } else if (field.equals("title")) {
            pageRequest = PageRequest.of(page, size,
                    (sort == SortType.DESC) ? Sort.by("name").descending() : Sort.by("name").ascending());
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        Page<MovieEntity> moviePage = movieRepository.findAll(pageRequest);

        List<MovieDTO> movieDTOs = moviePage.getContent().stream()
                .map(this::convertToDTO)
                .toList();

        return new PageImpl<>(movieDTOs, pageRequest, moviePage.getTotalElements());
    }

    private MovieDTO convertToDTO(MovieEntity entity) {
        MovieDTO dto = new MovieDTO();
        dto.setName(entity.getName());
        dto.setSynopsis(entity.getSynopsis());
        dto.setShortSynopsis(entity.getShortSynopsis());
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setMediaGlobalRating(entity.getMediaGlobalRating());

        List<String> watchOnList = convertPlatformsToString(entity.getWatchOn());
        dto.setWatchOn(watchOnList);

        List<String> buyOn = convertPlatformsToString(entity.getBuyOn());
        dto.setBuyOn(buyOn);

        return dto;
    }

    private List<String> convertPlatformsToString(List<Platform> platforms) {
        return platforms.stream()
                .map(platform -> platform.name().replace("_", " ").replace("PLUS", "+").toLowerCase())
                .toList();
    }

    private List<Platform> convertStringToPlatform(List<String> platforms) {
         return platforms.stream()
                .map(platformName -> Platform.valueOf(platformName.toUpperCase().replace(" ", "_").replace("+", "PLUS"))).toList();
    }
}
