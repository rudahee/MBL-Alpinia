import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MovieInterface } from '../../interfaces/movie';
import { MatDialog } from '@angular/material/dialog';
import { WatchOnComponent } from '../common/watch-on/watch-on.component';
import { GroupPageInterface, GroupWithMembersInterface } from '../../interfaces/group';
import { GroupService } from '../../services/group/group.service';
import { ActivatedRoute } from '@angular/router';
import { JwtHandlerService } from '../../services/jwt/JwtHandler.service';

@Component({
  selector: 'app-group',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatDividerModule, MatIconModule],
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  readonly dialog = inject(MatDialog);



  id: string = "";
  groupId: string = "";

  members: GroupWithMembersInterface = {
    id: '',
    name: '',
    members: []
  };

  group:GroupPageInterface = {
    id: "",
    name: "",
    inviteCode: "",
    movies: []
  }

  openDialog(element: any) {
    this.dialog.open(WatchOnComponent, {width: '30%',
      panelClass: 'watch-on.modal', 
    data: element});
  }

  constructor(private groupService: GroupService, private activateRoute: ActivatedRoute, private jwtService: JwtHandlerService) { }

  ngOnInit() {

    this.groupId = this.jwtService.getUID()

    this.id = this.activateRoute.snapshot.params['groupId']

    this.groupService.getGroupPageInfo(this.id).subscribe(
      data => {
        this.group = data;
      }, err => {
        console.log(err)
      })

      this.groupService.getGroupMembers(this.id).subscribe(
        data => {
          console.log(data)
          this.members = data;
          console.log(data.members)
        }
      )
  }

}
