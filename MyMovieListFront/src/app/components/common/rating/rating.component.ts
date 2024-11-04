import { UpdatedMovieRatingInterface } from './../../../interfaces/movie';
import { Component, Inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { JwtHandlerService } from '../../../services/jwt/JwtHandler.service';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RatingMovieInterface } from '../../../interfaces/movie';
import { MovieService } from '../../../services/movie/Movie.service';

@Component({
  selector: 'app-rating',
  standalone: true,
  imports: [MatCardModule, MatDialogTitle, MatDividerModule, MatDialogContent, MatDialogActions, MatSelectModule, MatCheckboxModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule, MatInputModule],
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  ratingForm: FormGroup;
  rating: RatingMovieInterface;
  snackBar: any;
  router: any;

  ratingupdated: UpdatedMovieRatingInterface = {
    id: "",
    mediaRating: 0,
    yourRating: 0,
    comment: ""
  }
  
  constructor(private build: FormBuilder, 
              private movieService: MovieService,
              private jwtService: JwtHandlerService, 
              @Inject(MAT_DIALOG_DATA) public movieId: String,
              private dialogRef: MatDialogRef<RatingComponent>) {
    this.ratingForm = this.build.group({
      rating: ['', [Validators.required, Validators.min(0), Validators.max(10)]],
      comment: ['']
    });




    this.rating = {
      movieId: "",
      userId: "",
      rating: 0,
      comment: ""
    }
  }
   
  ngOnInit() {
  }

  createRating(): void {
    this.rating = this.ratingForm.value;

    this.rating.userId = this.jwtService.getUID();
    this.rating.movieId = this.movieId;

    this.movieService.postRatingMovie(this.rating).subscribe(
      resp => {
        this.ratingupdated.id = resp.id;
        this.ratingupdated.mediaRating = resp.mediaGlobalRating ?? 0;
        this.ratingupdated.yourRating = this.ratingForm.value.rating
        this.ratingupdated.comment = this.ratingForm.value.comment

        this.dialogRef.close(this.ratingupdated);
      }, err => {
        console.log(err);
        this.snackBar.open(err.error.message, 'Close', { duration: 5000, panelClass: 'snackbar'});
      }
    );
  }

  cancel() {
    this.dialogRef.close()
  }


}
