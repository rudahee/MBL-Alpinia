import { UserGroupsInterface } from './../../interfaces/user_groups';
import { Component, AfterViewInit, ViewChild, inject, ChangeDetectorRef } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { MovieInterface } from '../../interfaces/movie';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { WatchOnComponent } from '../common/watch-on/watch-on.component';
import { MatDialog } from '@angular/material/dialog';
import { NewGroupComponent } from '../common/new-group/new-group.component';
import { JoinGroupComponent } from '../common/join-group/join-group.component';
import { UserService } from '../../services/user/user.service';
import { MovieService } from '../../services/movie/Movie.service';
import { JwtHandlerService } from '../../services/jwt/JwtHandler.service';
import { GroupService } from '../../services/group/group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SimpleUserInterface } from '../../interfaces/user';


@Component({
  selector: 'app-user',
  standalone: true,
  imports: [MatCardModule, MatDividerModule, MatIconModule,
    MatButtonModule, MatPaginatorModule,
    MatSortModule, MatTableModule, MatButtonToggleModule],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements AfterViewInit {


  displayedColumns: String[] = ['title', 'image', 'synopsis', 'rating', 'actions'];
  
  resultsLength:Number | undefined
  movies:Array<MovieInterface> = []
  groups:any = []
  simpleUser:SimpleUserInterface = {
    username: ''
  }

  id: string = "";
  userId: string = "";

  dataSource: any;
  
  readonly dialog = inject(MatDialog);
   
  openDialog(element: any) {
    this.dialog.open(WatchOnComponent, {width: '30%',
      panelClass: 'watch-on.modal', 
    data: element});
  }
  
  openGroupDialog() {
    this.dialog.open(NewGroupComponent, {width: '40%',
      panelClass: 'new-group.modal'});
  }

  openJoinGroupDialog() {
    this.dialog.open(JoinGroupComponent, {width: '40%',
      panelClass: 'join-group.modal'});
  }


  @ViewChild(MatSort) sort: MatSort = <MatSort>{};
  @ViewChild(MatPaginator) paginator: MatPaginator = <MatPaginator>{};

  constructor(private userService: UserService, 
    private groupService: GroupService,
    private jwtService: JwtHandlerService,
    private activateRoute: ActivatedRoute,
    private route: Router,
    private cdr: ChangeDetectorRef) {
  }

  ngAfterViewInit() {
    this.userId = this.jwtService.getUID()

    this.id = this.activateRoute.snapshot.params['userId']

    this.userService.getSimpleUser(this.id).subscribe(
      res => {
        this.cdr.markForCheck()
        this.simpleUser = res;
        this.cdr.detectChanges()
      }
    )


    const actualMovies: Array<MovieInterface> = []
    this.userService.getMovies(this.id).subscribe(
      res => {
        this.cdr.markForCheck()
        this.movies = res;

        this.dataSource = new MatTableDataSource<MovieInterface>(this.movies);
        this.cdr.detectChanges()

      }
    )

    this.groupService.getGroupWithMembers(this.id).subscribe(
      res => {
        console.log("REEES")
        console.log(res)
        this.groups = res;
      }
    )
    
    this.dataSource.paginator = this.paginator;
    this.resultsLength = this.movies.length
    this.dataSource.sort = this.sort;
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0))
  }

  navigateToSignIn() {
    this.route.navigate(['/sign-in'])
  }
}


