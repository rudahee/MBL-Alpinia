import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogRef } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CreateGroupInterface } from '../../../interfaces/group';
import { GroupService } from '../../../services/group/group.service';
import { JwtHandlerService } from '../../../services/jwt/JwtHandler.service';
import { RatingComponent } from '../rating/rating.component';

@Component({
  selector: 'app-join-group',
  templateUrl: './join-group.component.html',
  standalone: true,
  imports: [MatCardModule, MatDialogTitle, MatDividerModule, MatDialogContent, MatDialogActions, MatSelectModule, MatCheckboxModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule,MatInputModule],
  styleUrls: ['./join-group.component.css']
})
export class JoinGroupComponent implements OnInit {
  
  joinGroupForm: FormGroup;
  snackBar: any;
  router: any;
  inviteCode: any;



  constructor(private build: FormBuilder, private service: GroupService, private jtwService: JwtHandlerService, private dialogRef: MatDialogRef<RatingComponent>) {
    this.joinGroupForm = this.build.group({
      inviteCode: ['', [Validators.required]],
    });

    this.inviteCode = "";
  }

  ngOnInit() {
  }

  joinGroup(): void {
    this.inviteCode = this.joinGroupForm.value;
    console.log(this.inviteCode);

    this.service.joinGroup(this.inviteCode.inviteCode).subscribe(
      resp => {
        console.log(resp);
        this.router.navigate(['/group/'+resp.id]);
        
      }, err => {
        console.log(err);
        this.snackBar.open(err.error.message, 'Close', { duration: 5000, panelClass: 'snackbar'});
      }
    );
  }

  cancel() {
    this.dialogRef.close()
  }


}
