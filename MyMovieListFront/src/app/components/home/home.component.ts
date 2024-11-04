import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { JwtHandlerService } from '../../services/jwt/JwtHandler.service';
import { Router } from '@angular/router';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { GroupsHomeComponent } from '../common/groups-home/groups-home.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatCardModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule,MatInputModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  id: String = ""

  constructor(private jwtService: JwtHandlerService, private router: Router) { }

  ngOnInit() {
    this.id = this.jwtService.getUID()
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

  private _bottomSheet = inject(MatBottomSheet);

  openBottomSheet(): void {
    this._bottomSheet.open(GroupsHomeComponent);
  }


}
