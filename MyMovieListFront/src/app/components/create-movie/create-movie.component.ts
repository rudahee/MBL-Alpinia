import { MovieService } from './../../services/movie/Movie.service';
import { MovieInterface, MovieRegisterInterface } from './../../interfaces/movie';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogActions, MatDialogContent, MatDialogModule, MatDialogTitle } from '@angular/material/dialog';


@Component({
  selector: 'app-create-movie',
  standalone: true,
  imports: [MatCardModule, MatSelectModule, MatCheckboxModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule,MatInputModule],
  templateUrl: './create-movie.component.html',
  styleUrls: ['./create-movie.component.css']
})
export class CreateMovieComponent implements OnInit {

  platforms: string[] = ['Prime Video', 'Apple TV', 'Disney +', 'Google Play', 'HBO Max', 'Netflix', 'Youtube'];
  createMovieForm: FormGroup;
  movie:MovieRegisterInterface;
  snackBar: any;
  router: any;

  constructor(private build: FormBuilder, private service: MovieService) {


    this.createMovieForm = this.build.group({
      name: ['', [Validators.required]],
      shortSynopsis: ['', [Validators.required, Validators.maxLength(120)]],
      synopsis: ['', [Validators.required, Validators.maxLength(750)]],
      imageUrl: ['', [Validators.required]],
      buyOn: [''],
      watchOn: ['']
    });

    this.movie = {
      title: "",
      shortSynopsis: "",
      synopsis: "",
      buyOn: [],
      watchOn: [],
      imageUrl: ""
    }

  }

  ngOnInit() {
  }

  createMovie(): void {
    this.movie = this.createMovieForm.value;
    console.log(this.movie);

    this.service.createMovie(this.movie).subscribe(
      resp => {
        console.log(resp);
        this.snackBar.open('Successfully created', 'Close', { duration: 5000, panelClass: 'snackbar'});
        setTimeout(() => {
          this.router.navigate(['/movie/'+resp.id]);
        }, 500);

      }, err => {
        console.log(err);
        this.snackBar.open(err.error.message, 'Close', { duration: 5000, panelClass: 'snackbar'});
      }
    );
  }
}
