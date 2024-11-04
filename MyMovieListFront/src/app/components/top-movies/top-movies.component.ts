import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, signal, ViewChild } from '@angular/core';
import { MovieInterface } from '../../interfaces/movie';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { WatchOnComponent } from '../common/watch-on/watch-on.component';
import { MovieService } from '../../services/movie/Movie.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top-movies',
  imports: [MatCardModule, MatDividerModule, MatIconModule, FormsModule,
    MatButtonModule, MatPaginatorModule,
    MatSortModule, MatTableModule, MatButtonToggleModule],
  standalone: true,
  templateUrl: './top-movies.component.html',
  styleUrls: ['./top-movies.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TopMoviesComponent implements AfterViewInit {


  sort: string = 'rating-desc'; // Valor inicial para la ordenación
  

  hideSingleSelectionIndicator = signal(false);
  toggleSingleSelectionIndicator() {
    this.hideSingleSelectionIndicator.update(value => !value);
  }
  
  displayedColumns: String[] = ['title', "image", 'synopsis', 'rating', 'actions'];
  resultsLength:Number = 0;
  dataSource = new MatTableDataSource<MovieInterface>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  readonly dialog = inject(MatDialog);
   
  openDialog(element: any) {
    this.dialog.open(WatchOnComponent, {width: '40%',
      panelClass: 'watch-on.modal', data: element});
  }

  constructor(private movieService: MovieService, private cdr: ChangeDetectorRef, private router: Router) {
  }

  ngAfterViewInit() {
    this.loadMovies();
    
    this.paginator.page.subscribe(() => {
      this.loadMovies();
    });
    this.cdr.detectChanges();

  }

  loadMovies() {
    const page = this.paginator.pageIndex; // La API generalmente usa páginas 1-based
    const pageSize = this.paginator.pageSize;


    var fieldType = this.sort.split("-")[0] 
    var sortType = this.sort.split("-")[1]

    this.movieService.getMovies(page, pageSize, sortType, fieldType).subscribe(data => {
      this.dataSource.data = data.content; // Asegúrate de que coincida con la estructura de tu respuesta
      this.resultsLength = data.totalElements; // Asegúrate de que coincida con la estructura de tu respuesta
    });
    this.cdr.detectChanges();

  }


  onSortChange() {
    // Cambiar lógica para que se aplique el orden correcto
    this.cdr.markForCheck()
    this.paginator.firstPage()
    this.loadMovies();
  }

  navigateTo(groupId: String) {
    this.router.navigate(['/movie/'+groupId])
  }
}
