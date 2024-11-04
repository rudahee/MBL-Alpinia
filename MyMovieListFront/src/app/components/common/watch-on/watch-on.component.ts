import { Component, Inject, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MovieInterface } from '../../../interfaces/movie';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-watch-on',
  templateUrl: './watch-on.component.html',
  standalone: true,
  imports: [MatDialogTitle, MatDividerModule, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule],
  styleUrls: ['./watch-on.component.css']
})
export class WatchOnComponent implements OnInit {


  movie:MovieInterface = {
    watchOn: ["amazon", "hbo-max", "disney"],
    buyOn: ["youtube", "apple-tv"],
    name: "",
    imageUrl:"",
    synopsis:"",
    shortSynopsis:"",
    id: ""
  }


  constructor(@Inject(MAT_DIALOG_DATA) public movieIn: MovieInterface,) { }

  ngOnInit() {
    this.movie.buyOn = this.movieIn.buyOn.map(item => item.replace(" ", "-"))
    this.movie.watchOn = this.movieIn.watchOn.map(item => item.replace(" ", "-"))
  }

}
