import { Component, inject, OnInit } from '@angular/core';
import {
  MatBottomSheet,
  MatBottomSheetModule,
  MatBottomSheetRef,
} from '@angular/material/bottom-sheet';
import {MatListModule} from '@angular/material/list';
import {MatButtonModule} from '@angular/material/button';
import { GroupsForUserInterface } from '../../../interfaces/user_groups';
import { JwtHandlerService } from '../../../services/jwt/JwtHandler.service';
import { UserService } from '../../../services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-groups-home',
  standalone: true,
  imports: [MatBottomSheetModule, MatListModule, MatButtonModule],
  templateUrl: './groups-home.component.html',
  styleUrls: ['./groups-home.component.css']
})
export class GroupsHomeComponent implements OnInit {


  private _bottomSheetRef = inject<MatBottomSheetRef>(MatBottomSheetRef);
  public groupsUser:GroupsForUserInterface = {
    name: "",
    groups: []
  }
  
  openLink(event: MouseEvent): void {
    this._bottomSheetRef.dismiss();
    event.preventDefault();
  }

  constructor(private jwtService: JwtHandlerService, private router: Router, private userService: UserService) { }

  ngOnInit() {
    if (this.jwtService.getUID() !== '' && this.jwtService.getUID() !== undefined && this.jwtService.getUID() !== null) {
      this.userService.getGroupsByUser(this.jwtService.getUID()).subscribe(
        res => {
          this.groupsUser = res;
        }
      )
    }
  }
  
  navigateToGroup(groupId: String) {
    this.router.navigate(['/group/'+groupId])
  }
}
