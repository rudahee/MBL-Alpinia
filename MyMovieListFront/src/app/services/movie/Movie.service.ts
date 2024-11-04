import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MovieIdResponseInterface, MovieInterface, MovieRegisterInterface, PageableMovieInterface, RatingMovieInterface } from '../../interfaces/movie';


@Injectable({
  providedIn: 'root'
})
export class MovieService {
 

  constructor(private httpC: HttpClient) { }

  createMovie(movie: MovieRegisterInterface): Observable<MovieIdResponseInterface> {
    return this.httpC.post<MovieIdResponseInterface>('/movie', movie);
  }

  getMovie(uuid: String): Observable<MovieInterface> {
    return this.httpC.get<MovieInterface>('/movie/' + uuid);
  }

  postRatingMovie(rating: RatingMovieInterface): Observable<MovieInterface> {
    return this.httpC.post<MovieInterface>('/rating', rating);

  }

  getRatingMovie(userId: String, movieId: String): Observable<RatingMovieInterface> {
    return this.httpC.get<RatingMovieInterface>('/rating?userId=' + userId + "&movieId=" + movieId);
  }

  getMovies(page: number, pageSize: number, sort: string, field: string): Observable<PageableMovieInterface> {
    return this.httpC.get<PageableMovieInterface>(`/movie/all?page=${page}&page_size=${pageSize}&sort=${sort}&field=${field}`);
  }

}
