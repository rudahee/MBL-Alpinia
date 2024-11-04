import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { JwtHandlerService } from '../../../services/jwt/JwtHandler.service';
import { Location } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatMenuModule} from '@angular/material/menu';
import { UserService } from '../../../services/user/user.service';
import { GroupsForUserInterface } from '../../../interfaces/user_groups';

@Component({
  selector: 'app-navbar',
  standalone: true,
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports: [MatToolbarModule, MatMenuModule, MatFormFieldModule, MatButtonModule, MatIconModule]
})
export class NavbarComponent implements OnInit {


  public sideNav: boolean; //used to open right sandwich menu.

  public id: string = '';
  public role: string = '';
  public groupsUser:GroupsForUserInterface = {
    name: "",
    groups: []
  }

  constructor(private location: Location, private router: Router,
             private jwtService: JwtHandlerService, private userService: UserService) {
    this.sideNav = false;



    if (localStorage.getItem('user_id')) {
      this.id = localStorage.getItem('user_id') ?? '';
    }

    if (localStorage.getItem('roles')) {
      this.role = localStorage.getItem('rol') ?? '';
    }
  }
  
  ngOnInit() {

    if (this.id !== '' && this.id !== undefined && this.id !== null) {
      this.userService.getGroupsByUser(this.id).subscribe(
        res => {
          this.groupsUser = res;
        }
      )
    }
  }

  navigateBack(): void {
    this.location.back();
  }

  navigateToMain(): void {
    this.router.navigate(['/']);
  }

  navigateToYourPage(): void {
    this.router.navigate(['/user/'+this.id]);
  }

  navigateToTopSeries(): void {
    this.router.navigate(['/top']);
  }

  navigateToSignIn(): void {
      this.router.navigate(['/sign-in']);
  }
 
  navigateToSignUp(): void {
    this.router.navigate(['/sign-up']);
  }

  navigateToCreateGroup(): void {
    this.router.navigate(['/create/movie'])
  }

  public signOut(): void {
    this.jwtService.deleteJWT();
  }

  navigateToGroup(groupId: String) {
    this.router.navigate(['/group/'+groupId])
  }

}
