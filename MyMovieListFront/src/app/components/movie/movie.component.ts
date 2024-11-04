import { JwtHandlerService } from './../../services/jwt/JwtHandler.service';
import { UpdatedMovieRatingInterface } from './../../interfaces/movie';
import { MovieService } from './../../services/movie/Movie.service';
import { GroupInterface, SimpleGroupInterface } from './../../interfaces/group';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MovieInterface as MoviecompleteInterface } from '../../interfaces/movie';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RatingComponent } from '../common/rating/rating.component';
import { MatDialog } from '@angular/material/dialog';
import { JoinGroupComponent } from '../common/join-group/join-group.component';
import { NewGroupComponent } from '../common/new-group/new-group.component';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { group } from '@angular/animations';

@Component({
  selector: 'app-movie',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatDividerModule, MatIconModule],
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MovieComponent implements OnInit {



  id:String = "";
  username:String = ""
  userId:String = ""


  user_rating:UpdatedMovieRatingInterface = {
    id: "",
    mediaRating: 0,
    yourRating: undefined,
    comment: ""
  }

  movie:MoviecompleteInterface = {
    id: "",
    buyOn: [],
    imageUrl: "",
    shortSynopsis: "",
    synopsis: "",
    name: "",
    watchOn: [],
  }

  groups:GroupInterface[] = []

readonly dialog = inject(MatDialog)

  constructor(private jwtHandler: JwtHandlerService, 
                private userServ: UserService,
                private movieServ:MovieService, 
                private activateRoute: ActivatedRoute,
                private route: Router,
                private cdr: ChangeDetectorRef) { 

                  
                }
                

  ngOnInit() {

    this.id = this.activateRoute.snapshot.params['movieId']    
    this.userId = this.jwtHandler.getUID();

    this.username = this.jwtHandler.getName()

    this.userServ.getGroups(this.id).subscribe(
      res => {
        console.log(res)
        this.groups = res;
      }
    )

    this.movieServ.getMovie(this.id).subscribe(
      res => {
        this.cdr.markForCheck()
        this.movie = res;
        this.movie.buyOn = res.buyOn.map(item => item.replace(" ", "-"))
        this.movie.watchOn = res.watchOn.map(item => item.replace(" ", "-"))
        this.movie.mediaRating = res.mediaGlobalRating;

        if (this.user_rating.yourRating === undefined) {
          this.movieServ.getRatingMovie(this.jwtHandler.getUID(), res.id).subscribe(
            res2 => {
              this.cdr.markForCheck()
              this.user_rating.yourRating = res2.rating;
              this.user_rating.comment = res2.comment;
              this.cdr.detectChanges()

            }
          )
        }
        this.cdr.detectChanges()
      }
    );
  }

  openDialog() {
    const dialogRef = this.dialog.open(RatingComponent, {width: '40%',
      panelClass: 'rating.modal', data: this.movie.id})
      
    dialogRef.afterClosed().subscribe(
      res => {

        this.cdr.markForCheck()
        this.user_rating = res;
        this.movie.mediaRating = res.mediaRating;
        this.cdr.detectChanges()
      }, 
      err => {
        console.log(err);
    });
  }

  openGroupDialog() {
    this.dialog.open(NewGroupComponent, {width: '40%',
      panelClass: 'new-group.modal'});
  }

  openJoinGroupDialog() {
    this.dialog.open(JoinGroupComponent, {width: '40%',
      panelClass: 'join-group.modal'});
  }

  openAddRating() {
    this.dialog.open(RatingComponent, {width: '30%',
      panelClass: 'watch-on.modal', 
    data: this.user_rating});
  }


  navigateToSignIn() {
    this.route.navigate(['/sign-in'])
  }
}
