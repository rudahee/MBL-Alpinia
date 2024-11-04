import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import {Validators, FormsModule, ReactiveFormsModule, FormGroup, FormBuilder} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { RegisterInterface } from '../../interfaces/register';


@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [MatCardModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule,MatInputModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  user: RegisterInterface;
  signUpForm: FormGroup;
  visibilityPassword = false;
  visibilityRepeatPassword = false;



  constructor(private build: FormBuilder, private userService: UserService, private router: Router, private snackBar: MatSnackBar) { 
    this.user = {
      username: "",
      email: "",
      fullName: "",
      password: ""
    }

    this.signUpForm = this.build.group({
      fullname: ['', [Validators.required, Validators.minLength(5)]],
      email: ['', [Validators.required, Validators.minLength(4), Validators.email]],
      username: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirm_password: ['', [Validators.required, Validators.minLength(8)]]
    }, {validator: this.comparePassword('password', 'confirm_password')});

  }

  private comparePassword(control: string, secondControl: string): any {
    return (formGroup: FormGroup) => {
      const password = formGroup.controls[control];
      const confirmPassword = formGroup.controls[secondControl];
  
      if (!password || !confirmPassword) {
        return null; // Return null if either control is missing
      }
  
      if (confirmPassword.errors && !confirmPassword.errors['passwordMismatch']) {
        return null; // Return null if there's already another error
      }
  
      if (password.value !== confirmPassword.value) {
        confirmPassword.setErrors({ passwordMismatch: true });
        return { passwordMismatch: true }; // Return an object indicating the error
      } else {
        confirmPassword.setErrors(null); // Clear errors if passwords match
        return null; // Return null when there are no errors
      }
    };
  }

  signUp(): void {
    this.user = this.signUpForm.value;
    console.log(this.user);
    // tslint:disable-next-line: deprecation
    this.userService.signUp(this.user).subscribe(
      _ => {
        console.log(_);
        this.snackBar.open('Successful registration', 'Close', { duration: 5000, panelClass: 'snackbar'});
        setTimeout(() => {
          this.router.navigate(['/']);
        }, 500);

      }, err => {
        console.log(err);
        this.snackBar.open(err.error.message, 'Close', { duration: 5000, panelClass: 'snackbar'});
      }
    );
  }

  ngOnInit() {
  }

}
