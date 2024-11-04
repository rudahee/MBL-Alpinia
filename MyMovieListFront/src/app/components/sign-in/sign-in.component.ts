import { MatIconModule } from '@angular/material/icon';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import {FormControl, Validators, FormsModule, ReactiveFormsModule, FormBuilder, FormGroup} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { Router } from '@angular/router';
import { JwtHandlerService } from '../../services/jwt/JwtHandler.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../../services/user/user.service';
import { LoginInterface } from '../../interfaces/login';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [MatCardModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule,MatInputModule],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  signInForm: FormGroup;
  visibilityPassword = false;

  loginData: LoginInterface;


  constructor(private build: FormBuilder, private userService: UserService, private JWTHandler: JwtHandlerService,
    private router: Router, private snackBar: MatSnackBar) {

      this.loginData = {
        username: "",
        password: ""
      };

      this.signInForm = this.build.group({
        username: ['', [Validators.required, Validators.minLength(4)]],
        password: ['', [Validators.required, Validators.minLength(8)]]
      });
  }



  signIn(): void {
    this.loginData = this.signInForm.value;

    console.log(this.loginData)

    // tslint:disable-next-line: deprecation
    this.userService.signIn(this.loginData).subscribe(
      resp => {
        console.log("holi")
        console.log(resp)
        if (resp !== undefined && resp != null) {
          this.snackBar.open('Sign In successful', 'Close', { duration: 5000, panelClass: 'snackbar'});
          if (resp.body != null) {
            this.JWTHandler.saveJWT(resp.body);
            this.router.navigate(['/']);
          } else {
            this.snackBar.open('You can not log in, contact administrator', 'Close', {duration: 5000, panelClass: 'snackbar'})
          }
        }
      }, err => {
        console.log(err)
        this.snackBar.open('Username or password invalid', 'Close', { duration: 5000, panelClass: 'snackbar'});
      }
    );
  }

  ngOnInit() {
    
  }
 
}
