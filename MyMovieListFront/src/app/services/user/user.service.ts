import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { LoginInterface, SuccessfullyLoggedInterface } from '../../interfaces/login';
import { RegisteredUser, RegisterInterface } from '../../interfaces/register';
import { SimpleGroupInterface } from '../../interfaces/group';
import { JwtHandlerService } from '../jwt/JwtHandler.service';
import { MovieInterface } from '../../interfaces/movie';
import { SimpleUserInterface } from '../../interfaces/user';
import { GroupsForUserInterface, UserGroupsInterface } from '../../interfaces/user_groups';

@Injectable({
  providedIn: 'root'
})
export class UserService {

constructor(private httpC: HttpClient, private jwtHandler: JwtHandlerService) {}

  getSimpleUser(id: String): Observable<SimpleUserInterface> {
    return this.httpC.get<SimpleUserInterface>('/user/' + id);
  }

  signUp(userForRegister: RegisterInterface): Observable<RegisteredUser> {
    return this.httpC.post<RegisteredUser>('/auth/sign-up', userForRegister);
  }

  signIn(loginData: LoginInterface): Observable<HttpResponse<SuccessfullyLoggedInterface>> {
    return this.httpC.post<SuccessfullyLoggedInterface>('/auth/sign-in', loginData, {observe: 'response'});
  }

  getGroups(id: String): Observable<Array<SimpleGroupInterface>> {
    return this.httpC.get<Array<SimpleGroupInterface>>("/rating/group?movieId=" + id + "&userId=" + this.jwtHandler.getUID());
  }

  getMovies(id: String): Observable<Array<MovieInterface>> {
    return this.httpC.get<Array<MovieInterface>>("/user/movies?id=" + id);
  }

  getGroupsByUser(id: String): Observable<GroupsForUserInterface> {
    return this.httpC.get<GroupsForUserInterface>(`/user/groups?id=${id}`)
  }
}
