import { Routes } from '@angular/router';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { MovieComponent } from './components/movie/movie.component';
import { UserComponent } from './components/user/user.component';
import { TopMoviesComponent } from './components/top-movies/top-movies.component';
import { GroupComponent } from './components/group/group.component';
import { CreateMovieComponent } from './components/create-movie/create-movie.component';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
    { path: 'sign-in', component: SignInComponent },
    { path: 'sign-up', component: SignUpComponent },
    { path: 'movie/:movieId', component: MovieComponent },
    { path: 'user/:userId', component: UserComponent },
    { path: 'top', component: TopMoviesComponent },
    { path: 'group/:groupId', component: GroupComponent},
    { path: 'create/movie', component: CreateMovieComponent },
    { path: '', component: HomeComponent}
];
